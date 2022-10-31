package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Global
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.PersonName
import furhatos.app.groceriesassistant.utils.greeting
import furhatos.app.groceriesassistant.utils.sayAndAskMain
import furhatos.flow.kotlin.*

val IdentifyUser = state(Global) {
    var noResponse = 0

    sayAndAskMain(greeting, "what's your name?")

    onReentry {
        furhat.listen()
    }

    onResponse<PersonName> {
        val name = it.intent.text
        val isUser = Memory.setUser(name)
        if (isUser) {
            furhat.say("Good to see you again $name!")
            goto(WithUserPrelude)
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
