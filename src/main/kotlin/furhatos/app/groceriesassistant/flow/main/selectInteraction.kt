package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.utils.alright
import furhatos.app.groceriesassistant.utils.askMainQuestion
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.*
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.flow.kotlin.utterance

val SelectInteraction = state(WithUser) {
    init { Memory.overloadCurrent() }

    askMainQuestion(utterance {
        random {
            +"what can i do for you?"
            +"what do you want to do?"
        }
    })

    onResponse<DoGroceries> {
        furhat.say("${it.intent.buy} some ${it.intent.groceries}, okay")
        goto(NewOrExisting)
    }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(EditingList) }

    onResponse<UpdateUserInfo> {
        furhat.say(alright)
        goto(UpdateUser)
    }

    onResponse(listOf(Exit(), Done())) {
        goto(Idle)
    }
}