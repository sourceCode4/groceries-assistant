package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting : State = state(Parent) {
    onEntry {
        furhat.ask("Should I say Hello World?")
    }

    onResponse<Yes> {
        furhat.say("Hello World! .")
    }

    onResponse<No> {
        furhat.say("Ok.")
    }
}
