package furhatos.app.groceriesassistant.memory

import DatabaseConnection
import furhatos.app.groceriesassistant.memory.entity.*
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.GroceryKind
import furhatos.app.groceriesassistant.nlu.UserFieldValue

object Memory {

    private lateinit var user: User
    private val database = DatabaseConnection()
    private val shoppingList: HashMap<Grocery, Int> = HashMap()
    private var totalCalories = 0
    private var days = 1
    private val userName get() = user.name
    fun initUser(name: String) {
        user = User(name = name)
        user.nutrition = EmptyNutrition
    }

    /**
     *  If a user under @name exists sets it to current and returns true,
     *  otherwise returns false
     */
    fun setUser(name: String): Boolean {
        val user = database.getUser(name)
        return if (user != null) {
            this.user = user
            true
        } else
            false
    }
    fun updateUser(field: FieldEnum?, value: UserFieldValue): Boolean {
        with (user) {
            when (field) {
                //"name" -> name = value.name?.text ?: return false
                FieldEnum.HEIGHT -> height = value.number?.value ?: return false
                FieldEnum.WEIGHT -> weight = value.number?.value ?: return false
                FieldEnum.AGE  -> age = value.number?.value ?: return false
                FieldEnum.SEX  -> sex = value.sex?.enum ?: return false
                FieldEnum.DIET -> nutrition.diet = value.diet?.memoryEntity ?: return false
                else -> return false
            }
        }
        return true
    }

    fun commitUser() {
        user.calculateNutrition()
        database.updateUser(user)
    }

    /**
     *  Sets the new user to current and adds them to the database.
     */
    fun setNewUser() {
        user.calculateNutrition()
        database.addNewUser(user)
    }

    /**
     * Loads user's current list from the database.
     */
    fun overloadCurrent() {
        database.currentList(userName, shoppingList)
    }

    /**
     *  If the current list is nonempty, saves it to the database and returns true,
     *  otherwise returns false
     */
    fun commit() {
        if (shoppingList.isNotEmpty())
            database.overwriteList(user.name, shoppingList)
    }

    fun getKinds(): List<String> {
        //TODO: temp for testing, call the database
        return database.kinds
//        listOf("banana", "apple", "tomato", "chocolate", "ice cream", "fish",
//            "salmon", "tuna", "steak", "burger", "veggie_burger:veggie burger, vegetarian burger",
//            "mayonnaise:mayonnaise,mayo")
    }

    /**
     *  Retrieves grocery items from the database that match the grocery entity
     *  asked for by the user
     */
    fun GroceryKind.getGroceryItems(): List<Grocery> {
        val grocery = this.text
        return database.searchGroceries(userName, grocery)
//             listOf(Grocery(0, "generic banana", "banana", EmptyNutrition),
//                   Grocery(1,  "generic $grocery", "generic", EmptyNutrition))
    }
    fun currentList(): MutableMap<Grocery, Int> = shoppingList

    fun addItem(item: Grocery, amount: Int = 100) {
        shoppingList[item] = (shoppingList[item] ?: 0) + amount
        totalCalories += item.info.calories * (amount / 100)
    }

    fun newList(days: Int) {
        this.days = days
        totalCalories = 0
        shoppingList.clear()
    }

    fun recommend(item: Grocery? = null, weight: Int = 100): List<Grocery> {
        return if (item == null)
            database.recommendItems(userName)
        else
            database.recommendSimilar(user, item, weight, totalCalories, user.maxCalories(days))
//        listOf("snickers", "twix", "bueno").map {
//            Grocery(1, it, "chocolate", EmptyNutrition)
    }

    fun compatibility(item: Grocery, grams: Int): Compatibility {
        val dietary = item.info.diet.asInt <= user.nutrition.diet.asInt
        val calorically =
            item.info.calories * (grams / 100) + totalCalories <= user.nutrition.calories
        return when (dietary to calorically) {
            false to false -> Compatibility.INCOMPATIBLE
            false to true  -> Compatibility.DIET_RESTRICTION
            true to false  -> Compatibility.CALORIES_EXCEEDED
            else           -> Compatibility.COMPATIBLE
        }
    }
}
