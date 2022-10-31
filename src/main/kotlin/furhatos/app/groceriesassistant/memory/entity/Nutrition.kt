package furhatos.app.groceriesassistant.memory.entity

open class Nutrition(val calories: Int,
                      val protein: Int,
                      val carbs: Int,
                      val fats: Int,
                      var diet: Diet)
val MockNutrition = Nutrition(0, 0, 0, 0, Diet.OMNIVORE)

enum class Diet {
    VEGAN, VEGETARIAN, PESCETARIAN, OMNIVORE
}