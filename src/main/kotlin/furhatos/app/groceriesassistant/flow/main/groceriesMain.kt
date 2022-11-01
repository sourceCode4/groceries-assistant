package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.gui
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.memory.Memory.getGroceryItems
import furhatos.app.groceriesassistant.nlu.*
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.flowUtils.howMany
import furhatos.app.groceriesassistant.memory.entity.Grocery
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import furhatos.nlu.common.Number

val NewList = state(Groceries) {
    onEntry {
        Memory.newList()
        goto(EditingList)
    }
}

val EditingList: State = state(Groceries) {
    var addedFirst = false
    var justGotYes = false
    var justSuggested = false

    onEntry { raise(AskMainQuestion) }
    onEvent<AskMainQuestion> {
        furhat.ask("What would you like to add?", endSil = 1500, maxSpeech = 30000)
    }

    onReentry {
        justGotYes = false
        if (addedFirst) {
            if (!justSuggested && random(true, false)) {
                justSuggested = true
                call(Recommend)
                reentry()
            } else {
                furhat.ask(random(
                    "Anything else?",
                    "Anything else you want to add?"))
                justSuggested = false
            }
        } else
            furhat.ask(random(
                "Do you want to add anything?",
                "Anything you want to add?"), endSil = 1500, maxSpeech = 30000)
    }

    onResponse<Yes> {
        if (!justGotYes && reentryCount > 0) {
            justGotYes = true
            furhat.say(alright)
            furhat.ask("what would you like?")
        } else propagate()
    }

    onResponse<No> {
        if (!justGotYes && reentryCount > 0) raise(Done())
        else propagate()
    }

    onResponse<AddGroceries> {
        val groceries = it.intent.groceries
        if (groceries == null) propagate()
        else {
            for (grocery in groceries.list) {
                furhat.say("here are some options for ${grocery.text}")
                val options = grocery.grocery?.getGroceryItems()?.take(10) ?: continue
                val addedNew = call(ChooseItem(grocery, options)) as Boolean?
                addedFirst = addedFirst || (addedNew ?: false)
            }
            reentry()
        }
    }
}

// Check that input.grocery != null before calling!!
fun ChooseItem(input: QuantifiedGrocery, options: List<Grocery>) = state(Groceries) {
    input.grocery!!

    onEntry {
        gui.clear()
        options.forEach {
            gui.append(it.name)
        }
        raise(AskMainQuestion)
    }

    options.forEach { option ->
        onResponse(option.name) {
            var count = input.count?.value
            if (count == null || input.isUnspecifiedPlural) {
                count = call(HowMany(input.grocery.category?.text)) as Int? ?: 0
            }
            when {
                count < 0 -> raise(DubiousResponse())
                count > 1 -> furhat.say("$count ${input.grocery.text}")
                else -> furhat.say(option.name)
            }
            Memory.addItem(option, count)
            terminate(true)
        }
    }

    onEvent<AskMainQuestion> {
        furhat.ask(random("which product would you like?",
                                "which would you like?"), timeout = 8000)
    }

    onResponse("none", "neither", "nothing") {
        furhat.say(alright)
        terminate(false)
    }

    onExit { gui.clear() }
}

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
                received.first().grocery?.category?.text == category &&
                received.first().count != null)
                raise(received.first().count!!)
            else
                propagate()
        }
    }
}
