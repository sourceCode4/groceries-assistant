package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.events.control.DubiousResponse
import furhatos.app.groceriesassistant.flow.main.SelectInteraction
import furhatos.app.groceriesassistant.nlu.Done
import furhatos.app.groceriesassistant.flowUtils.alright
import furhatos.flow.kotlin.*
import furhatos.skills.HostedGUI

var gui = HostedGUI("gui")

val WithUser: State = state(Global) {

    onEvent<DubiousResponse> {
        furhat.say(random("don't be silly", "that doesn't seem right"))
        reentry()
    }

    onResponse<Done> {
        furhat.say(alright)
        gui.clear()
        goto(SelectInteraction)
    }
}