package furhatos.app.groceriesassistant.memory.entity

/**
 *  Grocery item, corresponding to a food database entry,
 *  name is assumed to be unique
 */
data class Grocery(
        val id: Int,
        val name: String,
        val subgroup: String,
        val info: Nutrition) {

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Grocery -> other.name == this.name
            else -> false
        }
    }
}