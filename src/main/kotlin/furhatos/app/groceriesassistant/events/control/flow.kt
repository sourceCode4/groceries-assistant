package furhatos.app.groceriesassistant.events.control

import furhatos.event.Event

open class AskQuestion(val id: String) : Event()

object AskMainQuestion : AskQuestion("main")