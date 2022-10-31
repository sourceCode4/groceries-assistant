package furhatos.app.groceriesassistant.utils

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

val done = utterance {
    random {
        +"anything else"
        +"that's it"
        +"is that all"
    }
}

fun howMany(it: String = "") = utterance {
    random {
        +"how many $it do you want?"
        +"how many $it would you like?"
        +"how many?"
    }
}