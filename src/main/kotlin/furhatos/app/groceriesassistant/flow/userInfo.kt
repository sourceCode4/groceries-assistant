package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.*
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.Diet
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.Sex
import furhatos.app.groceriesassistant.nlu.UserFieldValue
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Number

val UserInfo = state(WithUser) {
    onEvent<AskHeight> {
        furhat.ask("How tall are you?")
        goto(GetFieldValue(FieldEnum.HEIGHT))
    }
    onEvent<AskWeight> {
        furhat.ask("How much do you weigh?")
        goto(GetFieldValue(FieldEnum.WEIGHT))
    }
    onEvent<AskAge> {
        furhat.ask("How old are you?")
        goto(GetFieldValue(FieldEnum.AGE))
    }
    onEvent<AskSex> {
        furhat.ask("What is your sex?")
        goto(GetFieldValue(FieldEnum.SEX))
    }
    onEvent<AskDiet> {
        furhat.ask("What is your diet?")
        goto(GetFieldValue(FieldEnum.DIET))
    }
}

fun GetFieldValue(field: FieldEnum) = state(WithUser) {
    when (field) {
        FieldEnum.HEIGHT,
        FieldEnum.WEIGHT,
        FieldEnum.AGE -> onResponse<Number> {
            Memory.updateUser(
                field, UserFieldValue(number = it.intent))
        }
        FieldEnum.SEX -> onResponse<Sex> {
            Memory.updateUser(FieldEnum.SEX, UserFieldValue(sex = it.intent))
        }
        FieldEnum.DIET -> onResponse<Diet> {
            Memory.updateUser(FieldEnum.DIET, UserFieldValue(diet = it.intent))
        }
    }
}