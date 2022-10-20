package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class Food : EnumEntity(stemming = true, speechRecPhrases = true) {
    /* food from the database goes here */
    override fun getEnum(lang: Language): List<String> {
        return listOf("")
    }
}