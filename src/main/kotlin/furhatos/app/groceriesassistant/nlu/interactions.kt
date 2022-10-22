package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class ActionBuy : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("buy:buy,get,purchase,pick up,shop for,procure")
    }
}

class Groceries : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("groceries:groceries,food,stuff,produce,goods")
    }
}

class DoGroceries(
    val groceries: Groceries? = null,
    val buy: ActionBuy? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to do some groceries",
            "I want to @buy some @groceries",
            "@buy @groceries")
    }
}

class ActionMake : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("make:make,create,assemble,put together")
    }
}

class ActionEdit : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("edit:edit,modify,change,refine")
    }
}

class MakeList(val make: ActionMake? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to @make a new list",
            "I would like to @make a list",
            "Can I @make a list"
        )
    }
}

class EditList(val edit: ActionEdit? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to @edit an existing list",
            "I would like to @edit a list",
            "I want to @edit a list",
            "Can I @edit a list"
        )
    }
}