package furhatos.app.groceriesassistant

import furhatos.app.groceriesassistant.flow.Init
import furhatos.flow.kotlin.Flow
import furhatos.skills.Skill


class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
//    val lineReader = BufferedReader(FileReader("newdata.csv"))
}
