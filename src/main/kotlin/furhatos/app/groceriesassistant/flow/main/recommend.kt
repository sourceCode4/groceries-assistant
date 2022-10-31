package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.Groceries
import furhatos.app.groceriesassistant.flowUtils.askMainQuestion
import furhatos.app.groceriesassistant.flowUtils.recommend
import furhatos.app.groceriesassistant.memory.Memory
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val Recommend = state(Groceries) {
    onEntry {
//        Memory.recommend
//        furhat.ask(recommend())
    }
}