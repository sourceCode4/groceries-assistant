package furhatos.app.groceriesassistant.memory.entity

open class Nutrition(val calories: Int,
                      val protein: Int,
                      val carbs: Int,
                      val fats: Int,
                      var diet: Diet)
val EmptyNutrition = Nutrition(0, 0, 0, 0, Diet.OMNIVORE)

enum class Diet : Comparable<Diet> {
    VEGAN, VEGETARIAN, PESCETARIAN, OMNIVORE;
    val asInt get() = when (this) {
        VEGAN -> 0
        VEGETARIAN -> 1
        PESCETARIAN -> 2
        OMNIVORE -> 3
    }

    companion object {
        fun fromInt(n: Int): Diet = when {
            n < 1 -> VEGAN
            n == 1 -> VEGETARIAN
            n == 2 -> PESCETARIAN
            else   -> OMNIVORE
        }
    }
}

enum class Compatibility {
    COMPATIBLE, DIET_RESTRICTION, CALORIES_EXCEEDED, INCOMPATIBLE
}