package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.util.Language

class Buy : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("buy", "get", "get", "procure")
    }
}

class Groceries : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("groceries", "food", "stuff")
    }
}

class DoGroceries(
    val groceries: Groceries? = null,
    val buy: Buy? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to do some groceries",
            "I want to @buy some @groceries",
            "@buy @groceries")
    }
}

class MakeList : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I want to make a new list")
    }
}

class EditList : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "I want to edit an existing list",
            "I want to modify an existing list"
        )
    }
}