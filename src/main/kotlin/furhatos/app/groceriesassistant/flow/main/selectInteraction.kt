package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.*
import furhatos.flow.kotlin.*

val SelectInteraction = state(WithUser) {
    init {
        //TODO: commented out for testing
        //Memory.overloadCurrent()
    }

    askMainQuestion(utterance {
        random {
            +"what can i do for you?"
            +"what do you want to do?"
        }
    })

    onResponse<DoGroceries> {
        if (it.intent.buy != null)
            furhat.say("${it.intent.buy} some ${it.intent.groceries}, okay")
        else {
            furhat.say("${it.intent.groceries}, sure")
        }
        if (Memory.currentList().isNotEmpty())
            goto(NewOrExisting)
        else
            goto(NewList)
    }

    onResponse<UpdateUserInfo> {
        furhat.say(alright)
        goto(UpdateUser)
    }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(EditingList) }

    onResponse<Done> { raise(Exit()) }
}