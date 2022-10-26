package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.flow.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.flow.utils.alright
import furhatos.app.groceriesassistant.nlu.DoGroceries
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.app.groceriesassistant.nlu.UpdateUserInfo
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val SelectInteraction = state(WithUser) {
    onEntry { raise(AskMainQuestion) }

    onEvent<AskMainQuestion> { furhat.ask("What would you like to do?") }

    onResponse<DoGroceries> {
        furhat.say("Do some ${it.intent.groceries}, okay")
        goto(NewOrExisting)
    }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(EditingList) }

    onResponse<UpdateUserInfo> {
        //TODO: edit user info
        furhat.say(alright)
        reentry()
    }
}