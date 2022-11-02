package furhatos.app.groceriesassistant.flow.main.user

import furhatos.app.groceriesassistant.events.control.*
import furhatos.app.groceriesassistant.flow.UserInfo
import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.EditUser
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.flowUtils.done
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val UpdateUser = state(WithUser) {

    include(UserInfo)

    askMainQuestion("what do you want to update?")

    onReentry { furhat.ask(done) }

    onResponse<EditUser> {
        val field = it.intent.field
        val fieldName = it.intent.fieldName
        val value = it.intent.value
        if (field != null) {
            if (it.intent.isValid) {
                Memory.updateUser(field.enum, value!!)
                reentry()
            } else {
                if (it.intent.hasValue)
                    furhat.say("That is not a valid value for $fieldName")
                when (fieldName) {
                    "name"   -> raise(AskName())
                    "height" -> raise(AskHeight())
                    "weight" -> raise(AskWeight())
                    "age"    -> raise(AskAge())
                    "sex"    -> raise(AskSex())
                    "diet"   -> raise(AskDiet())
                }
            }
        }
        propagate()
    }

    onEvent<GotInfo> { reentry() }

    onExit { Memory.commitUser() }
}