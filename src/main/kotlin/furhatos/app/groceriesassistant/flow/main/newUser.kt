package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Parent
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

fun NewUser(name: String) = state(Parent) {
    onEntry {
        furhat.say("Nice to meet you $name!")
        furhat.ask("I'm going to ask you for some information, is that alright?")
    }

    onResponse<Yes> {
        furhat.say("Great!")
        goto(GatherInfo(name))
    }

    onResponse<No> {
        val areYouSure = state {
            var noResponse = 0
            onEntry {
                furhat.ask("Are you sure?")
            }

            onResponse<Yes> {
                furhat.say("That's a shame!")
                terminate(true)
            }

            onResponse<No> {
                furhat.say("I am glad you changed your mind!")
                terminate(false)
            }

            onNoResponse {
                noResponse++
                if (noResponse > 1) terminate(true)
                else propagate()
            }
        }
        val noConsent = call(areYouSure) as Boolean
        if (noConsent) goto(Idle)
        else furhat.listen()
    }
}

fun GatherInfo(name: String) = state {
    onEntry {
        furhat.say("This is not yet implemented")
    }
}