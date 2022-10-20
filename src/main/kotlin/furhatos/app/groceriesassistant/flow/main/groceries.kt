package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Parent
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val Groceries = state(Parent) {
    onEntry { furhat.ask("Do you want to make a new list, or edit an existing one?") }

    /* for testing */
    onReentry { furhat.listen() }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(ExistingList) }
}

val NewList = state(Parent) {
    onEntry {
        furhat.say("This is not yet implemented")
        goto(Idle)
    }
}

val ExistingList = state(Parent) {
    onEntry {
        furhat.say("This is not yet implemented")
        goto(Idle)
    }
}