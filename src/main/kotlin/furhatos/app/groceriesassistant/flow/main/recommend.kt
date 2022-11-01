package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.events.control.AppendGUI
import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.recommend
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.flow.kotlin.*
import furhatos.nlu.ListEntity
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.No
import furhatos.nlu.common.Number
import furhatos.nlu.common.Yes

val Recommend = state(Groceries) {
    val suggested = Memory.recommend()
    var currentQuestion = recommend

    onEntry {
        suggested.forEach {
            raise(AppendGUI(it.name))
        }
        raise(AskMainQuestion)
    }

    onEvent<AskMainQuestion> { furhat.ask(currentQuestion) }

    onResponse<Yes> {
        furhat.ask("which one?", timeout = 6000)
        currentQuestion = utterance{+"which one?"}
    }

    onResponse<No> {
        furhat.say(alright)
        goto(EditingList)
    }

    var count: Int? = 1
    for (item in suggested) {
        onPartialResponse<Number> {
            count = it.intent.value
            if (count != null && (count as Int) < 1)
                raise(DubiousResponse())
            else {
                val second = it.secondaryIntent
                if (second != null) {
                    count = count ?: 1
                    raise(second)
                }
            }
        }
        onResponse(item.name) {
            Memory.addItem(item, count ?: 1)
            furhat.say("$count ${it.text}")
        }
    }
}