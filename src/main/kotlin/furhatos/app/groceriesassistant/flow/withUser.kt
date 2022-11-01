package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.AppendGUI
import furhatos.app.groceriesassistant.events.control.ClearGUI
import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.main.SelectInteraction
import furhatos.app.groceriesassistant.nlu.Done
import furhatos.app.groceriesassistant.utils.alright
import furhatos.app.groceriesassistant.nlu.Exit
import furhatos.flow.kotlin.*
import furhatos.skills.HostedGUI

private var gui = HostedGUI("gui")

val WithUser: State = state(Global) {

    onEvent<AppendGUI> { gui.append(it.message) }

    onEvent<ClearGUI> { gui.clear() }

    onEvent<DubiousResponse> {
        furhat.say(random("don't be silly", "that doesn't seem right"))
        reentry()
    }

    onReentry { furhat.listen() }

    onResponse<Done> {
        furhat.say(alright)
        gui.clear()
        goto(SelectInteraction)
    }
}