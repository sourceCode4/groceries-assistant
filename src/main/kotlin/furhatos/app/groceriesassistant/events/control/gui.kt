package furhatos.app.groceriesassistant.events.control

import furhatos.event.Event

open class GUIEvent : Event()

class AppendGUI(val message: String) : GUIEvent()

class ClearGUI : GUIEvent()