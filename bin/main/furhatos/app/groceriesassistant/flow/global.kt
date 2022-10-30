package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.flow.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.nlu.common.RequestRepeat

val Global = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

    onResponse<RequestRepeat> { raise(AskMainQuestion) }
}