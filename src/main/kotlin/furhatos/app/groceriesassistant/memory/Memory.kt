package furhatos.app.groceriesassistant.memory

import Queries
import furhatos.app.groceriesassistant.memory.entity.Grocery
import furhatos.app.groceriesassistant.memory.entity.EmptyNutrition
import furhatos.app.groceriesassistant.memory.entity.Nutrition
import furhatos.app.groceriesassistant.memory.entity.User
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.Groceries
import furhatos.app.groceriesassistant.nlu.GroceryKind
import furhatos.app.groceriesassistant.nlu.UserFieldValue

object Memory {

    private lateinit var user: User
    private val shoppingList: HashMap<Grocery, Int> = HashMap()
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
        val user = Queries.getUser(name)
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
        val user = user
        Queries.updateUser(user)
    }

    /**
     *  Sets the new user to current and adds them to the database.
     */
    fun setNewUser(user: User) {
        Queries.addNewUser(user)
        this.user = user
    }

    /**
     * Loads user's current list from the database.
     */
    fun overloadCurrent() {
        //val name = user.name
        //TODO: return current shopping list (in hash map of grocery objects to ints)
        Queries.currentList(userName)
    }

    /**
     *  If the current list is nonempty, saves it to the database and returns true,
     *  otherwise returns false
     */
    fun commit(): Boolean {
        if (shoppingList.isEmpty()) return false
        //TODO: put a shopping list into the database
        return true
    }

    /**
     *  Retrieves grocery items from the database that match the grocery entity
     *  asked for by the user
     */
    fun GroceryKind.getGroceryItems(): List<Grocery> {
        val grocery = this.text
        //TODO: search the database
        return listOf(
            Grocery(0, "generic banana", "banana", EmptyNutrition),
            Grocery(1,  "generic $grocery", this.category?.text!!, EmptyNutrition))
    }

    fun getPreferenceVector() {
        Queries.getPreferenceVector(userName)
    }

    fun setPreferenceVector() {
        //TODO: return array of preferences
    }

    fun currentList(): MutableMap<Grocery, Int> = shoppingList

    fun addItem(item: Grocery, amount: Int = 1) {
        shoppingList[item] = (shoppingList[item] ?: 0) + amount
    }

    fun newList() {
        shoppingList.clear()
    }

    /**
     *  Returns true if there was an entry with this item in the list
     */
    fun removeItem(item: Grocery): Boolean {
        return shoppingList.remove(item) != null
    }

    fun recommend(): List<Groceries> {
        return listOf()
    }
}
