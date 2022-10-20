package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Parent
import furhatos.app.groceriesassistant.flow.utils.greeting
import furhatos.app.groceriesassistant.memory.Current
import furhatos.app.groceriesassistant.memory.longterm.Users
import furhatos.flow.kotlin.*
import furhatos.nlu.common.PersonName

val IdentifyUser = state(Parent) {
    var noResponse = 0

    onEntry {
        noResponse = 0
        reentry()
    }

    onReentry {
        furhat.say(greeting)
        furhat.ask("What's your name?")
    }

    onResponse<PersonName> {
        val name = it.intent.text
        val user = Users[name]
        if (user != null) {
            furhat.say("Good to see you again $name!")
            Current.user = user
            goto(SelectInteraction)
        }
        else goto(NewUser(name))
    }

    onNoResponse {
        noResponse++
        if (noResponse == 1) furhat.ask("Don't be shy.")
        else {
            furhat.say("Come back when you're ready to talk!")
            goto(Idle)
        }
    }
}
