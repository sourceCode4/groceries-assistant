package furhatos.app.groceriesassistant.memory.entity

/**
 * User data object that is to correspond with
 * the database (long-term memory) user entry
 */
data class User(val name: String,
                val height: Int,
                val weight: Double,
                val age: Int,
                val sex: Sex,
                val diet: Diet) {

    fun getDailyCalories(): Double = when (sex) {
        Sex.FEMALE -> 655 + 9.6 * weight + 1.8 * height - 4.7 * age
        Sex.MALE   -> 66 + 13.7 * weight + 5 * height - 6.8 * age
    }
}

enum class Sex { MALE, FEMALE }