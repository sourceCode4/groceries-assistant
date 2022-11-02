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

val alright = utterance {
    random {
        +"alright"
        +"okay"
        +"sure"
    }
}

val repeat = utterance {
    random {
        +"would you repeat that"
        +"can you repeat that"
        +"come again please"
    }
}