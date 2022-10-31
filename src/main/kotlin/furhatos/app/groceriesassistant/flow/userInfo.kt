package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.*
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.Diet
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.Sex
import furhatos.app.groceriesassistant.nlu.UserFieldValue
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.partialState
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Number

val UserInfo = partialState {
    onEvent<AskHeight> {
        furhat.say("How tall are you?")
        call(GetFieldValue(FieldEnum.HEIGHT))
        raise(GotHeight())
    }
    onEvent<AskWeight> {
        furhat.say("How much do you weigh?")
        call(GetFieldValue(FieldEnum.WEIGHT))
        raise(GotWeight())
    }
    onEvent<AskAge> {
        furhat.say("How old are you?")
        call(GetFieldValue(FieldEnum.AGE))
        raise(GotAge())
    }
    onEvent<AskSex> {
        furhat.say("What is your sex?")
        call(GetFieldValue(FieldEnum.SEX))
        raise(GotSex())
    }
    onEvent<AskDiet> {
        furhat.say("What is your diet?")
        call(GetFieldValue(FieldEnum.DIET))
        raise(GotDiet())
    }
}

fun GetFieldValue(field: FieldEnum) = state(Global) {
    when (field) {
        FieldEnum.HEIGHT,
        FieldEnum.WEIGHT,
        FieldEnum.AGE -> onResponse<Number> {
            if (Memory.updateUser(
                field, UserFieldValue(number = it.intent)))
                terminate()
            else propagate()
        }
        FieldEnum.SEX -> onResponse<Sex> {
            if (Memory.updateUser(FieldEnum.SEX, UserFieldValue(sex = it.intent)))
                terminate()
            else propagate()
        }
        FieldEnum.DIET -> onResponse<Diet> {
            if(Memory.updateUser(FieldEnum.DIET, UserFieldValue(diet = it.intent)))
                terminate()
            else propagate()
        }
    }
    onEntry { furhat.listen() }
}