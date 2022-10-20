package furhatos.app.groceriesassistant.flow.utils

import furhatos.flow.kotlin.utterance

val greeting = utterance {
    random {
        +"Hey"
        +"Hi"
        +"Hello"
    }
    random {
        +"there!"
        +"!"
    }
}