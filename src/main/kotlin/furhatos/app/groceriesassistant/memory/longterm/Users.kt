package furhatos.app.groceriesassistant.memory.longterm
import furhatos.app.groceriesassistant.memory.entity.*

object Users {
    /* will be replaced by a database */
    private val users: HashMap<String, User> = hashMapOf("John" to User("John", 175, 75f, Diet()))

    fun isUser(name: String): Boolean = users.contains(name)

    operator fun get(name: String): User? = users[name]

    operator fun set(name: String, user: User): Boolean {
        users[name] = user
        return true
    }
}