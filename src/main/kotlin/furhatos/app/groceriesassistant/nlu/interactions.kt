package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class BuySynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("buy:buy,get,purchase,pick up,shop for,procure")
    }
}

class GroceriesSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("groceries:groceries,food,stuff,produce,goods")
    }
}

class DoGroceries(
    val groceries: GroceriesSynonym? = null,
    val buy: BuySynonym? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to do some groceries",
            "I want to @buy some @groceries",
            "@buy @groceries")
    }
}

class MakeSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("make:make,create,assemble,put together")
    }
}

class EditSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("edit:edit,modify,change,refine,update")
    }
}

class MakeList(val make: MakeSynonym? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to @make a new list",
            "I would like to @make a list",
            "Can I @make a list",
            "@make a list",
            "new list"
        )
    }
}

class EditList(val edit: EditSynonym? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to @edit the existing list",
            "I would like to @edit the list",
            "I want to @edit the list",
            "Can I @edit the list",
            "@edit"
        )
    }
}

class InfoSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("info:info,information,info,profile,settings")
    }
}

class UpdateUserInfo(
    val edit: EditSynonym? = null,
    val info: InfoSynonym? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "i want to @edit my @info",
            "i want to @edit my user @info",
            "i would like to @edit my user @info",
            "@edit my user @info",
            "let me @edit my user @info"
        )
    }
}

class ExitSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("exit: exit, quit, return, go back")
    }
}

class Exit(val exit: ExitSynonym? = null) : Intent() {
    override fun getExamples(lang: Language) : List<String> {
        return listOf("@exit", "please @exit", "@exit please", "i want to @exit")
    }
}