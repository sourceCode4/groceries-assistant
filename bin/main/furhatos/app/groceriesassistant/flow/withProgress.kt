package furhatos.app.groceriesassistant.flow

import furhatos.app.groceriesassistant.nlu.Exit
import furhatos.flow.kotlin.onResponse
import furhatos.flow.kotlin.state

val WithProgress = state(WithUser) {
    onResponse<Exit> {

    }
}