package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class BuySynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("buy,get,purchase,pick up,shop for,procure")
    }
}

class MakeSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("make,create,assemble,put together")
    }
}

class EditSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("edit,modify,change,refine,update")
    }
}


class InfoSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("info,information,info,profile,settings")
    }
}

class ExitSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("exit, quit, return, go back")
    }
}