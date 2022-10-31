import furhatos.app.groceriesassistant.memory.entity.Grocery;
import furhatos.app.groceriesassistant.memory.entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

public class Queries {
    public static User getUser(String name) {
        //TODO: if the user with this name exists, return that,
        //  otherwise return null
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();


            String sql =
                    "INSERT INTO USERFOODTABLE(id, FOODKEY, COUNT, PREF)" +
                            "SELECT (SELECT id FROM userdata WHERE userdata.name = '" + x + "'), id, 0 as COUNT, 1 as PREF FROM FOOD";



            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

        return null;
    }

    public static void updateUser(User user) {
        //TODO:update the age, weight, height, sex and diet
        //assume it is an existing user
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
