package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName as Name
import furhatos.util.Language

class PersonName : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        val name = Name()
        return name.getEnum(Language.ENGLISH_US) +
                name.getEnum(Language.DUTCH) +
                name.getEnum(Language.CROATIAN)
    }
}

class UserField : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "name",
            "height:height,tall",
            "weight:weight,weigh",
            "age:age,old",
            "sex", "diet")
    }
}

class Sex : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("male", "female")
    }
    val memoryEntity get() = when (text) {
        "male" -> furhatos.app.groceriesassistant.memory.entity.Sex.MALE
        "female" -> furhatos.app.groceriesassistant.memory.entity.Sex.FEMALE
        else -> null
    }
}

class Diet : EnumEntity(speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf("omnivore", "pescetarian", "vegetarian", "vegan")
    }

    val memoryEntity get() = when (text) {
        "omnivore" -> furhatos.app.groceriesassistant.memory.entity.Diet.OMNIVORE
        "pescetarian" -> furhatos.app.groceriesassistant.memory.entity.Diet.PESCETARIAN
        "vegetarian" -> furhatos.app.groceriesassistant.memory.entity.Diet.VEGETARIAN
        "vegan" -> furhatos.app.groceriesassistant.memory.entity.Diet.VEGAN
        else -> null
    }
}

class UserFieldValue(
    val name: PersonName? = null,
    val number: Number? = null,
    val sex: Sex? = null,
    val diet: Diet? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("@name", "@number", "@sex", "@diet")
    }

    val type get() = when {
        name != null -> PersonName::class
        number != null -> Number::class
        sex != null -> Sex::class
        diet != null -> Diet::class
        else -> null
    }
}