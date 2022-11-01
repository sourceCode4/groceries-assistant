package furhatos.app.groceriesassistant





import DatabaseBuilder
import DatabaseBuilder.ana
import DatabaseBuilder.build
import Queries.getItemsForRecommendation
import Queries.setPreferenceVector
import furhatos.app.groceriesassistant.flow.Init
import furhatos.flow.kotlin.*
import furhatos.skills.Skill


class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
//    val a: String = "c"
//    getUser(a);

//    val username: String = "a"
//
//    val height: Int = 6
//    val weight: Int = 6
//    val age: Int = 6
//    val sex = Sex.FEMALE
//
//    val nutrition = Nutrition(-1, -1, -1, -1, Diet.VEGAN)
//
//    val user = User(username, height, weight, age, sex, nutrition)
//
//
//    getPreferenceVector(username)
    //getItemsForRecommendation();
//    ana();
    setPreferenceVector()
}
