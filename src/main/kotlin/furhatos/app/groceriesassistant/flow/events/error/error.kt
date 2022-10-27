package furhatos.app.groceriesassistant.flow.events.error

import furhatos.event.Event
import furhatos.flow.kotlin.State

abstract class ErrorEvent(val state: State) : Event()

class NluError(state: State, val message: String) : ErrorEvent(state) {
    init {
        println("NLU failed in ${state.name} with message: $message")
    }
}