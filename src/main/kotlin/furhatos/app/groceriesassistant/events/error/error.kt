package furhatos.app.groceriesassistant.events.error

import furhatos.event.Event
import furhatos.flow.kotlin.State

abstract class Error(val state: State) : Event()

class NluError(state: State, val message: String) : Error(state) {
    init {
        println("NLU failed in ${state.name} with message: $message")
    }
}