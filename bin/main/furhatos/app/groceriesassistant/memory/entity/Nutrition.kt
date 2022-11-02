package furhatos.app.groceriesassistant.memory.entity

data class Nutrition(val calories: Int,
                     val protein: Int,
                     val carbs: Int,
                     val fats: Int,
                     val diet: Diet)

enum class Diet {
    VEGAN, VEGETARIAN, PESCETARIAN, OMNIVORE
}