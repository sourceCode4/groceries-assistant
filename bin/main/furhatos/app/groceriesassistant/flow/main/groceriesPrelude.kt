package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.flow.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.flow.utils.alright
import furhatos.app.groceriesassistant.flow.utils.repeat
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.EditList
import furhatos.app.groceriesassistant.nlu.MakeList
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val NewOrExisting: State = state(WithUser) {
    onEntry { raise(AskMainQuestion) }

    onEvent<AskMainQuestion> { furhat.ask("Do you want to make a new list, or edit the current one?") }

    onReentry { furhat.listen() }

    onResponse<MakeList> { goto(NewList) }

    onResponse<EditList> {
        furhat.say(alright)
        goto(EditingList)
    }
}

val NewList = state(WithUser) {
    onEntry { raise(AskMainQuestion) }

    onEvent<AskMainQuestion> { furhat.ask("Do you wish to archive the current list?") }

    onResponse<Yes> {

        furhat.say(alright)
        Memory.newList(true)
        goto(EditingList)
    }

    onResponse<No> {
        furhat.say(alright)
        Memory.newList(false)
        goto(EditingList)
    }
}