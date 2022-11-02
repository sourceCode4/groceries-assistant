package furhatos.app.groceriesassistant.flow.main.groceries

import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.flow.gui
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.recommend
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.flow.kotlin.*
import furhatos.nlu.DynamicIntent
import furhatos.nlu.common.No
import furhatos.nlu.common.Number
import furhatos.nlu.common.Yes
import furhatos.util.Language

private var currentQuestion = recommend

val Recommend = state(Groceries) {
    val suggested = Memory.recommend()

    onEntry {
        gui.clear()
        suggested.forEach {
            gui.append(it.name)
        }
        raise(AskMainQuestion)
    }

    onEvent<AskMainQuestion> { furhat.ask(currentQuestion) }

    onPartialResponse<Yes> {
        val secondary = it.secondaryIntent
        if (secondary == null) {
            furhat.ask("which one?", timeout = 6000)
            currentQuestion = utterance{+"which one would you like?"}
        } else {
            raise(it, secondary)
        }
    }

    onResponse<No> {
        furhat.say(alright)
        terminate()
    }

    var count: Int?
    for (item in suggested) {
        onResponse(DynamicIntent(
            listOf("@count ${item.name}"),
            mapOf("count" to Number::class.java),
            Language.ENGLISH_US)
        ) {
            count = (it.intent.getEntities()["count"] as Number).value
            if (count != null && (count as Int) < 1)
                raise(DubiousResponse())
            else {
                count = count ?: 1
                raise(item.name)
            }
        }
        onResponse(item.name) {
            count = call(HowMany(item.subgroup)) as Int?
            Memory.addItem(item, count ?: 1)
            furhat.say("$count ${item.name}")
            terminate()
        }
    }

    onExit { gui.clear() }
}