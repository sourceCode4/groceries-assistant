package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.UserInfo
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.EditUser
import furhatos.app.groceriesassistant.utils.askMainQuestion
import furhatos.app.groceriesassistant.utils.done
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val UpdateUser = state(UserInfo) {
    askMainQuestion("what do you want to update?")

    onReentry { furhat.ask(done) }

    onResponse<EditUser> {
        val field = it.intent.fieldName
        val value = it.intent.value
        if (field != null && it.intent.isValid) {
            if (value != null)
                Memory.updateUser(field, value)
            else when (field) {

            }
        } else propagate()
    }
}