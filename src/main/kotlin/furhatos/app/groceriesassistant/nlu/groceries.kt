package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language

class GroceriesSynonym : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("groceries:groceries,food,stuff,produce,goods")
    }
}

/**
 *  Broadest classification of groceries: e.g. fish, milk, bread, meat
 */
class GroceryCategory : EnumEntity(stemming = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "apple", "tomato", "chocolate", "ice cream", "fish",
            "salmon", "tuna", "steak", "burger", "veggie_burger:veggie burger, vegetarian burger",
            "mayonnaise:mayonnaise,mayo")
    }
}

/**
 *  Kind of the item from a category: e.g. cod fish, full fat milk, rye bread, beef steak
 */
class GroceryKind(
    val category: GroceryCategory? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return mutableListOf("banana", "apple", "tomato", "milk chocolate", "dark chocolate",
            "white chocolate", "vanilla ice cream", "chocolate ice cream", "cod fish", "salmon", "wild salmon",
            "tuna", "steak", "beef burger", "veggie burger", "mayonnaise", "@category")
    }
}

class QuantifiedGrocery(
    val count: Number? = null,
    val grocery: GroceryKind? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "@grocery",
            "some @grocery",
            "some more @grocery",
            "@count @grocery",
            "@count more @grocery",
            "@count packs of @grocery",
            "@grocery", "a @grocery", "an @grocery"
        )
    }

    val isUnspecifiedPlural
        get(): Boolean =
            count?.value == null && text.contains("some", ignoreCase = true)
}

class Groceries : ListEntity<QuantifiedGrocery>()