package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.*
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.nlu.Diet
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.Sex
import furhatos.app.groceriesassistant.nlu.UserFieldValue
import furhatos.app.groceriesassistant.utils.askMainQuestion
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.partialState
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Number

val UserInfo = partialState {
    onEvent<AskHeight> {
        val question = "How tall are you?"
        call(GetFieldValue(FieldEnum.HEIGHT, question))
        raise(GotHeight())
    }
    onEvent<AskWeight> {
        val question = "How much do you weigh?"
        call(GetFieldValue(FieldEnum.WEIGHT, question))
        raise(GotWeight())
    }
    onEvent<AskAge> {
        val question = "How old are you?"
        call(GetFieldValue(FieldEnum.AGE, question))
        raise(GotAge())
    }
    onEvent<AskSex> {
        val question = "What is your sex?"
        call(GetFieldValue(FieldEnum.SEX, question))
        raise(GotSex())
    }
    onEvent<AskDiet> {
        val question = "What is your diet?"
        call(GetFieldValue(FieldEnum.DIET, question))
        raise(GotDiet())
    }
}

fun GetFieldValue(field: FieldEnum, question: String) = state(Global) {
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

    askMainQuestion(question)
}