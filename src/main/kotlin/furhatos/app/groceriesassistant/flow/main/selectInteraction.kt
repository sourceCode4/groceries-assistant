package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Parent
import furhatos.app.groceriesassistant.nlu.DoGroceries
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val SelectInteraction = state(Parent) {
    onEntry { furhat.ask("What would you like to do?") }

    onResponse<DoGroceries> {
        furhat.say("Do some ${it.intent.groceries}, okay")
        goto(Groceries)
    }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(ExistingList) }
}