package furhatos.app.groceriesassistant.nlu

import furhatos.nlu.ComplexEnumEntity
import furhatos.nlu.EnumEntity
import furhatos.nlu.common.Number
import furhatos.nlu.common.PersonName as Name
import furhatos.util.Language

class Name : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        val name = Name()
        return name.getEnum(Language.ENGLISH_US) +
                name.getEnum(Language.DUTCH) +
                name.getEnum(Language.CROATIAN)
    }
}
enum class FieldEnum { HEIGHT, WEIGHT, AGE, SEX, DIET }
class UserField : EnumEntity(stemming = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            //"name",
            "height:height,tall",
            "weight:weight,weigh",
            "age:age,old",
            "sex",
            "diet")
    }
    override fun toText(): String {
        return super.toText().toLowerCase()
    }
    val type get() = when (text) {
        //"name" -> PersonName::class
        "height", "weight", "age" -> Number::class
        "sex"  -> Sex::class
        "diet" -> Diet::class
        else -> null
    }

    val enum get() = when (text) {
        //"name" -> FieldEnum.NAME
        "height" -> FieldEnum.HEIGHT
        "weight" -> FieldEnum.WEIGHT
        "age"    -> FieldEnum.AGE
        "sex"    -> FieldEnum.SEX
        "diet"   -> FieldEnum.DIET
        else     -> null
    }
}

class Sex : EnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf("male:man,boy,male,mail", "female:woman,girl,female")
    }
    override fun toText(): String {
        return when (super.text) {
            "man","boy","male","mail" -> "male"
            "woman","girl","female"   -> "female"
            else -> text
        }
    }

    val enum get() = when (toText()) {
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
    //val name: PersonName? = null,
    val number: Number? = null,
    val sex: Sex? = null,
    val diet: Diet? = null
) : ComplexEnumEntity() {
    override fun getEnum(lang: Language): List<String> {
        return listOf(/*"@name", */"@number", "@sex", "@diet")
    }
    val type get() = when {
        //name != null -> PersonName::class
        number != null -> Number::class
        sex != null -> Sex::class
        diet != null -> Diet::class
        else -> null
    }
}