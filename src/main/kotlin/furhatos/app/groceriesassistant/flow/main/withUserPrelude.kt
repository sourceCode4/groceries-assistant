package furhatos.app.groceriesassistant.flow.main

import furhatos.app.groceriesassistant.flow.WithUser
import furhatos.event.senses.SenseSkillGUIConnected
import furhatos.flow.kotlin.furhat
import furhatos.flow.kotlin.state

val WithUserPrelude = state(WithUser) {
    onEntry {
        furhat.say("open up the G.U.I.")
    }

    onEvent<SenseSkillGUIConnected> {
        goto(SelectInteraction)
    }
}