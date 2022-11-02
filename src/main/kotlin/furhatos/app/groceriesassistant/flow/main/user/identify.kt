package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Global
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.flowUtils.greeting
import furhatos.app.groceriesassistant.flowUtils.sayAndAskMain
import furhatos.flow.kotlin.*
import furhatos.nlu.common.PersonName

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
            goto(OpenGui)
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
