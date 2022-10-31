package furhatos.app.groceriesassistant





import FoodTable.runner
import TestClassInterop.createShoppinglist
import TestClassInterop.createShoppinglogs
import TestClassInterop.createUserTable
import furhatos.app.groceriesassistant.flow.Init
import furhatos.skills.Skill
import furhatos.flow.kotlin.*


class GroceriesassistantSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {

        runner()
//    createUserTable()
//    createShoppinglist()
//    createShoppinglogs()

//    inserter()

    Skill.main(args)
}
