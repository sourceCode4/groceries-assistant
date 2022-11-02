package furhatos.app.groceriesassistant.flow.events.control

import furhatos.event.Event

open class AskQuestion(val id: String) : Event()

object AskMainQuestion : AskQuestion("main")