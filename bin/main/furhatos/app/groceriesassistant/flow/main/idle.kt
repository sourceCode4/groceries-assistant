package furhatos.app.groceriesassistant.flow.main

import furhatos.flow.kotlin.*

val Idle: State = state {

    init {
        when {
            users.count > 0 -> {
                furhat.attend(users.random)
                goto(IdentifyUser)
            }
        }
    }

    onEntry {
        furhat.attendNobody()
    }

    onUserEnter {
        furhat.attend(it)
        goto(IdentifyUser)
    }
}
