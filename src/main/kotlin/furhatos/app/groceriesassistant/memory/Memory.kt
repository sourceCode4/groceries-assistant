package furhatos.app.groceriesassistant.memory

import furhatos.app.groceriesassistant.memory.entity.Grocery
import furhatos.app.groceriesassistant.memory.entity.MockNutrition
import furhatos.app.groceriesassistant.memory.entity.User
import furhatos.app.groceriesassistant.nlu.FieldEnum
import furhatos.app.groceriesassistant.nlu.GroceryKind
import furhatos.app.groceriesassistant.nlu.UserFieldValue
import furhatos.nlu.common.Date

object Memory {

    private val state = object {
        lateinit var user: User
        val shoppingList: MutableMap<Grocery, Int> = mutableMapOf()
    }

    /**
     *  If a user under @name exists sets it to current and returns true,
     *  otherwise returns false
     */
    fun setUser(name: String): Boolean {
        //TODO: return the user from the database or return null if no user
        return true
    }


    fun updateUser(field: FieldEnum?, value: UserFieldValue): Boolean {
        with (state.user) {
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

    fun commitUser() = {
        val user = state.user //user this as the user information
        /*TODO: update the personal information of the user in the database
            -> can only be age, height, weight, sex, diet
        **/
    }

    /**
     *  Sets the new user to current and adds them to the database.
     */
    fun setNewUser(user: User) {
        state.user = user
        //TODO: adds this user to the database
    }

    /**
     * Loads user's current list from the database.
     */
    fun overloadCurrent() {
        //val name = state.user.name
        //TODO: return current shopping list (in hash map of grocery objects to ints)
    }

    /**
     *  If the current list is nonempty, saves it to the database and returns true,
     *  otherwise returns false
     */
    fun commit(): Boolean {
        if (state.shoppingList.isEmpty()) return false
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
            Grocery(0, "generic banana", "banana", MockNutrition),
            Grocery(1,  "generic $grocery", this.category?.text!!, MockNutrition))
    }

    fun getPreferenceVector() {

    }

    fun setPreferenceVector() {

    }

    fun currentList(): MutableMap<Grocery, Int> = state.shoppingList

    fun addItem(item: Grocery, amount: Int = 1) {
        state.shoppingList[item] = (state.shoppingList[item] ?: 0) + amount
    }

    fun newList() {
        state.shoppingList.clear()
    }

    /**
     *  Returns true if there was an entry with this item in the list
     */
    fun removeItem(item: Grocery): Boolean {
        return state.shoppingList.remove(item) != null
    }
}
