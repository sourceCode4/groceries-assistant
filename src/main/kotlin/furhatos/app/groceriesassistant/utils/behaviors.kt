package furhatos.app.groceriesassistant.utils

import furhatos.app.groceriesassistant.events.control.AskMainQuestion
import furhatos.flow.kotlin.*

fun StateBuilder.askMainQuestion(question: String) =
    askMainQuestion(utterance { +question })

fun StateBuilder.askMainQuestion(question: Utterance) {
    onEntry { raise(AskMainQuestion) }
    onEvent<AskMainQuestion> { furhat.ask(question) }
}

fun StateBuilder.sayAndAskMain(state: String, question: String) {
    sayAndAskMain(utterance { +state }, question)
}
fun StateBuilder.sayAndAskMain(utterance: Utterance, question: String) {
    onEntry {
        furhat.say(utterance)
        raise(AskMainQuestion)
    }
    onEvent<AskMainQuestion> { furhat.ask(question) }
}

