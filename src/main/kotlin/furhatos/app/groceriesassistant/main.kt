package furhatos.app.groceriesassistant

import furhatos.app.groceriesassistant.flow.main.EditingList
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(EditingList)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
