package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.Intent
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName
import furhatos.util.Language

class AddGroceries(
    val groceries: Groceries? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@groceries",
            "I would like to add @groceries",
            "I want @groceries",
            "add @groceries",
            "another @groceries"
        )
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
            "@buy @groceries",
            "@groceries")
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
            "I want to @edit my list",
            "Can I @edit the list",
            "@edit"
        )
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


class Exit(val exit: ExitSynonym? = null) : Intent() {
    override fun getExamples(lang: Language) : List<String> {
        return listOf("@exit", "please @exit", "@exit please", "i want to @exit")
    }
}

class Done : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("I'm done", "that's it", "that would be it",
                      "that is all", "nothing else")
    }
}
abstract class InfoIntent(
    val field: UserField? = null,
    val value: UserFieldValue? = null
) : Intent() {
    val fieldName get() = field?.text
    val hasValue get() = value != null
    val isValid get() = value != null && with (value) {
        when (fieldName) {
            "name"   -> type == PersonName::class
            "number" -> type == Number::class
            "sex"    -> type == Sex::class
            "diet"   -> type == Diet::class
            else     -> false
        }
    }
}

open class GiveUserInfo(
    field: UserField? = null,
    value: UserFieldValue? = null
) : InfoIntent(field, value) {
    override fun getExamples(lang: Language): List<String> {
        return listOf("my @field is @value", "@field @value")
    }
}

class EditUser(
    field: UserField? = null,
    value: UserFieldValue? = null,
    val edit: EditSynonym? = null
) : GiveUserInfo(field, value) {
    override fun getExamples(lang: Language): List<String> {
        return super.getExamples(lang) +
            listOf("I want to @edit my @field",
                "@field", "@edit the @field",
                "@edit how much i weigh",
                "@edit how tall i am",
                "@edit how old i am",
                "I want to @edit my @field to @value",
                "@edit @field to @value",
                "i want to @edit how much i weigh to @value",
                "@edit how tall i am to @value",
                "@edit how old i am to @value")
    }

}