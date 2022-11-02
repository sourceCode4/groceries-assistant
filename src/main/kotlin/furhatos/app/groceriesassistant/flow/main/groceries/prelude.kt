package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.*

val NewOrExisting: State = state(WithUser) {
    askMainQuestion("Do you want to make a new list, or edit the current one?")

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> {
        furhat.say(alright)
        goto(EditingList)
    }
}