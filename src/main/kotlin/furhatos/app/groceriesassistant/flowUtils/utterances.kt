package furhatos.app.groceriesassistant.flowUtils

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
        +"is that all"
    }
}

val recommend = utterance {
    random {
        +"would you like some of these items?"
    }
}

fun howMany(it: String = "") = utterance {
    val ofIt = if (it == "") "" else "of $it"
    random {
        +"how many grams $ofIt do you want?"
        +"how many grams $ofIt would you like?"
        +"how many grams?"
    }
}