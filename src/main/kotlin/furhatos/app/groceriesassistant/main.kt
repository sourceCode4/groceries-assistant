package furhatos.app.groceriesassistant

import furhatos.app.groceriesassistant.flow.main.SelectInteraction
import furhatos.skills.Skill
import furhatos.flow.kotlin.*


class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(SelectInteraction)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}
