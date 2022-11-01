package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.flow.main.Idle
import furhatos.app.groceriesassistant.nlu.Done
import furhatos.app.groceriesassistant.nlu.Exit
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

    onResponse<Done> { raise(Exit()) }

    onResponse<Exit> {
        furhat.say("Till next time!")
        goto(Idle)
    }

    onNoResponse {
        val wannaTalk = furhat.askYN("Do you still want to talk?") {
            onResponse("i don't", "i do not") { terminate(false) }
            onResponse<Done> { terminate(false) }
            onNoResponse { terminate(null) }
        }
        when (wannaTalk) {
            true -> reentry()
            null -> furhat.say("I guess that's a no")
        }
        raise(Exit())
    }

    onResponse {
        val getIt = random("understand that", "get that", "catch that")
        furhat.say("Sorry, I didn't $getIt")
        reentry()
    }
}