package furhatos.app.groceriesassistant.flow.main.groceries

import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.flowUtils.howMany
import furhatos.app.groceriesassistant.nlu.AddGroceries
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.raise
import furhatos.flow.kotlin.state
import furhatos.nlu.common.Number

fun HowMany(category: String?) = state(Groceries) {
    askMainQuestion(howMany())

    onResponse<Number> {
        val number = it.intent.value
        when {
            number == null -> propagate()
            number < 1     -> raise(DubiousResponse())
            else -> terminate(number)
        }
    }

    onResponse<AddGroceries> {
        val received = it.intent.groceries?.list
        if (received == null) propagate()
        else {
            if (received.size == 1 &&
                received.first().grocery?.text == category &&
                received.first().count != null)
                raise(received.first().count!!)
            else
                propagate()
        }
    }
}
