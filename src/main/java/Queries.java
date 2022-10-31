import furhatos.app.groceriesassistant.memory.entity.Grocery;
import furhatos.app.groceriesassistant.memory.entity.User;

import java.util.HashMap;
import java.util.List;

public class Queries {
    public static User getUser(String name) {
        //TODO: if the user with this name exists, return that,
        //  otherwise return null
        return null;
    }

    public static void updateUser(User user) {
        //TODO:update the age, weight, height, sex and diet
        // assume it is an existing user
    }

    public static void addNewUser(User user) {
        //TODO:add this new user to the database
    }

    public static HashMap<Grocery, Integer> currentList(String userName) {
        //TODO: return current users shopping list
        return null;
    }

    public static void overwriteList(String userName, HashMap<Grocery, Integer> newList) {
        //TODO: overwrite the current shopping list in the database with the new list
    }

    public static List<String> getCategories() {
        //TODO: return all the category values
        return null;
    }

    public static List<Grocery> searchGroceries(String userName, String input) {
        //TODO: return the list of groceries that match the input,
        // ordered by this user's preference
        return null;
    }

    public static float[] getPreferenceVector(String userName) {
        //TODO: return this user's preference array,
        // with each index matching the primary key of the food
        return null;
    }

    public static void setPreferenceVector(String userName, float[] prefs) {
        //TODO: set prefs to be new preference values
    }
}
