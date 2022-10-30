package furhatos.app.groceriesassistant





import TestClassInterop.createShoppinglist
import TestClassInterop.createShoppinglogs
import furhatos.app.groceriesassistant.flow.Init
import furhatos.skills.Skill
import furhatos.flow.kotlin.*


class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {

//    createUserTable()
    createShoppinglist()
    createShoppinglogs()

//    inserter()

    Skill.main(args)

}
