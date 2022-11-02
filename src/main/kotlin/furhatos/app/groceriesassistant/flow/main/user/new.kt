package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Global
import furhatos.app.groceriesassistant.events.control.*
import furhatos.app.groceriesassistant.flow.UserInfo
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.flowUtils.sayAndAskMain
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

fun NewUser(name: String) = state(Global) {

    sayAndAskMain(
        "hi! nice to meet you $name!",
        "I will ask you for some basic information, is that alright?")

    onResponse<Yes> {
        furhat.say("Great!")
        goto(GatherInfo(name))
    }

    onResponse(listOf("that is fine", "that is alright")) {
        raise(Yes())
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

fun GatherInfo(name: String) = state(Global) {

    include(UserInfo)

    onEntry {
        Memory.initUser(name)
        raise(AskHeight())
    }
    onEvent<GotHeight> { raise(AskWeight()) }

    onEvent<GotWeight> { raise(AskAge()) }

    onEvent<GotAge> { raise(AskSex()) }

    onEvent<GotSex> { raise(AskDiet()) }

    onEvent<GotDiet> {
        furhat.say("perfect, i am ready to compose your first list!")
        Memory.setNewUser()
        goto(OpenGui)
    }
}