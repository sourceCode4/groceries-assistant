package furhatos.app.groceriesassistant.memory

import furhatos.app.groceriesassistant.memory.entity.Grocery
import furhatos.app.groceriesassistant.memory.entity.MockNutrition
import furhatos.app.groceriesassistant.memory.entity.User
import furhatos.app.groceriesassistant.nlu.GroceryKind
import furhatos.app.groceriesassistant.nlu.UserFieldValue
import furhatos.nlu.common.Date

object Memory {

    private val state = object {
        lateinit var user: User
        /**
         *  for how many days the groceries should last, max 7
         *  use this to calculate the required amount of groceries when composing a list
         */
        var duration: Int = 0
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

    fun getUser(): User = state.user

    fun updateUser(fieldName: String, value: UserFieldValue): Boolean {
        with (state.user) {
            when (fieldName) {
                "name" -> name = value.name?.text ?: return false
                "height", "weight", "age"
                    -> height = value.number?.value ?: return false
                "sex" -> sex = value.sex?.memoryEntity ?: return false
                "diet" -> diet.diet = value.diet?.memoryEntity ?: return false
                else -> return false
            }
        }
        //TODO: commit to the database
        return true
    }

    /**
     *  Sets the new user to current and adds them to the database.
     */
    fun setNewUser(user: User) {
        state.user = user
        //TODO:
    }

    /**
     * Loads user's current list from the database.
     */
    fun overloadCurrent() {
        //val name = state.user.name
        //TODO:
    }
    fun currentList(): MutableMap<Grocery, Int> = state.shoppingList

    fun addItem(item: Grocery, amount: Int = 1) {
        state.shoppingList[item] = (state.shoppingList[item] ?: 0) + amount
    }

    /**
     *  Returns true if there was an entry with this item in the list
     */
    fun removeItem(item: Grocery): Boolean {
        return state.shoppingList.remove(item) != null
    }

    /**
     *
     */
    fun newList(archiveCurrent: Boolean) {
        if (archiveCurrent) {
            //TODO: save current list to the database
        }
        state.shoppingList.clear()
    }

    /**
     *  Return the list from the given date, if it doesn't exist return null
     */
    fun retrieveList(dateEntity: Date): List<Grocery>? {
        val date = dateEntity.asLocalDate() ?: return null
        //TODO: use the date to retrieve from the database
        return null
    }

    /**
     *  Forgets the list and returns true if it exists,
     *  false otherwise
     */
    fun forgetList(dateEntity: Date): Boolean {
        val date = dateEntity.asLocalDate() ?: return false
        //TODO: forget the list or return false if there is none
        return true
    }

    /**
     *  If the current list is nonempty, saves it to the database and returns true,
     *  otherwise returns false
     */
    fun commit(): Boolean {
        if (state.shoppingList.isEmpty()) return false
        //TODO: commit the current list to the database
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
            Grocery("generic banana", "banana", MockNutrition()),
            Grocery("generic chocolate bar", "chocolate", MockNutrition()))
    }
}
