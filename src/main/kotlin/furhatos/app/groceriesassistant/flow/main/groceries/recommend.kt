package furhatos.app.groceriesassistant.flow.main.groceries

import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.flow.gui
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.recommend
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.memory.entity.Grocery
import furhatos.flow.kotlin.*
import furhatos.nlu.DynamicIntent
import furhatos.nlu.common.No
import furhatos.nlu.common.Number
import furhatos.nlu.common.Yes
import furhatos.util.Language

private var currentQuestion = recommend

fun Recommend(
    item: Grocery? = null,
    weight: Int = 100
) = state(Groceries) {
    val suggested = Memory.recommend(item, weight)

    onEntry {
        gui.clear()
        suggested.forEach {
            gui.append(it.name)
        }
        if (suggested.isEmpty()) terminate(false)
        raise(AskMainQuestion)
    }

    onEvent<AskMainQuestion> { furhat.ask(currentQuestion, timeout = 8000) }

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
        terminate(false)
    }

    var count: Int?
    for (option in suggested) {
        onResponse(DynamicIntent(
            listOf("@count ${option.name}"),
            mapOf("count" to Number::class.java),
            Language.ENGLISH_US)
        ) {
            count = (it.intent.getEntities()["count"] as Number).value
            if (count != null && (count as Int) < 1)
                raise(DubiousResponse())
            else {
                count = count ?: 1
                raise(option.name)
            }
        }
        onResponse(option.name) {
            count = call(HowMany(option.subgroup)) as Int?
            Memory.addItem(option, count ?: 1)
            furhat.say("$count ${option.name}")
            terminate(true)
        }
    }

    onExit { gui.clear() }
}