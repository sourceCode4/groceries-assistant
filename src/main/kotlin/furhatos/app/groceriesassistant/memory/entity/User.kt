package furhatos.app.groceriesassistant.memory.entity

/**
 * User data object that is to correspond with
 * the database (long-term memory) user entry
 */
data class User(
    var name: String = "",
    var height: Int = -1,
    var weight: Int = -1,
    var age: Int = -1
) {
    lateinit var sex: Sex
    lateinit var nutrition: Nutrition

    constructor(
        name: String, height: Int = 165, weight: Int = 60, age: Int = 23,
        sex: Sex = Sex.MALE, nutrition: Nutrition = EmptyNutrition
    ) : this(name, height, weight, age) {
        this.sex = sex
        this.nutrition = nutrition
        calculateNutrition()
    }

    private fun getDailyCalories(): Int = when (sex) {
        Sex.FEMALE -> 655 + 9.6 * weight + 1.8 * height - 4.7 * age
        Sex.MALE   -> 66 + 13.7 * weight + 5 * height - 6.8 * age
    }.toInt()

    fun maxCalories(days: Int): Int = getDailyCalories() * days

    fun calculateNutrition() {
        val cals = getDailyCalories()
        this.nutrition = Nutrition(
            cals,
            (weight * 0.8).toInt(),
            (cals * 0.45).toInt(),
            (cals * 0.35).toInt(),
            nutrition.diet
        )
    }
}
enum class Sex { MALE, FEMALE }