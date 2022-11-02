package furhatos.app.groceriesassistant.events.control

import furhatos.event.Event

abstract class AskQuestion(val id: String) : Event()
object AskMainQuestion : AskQuestion("main")

class DubiousResponse : Event()
abstract class AskInfo : Event()
class AskName : AskInfo()
class AskHeight : AskInfo()
class AskWeight : AskInfo()
class AskAge : AskInfo()
class AskSex : AskInfo()
class AskDiet : AskInfo()
abstract class GotInfo : Event()
class GotHeight : GotInfo()
class GotWeight : GotInfo()
class GotAge : GotInfo()
class GotSex : GotInfo()
class GotDiet : GotInfo()