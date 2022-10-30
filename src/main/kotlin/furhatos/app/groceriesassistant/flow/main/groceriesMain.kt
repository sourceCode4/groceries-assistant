package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.events.control.AppendGUI
import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.app.groceriesassistant.utils.alright
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.app.groceriesassistant.memory.Memory.getGroceryItems
import furhatos.app.groceriesassistant.nlu.*
import furhatos.app.groceriesassistant.utils.askMainQuestion
import furhatos.app.groceriesassistant.utils.howMany
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Yes
import furhatos.nlu.common.No
import furhatos.nlu.common.Number

val NewList = state(Groceries) {
    onEntry {
        if (Memory.currentList().isEmpty())
            goto(EditingList)
        else
            raise(AskMainQuestion)
    }

    onEvent<AskMainQuestion> {
        furhat.ask("Do you wish to archive the current list?")
    }

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

val EditingList: State = state(Groceries) {
    var addedFirst = false
    var justGotYes = false

    onEntry { raise(AskMainQuestion) }
    onEvent<AskMainQuestion> {
        furhat.ask("What would you like to add?", endSil = 1500, maxSpeech = 30000)
    }

    onReentry {
        justGotYes = false
        if (addedFirst)
            furhat.ask(random("Anything else?", "Anything else you want to add?"))
        else
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
        if (!justGotYes && reentryCount > 0) raise(Exit())
        else propagate()
    }

    onResponse<AddGroceries> {
        val groceries = it.intent.groceries
        if (groceries == null) propagate()
        else {
            for (grocery in groceries.list) {
                val addedNew = call(ChooseItem(grocery)) as Boolean?
                addedFirst = addedFirst || (addedNew ?: false)
            }
            reentry()
        }
    }
}

fun ChooseItem(input: QuantifiedGrocery) = state(Groceries) {
    val options = input.grocery?.getGroceryItems()?.take(10)

    onEntry {
        furhat.say("here are some options for ${input.grocery?.text}.")
        options?.forEach {
            raise(AppendGUI(it.name))
        }
        raise(AskMainQuestion)
    }

    options?.forEach { option ->
        onResponse(option.name) {
            var count = input.count?.value
            if (count == null || input.isUnspecifiedPlural) {
                count = call(HowMany(input.grocery)) as Int? ?: 0
            }
            when (count) {
                0, 1 -> furhat.say(option.name)
                else -> furhat.say("$count ${input.grocery.text}")
            }
            Memory.addItem(option, count)
            terminate(true)
        }
    }

    onReentry { raise(AskMainQuestion) }

    onEvent<AskMainQuestion> {
        furhat.ask("which product would you like?")
    }
}

fun HowMany(item: GroceryKind) = state(Groceries) {
    askMainQuestion(howMany())

    onResponse<Number> {
        val number = it.intent.value
        when {
            number == null -> propagate()
            number < 1     -> {
                furhat.say("don't be silly")
                reentry()
            }
            else -> terminate(number)
        }
    }
    // change to quantified grocery?
    onResponse<AddGroceries> {
        val received = it.intent.groceries?.list
        if (received == null) propagate()
        else {
            if (received.size == 1 &&
                received.first().grocery?.category == item.category &&
                received.first().count != null)
                raise(received.first().count!!)
            else
                propagate()
        }
    }
}
