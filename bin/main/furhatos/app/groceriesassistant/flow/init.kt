package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.flow.main.Idle
import furhatos.app.groceriesassistant.setting.distanceToEngage
import furhatos.app.groceriesassistant.setting.maxNumberOfUsers
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")
        /** start the interaction */
        goto(Idle)
    }
}
