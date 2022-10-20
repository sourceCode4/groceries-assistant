package furhatos.app.groceriesassistant.memory.entity

/**
 * User data object that is to correspond with
 * the database (long-term memory) user entry
 */
data class User(val name: String,
                val height: Int,
                val weight: Float,
                val diet: Diet)

class Diet

data class Nutrition(val calories: Int,
                    val protein: Int,
                    val carbs: Int,
                    val fats: Int,
                    val diet: Diet)

data class FoodItem(val name: String,
                    val info: Nutrition)