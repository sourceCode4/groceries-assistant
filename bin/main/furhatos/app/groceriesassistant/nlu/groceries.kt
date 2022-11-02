package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.ListEntity
import furhatos.nlu.common.Number
import furhatos.util.Language

/**
 *  Broadest classification of groceries: e.g. fish, milk, bread, meat
 */
class GroceryCategoryEntity : EnumEntity(stemming = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "apple", "tomato", "chocolate", "ice cream", "fish",
            "salmon", "tuna", "steak", "burger", "veggie_burger:veggie burger, vegetarian burger",
            "mayonnaise:mayonnaise,mayo")
    }
}

/**
 *  Kind of the item from a category: e.g. cod fish, full fat milk, rye bread, beef steak
 */
class GroceryKindEntity(
    val category: GroceryCategoryEntity? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("banana", "apple", "tomato", "milk chocolate", "dark chocolate",
            "white chocolate", "vanilla ice cream", "chocolate ice cream", "cod fish", "salmon", "wild salmon",
            "tuna", "steak", "beef burger", "veggie burger", "mayonnaise", "@category")
    }
}

/**
 *  The specific item available in the database: e.g. Albert Heijn brand cod fish, Campina full fat milk...
 *      -> might not be used in the interest of time
 */
class GroceryItemEntity : EnumEntity() {}

class QuantifiedGroceryEntity(
    val count: Number? = Number(1),
    val grocery: GroceryKindEntity? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "@count @grocery",
            "@count more @grocery",
            "@count packs of @grocery",
            "@grocery", "a @grocery", "an @grocery"
        )
    }
}
class GroceryListEntity : ListEntity<QuantifiedGroceryEntity>()

class AddGrocery(
    val grocery: QuantifiedGroceryEntity? = null
) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@grocery",
            "I would like to add @grocery",
            "I want @grocery",
            "add @grocery",
            "another @grocery"
        )
    }
}