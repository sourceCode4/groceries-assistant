package furhatos.app.groceriesassistant.memory.entity

/**
 * User data object that is to correspond with
 * the database (long-term memory) user entry
 */
data class User(
    var name: String,
    var height: Int,
    var weight: Int,
    var age: Int,
    var sex: Sex,
    var nutrition: Nutrition
) {
    fun getDailyCalories(): Double = when (sex) {
        Sex.FEMALE -> 655 + 9.6 * weight + 1.8 * height - 4.7 * age
        Sex.MALE   -> 66 + 13.7 * weight + 5 * height - 6.8 * age
    }
}
enum class Sex { MALE, FEMALE }