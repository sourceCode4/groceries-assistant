package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Global
import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onNoResponse
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

fun NewUser(name: String) = state(Global) {
    onEntry {
        furhat.say("hi! nice to meet you $name!")
        raise(AskMainQuestion)
    }

    onEvent<AskMainQuestion> { furhat.ask("I will ask you for some basic information, is that alright?") }

    onResponse<Yes> {
        furhat.say("Great!")
        goto(GatherInfo(name))
    }

    onResponse<No> {
        val areYouSure = state(Global) {
            var noResponse = 0
            onEntry { raise(AskMainQuestion) }
            onEvent<AskMainQuestion> { furhat.ask("Are you sure?") }

            onReentry { furhat.listen() }

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
                if (noResponse > 1) {
                    furhat.say("I guess that's a no")
                    terminate(true)
                }
                else propagate()
            }
        }
        val noConsent = call(areYouSure) as Boolean
        if (noConsent) goto(Idle)
        else furhat.listen()
    }
}

//TODO:
fun GatherInfo(name: String) = state(Global) {
    onEntry {
        furhat.say("This is not yet implemented")
    }
}