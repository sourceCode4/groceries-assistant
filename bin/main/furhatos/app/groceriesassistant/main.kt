package furhatos.app.groceriesassistant

import furhatos.app.groceriesassistant.flow.Init
import furhatos.skills.Skill
import furhatos.flow.kotlin.*

class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
