package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithProgress
import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.app.groceriesassistant.flow.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.flow.events.error.NluError
import furhatos.app.groceriesassistant.flow.utils.alright
import furhatos.app.groceriesassistant.nlu.AddGrocery
import furhatos.app.groceriesassistant.nlu.Exit
import furhatos.app.groceriesassistant.nlu.QuantifiedGroceryEntity
import furhatos.flow.kotlin.*
import furhatos.nlu.Response
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No

val EditingList: State = state(WithProgress) {0
    var addedFirst = false
    var justGotYes = false

    onEntry { raise(AskMainQuestion) }
    onEvent<AskMainQuestion> { furhat.ask("What would you like to add?") }

    onReentry {
        justGotYes = false
        if (addedFirst)
            furhat.ask { random(+"Anything else?", +"Anything else you want to add?") }
        else
            furhat.ask { random(+"Do you want to add anything?", +"Anything you want to add?") }
    }

    onResponse<Yes>(cond = { addedFirst && reentryCount > 0 }) {
        justGotYes = true
        furhat.ask("what would you like?")
    }

    onResponse<No>(cond = { !justGotYes && reentryCount > 0 }) {
        furhat.say {
            if (addedFirst) +"ok, I will save the current list"
            else alright
        }
        raise(Response<Exit>())
    }

    onResponse<AddGrocery> {
        val grocery = it.intent.grocery
        if (grocery == null) {
            raise(NluError(thisState, "Grocery was null"))
            propagate()
        } else {
            val addedNew = call(ChooseItem(grocery)) as Boolean
            addedFirst = addedFirst || addedNew
            reentry()
        }
    }

    onResponse<Exit> {
        //handle exit behavior
    }
}

//TODO: get the list of matches
fun ChooseItem(grocery: QuantifiedGroceryEntity) = state(WithProgress) {
    onEntry {
        furhat.say(alright)
        terminate(true)
    }
}