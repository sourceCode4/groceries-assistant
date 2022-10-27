package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.flow.main.NewList
import furhatos.app.groceriesassistant.flow.main.EditingList
import furhatos.app.groceriesassistant.flow.main.SelectInteraction
import furhatos.app.groceriesassistant.flow.utils.alright
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.Exit
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.*

val WithUser: State = state(Global) {

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> { goto(EditingList) }

    onResponse<Exit> {
        furhat.say(alright)
        goto(SelectInteraction)
    }
}