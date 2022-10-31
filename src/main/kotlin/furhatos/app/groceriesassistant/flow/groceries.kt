package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.flow.main.Idle
import furhatos.app.groceriesassistant.flow.main.NewList
import furhatos.app.groceriesassistant.flow.main.SelectInteraction
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.Done
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.Exit
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.State
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Groceries: State = state(WithUser) {

    val SaveProgress = state(Global) {
        askMainQuestion("Do you want me to remember the changes?")

        onResponse<Yes> {
            furhat.say(alright)
            Memory.commit()
            terminate()
        }

        onResponse<No> {
            furhat.say(alright)
            Memory.overloadCurrent()
            terminate()
        }
    }

    onResponse<MakeList> {
        goto(NewList)
    }

    onResponse<EditList> {
        furhat.say("We are already working on a list!")
        reentry()
    }

    onResponse<Done> {
        furhat.say(alright)
        if (Memory.currentList().isNotEmpty()) call(SaveProgress)
        goto(SelectInteraction)
    }

    onResponse<Exit> {
        furhat.say("bye then!")
        if (Memory.currentList().isNotEmpty()) call(SaveProgress)
        goto(Idle)
    }
}