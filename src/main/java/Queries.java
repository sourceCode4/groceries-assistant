import furhatos.app.groceriesassistant.memory.entity.*;

import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
                    "SELECT *  FROM USERDATA WHERE userdata.name = '" + name +"'";

            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next() == false){
                return null;
            }
            rs.next();

            String username = rs.getString(2);

            int height = Integer.parseInt(rs.getString(3));
            int weight = Integer.parseInt(rs.getString(4));
            int age = Integer.parseInt(rs.getString(5));
            Sex sex = Objects.equals(rs.getString(6), "MALE") ? Sex.MALE : Sex.FEMALE;

            Nutrition nutrition = new Nutrition(-1,-1,-1,-1, Diet.VEGAN);

            User user =new User(username,height,weight,age,sex,nutrition);

            System.out.println(user);

            stmt.close();
            c.close();

            return user;

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
        String name = user.getName();
        int age = user.getAge();
        int weight = user.getWeight();
        int height = user.getHeight();
        String sex= user.getSex().toString();
        Diet diet = user.getNutrition().getDiet();

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
                    "UPDATE userdata SET (age, weight, height, sex,diet) = ('" + age + "', '" + weight + "','" + height + "','" + sex + "','" + diet + "')" +
                     "WHERE name = '" + name + "'";



            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }



    }

    public static void addNewUser(User user) {
        //TODO:add this new user to the database
        String name = user.getName();
        int age = user.getAge();
        int weight = user.getWeight();
        int height = user.getHeight();
        String sex= user.getSex().toString();
        int calories = user.getNutrition().getCalories();
        int protein = user.getNutrition().getProtein();
        int carbs = user.getNutrition().getCarbs();
        int fats = user.getNutrition().getFats();
        Diet diet = user.getNutrition().getDiet();


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();

//            INSERT INTO userdata(name, age, weight, height, sex, calories, protein, carbs, fats, diet)
//            VALUES (5,5,5,5,'FEMALE',5,5,5,5,'VEGAN');

            String sql =
                    "INSERT INTO userdata(name, age, weight, height, sex, calories, protein, carbs, fats, diet)" +
                            "VALUES ('" + name + "','" + age + "','" + weight + "','" + height + "','" + sex + "','" + calories +"','" + protein + "','" + carbs + "','" + fats + "','" + diet + "')";



            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

            TestClassInterop.addUserFoodTable(name);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static HashMap<Grocery, Integer> currentList(String userName) {
        //TODO: return current users shopping list
/*        SELECT food.id, name, subgroup, calories, protein, carbs, fat, diet, count FROM userfoodtable
            JOIN food ON userfoodtable.foodkey = food.id
            WHERE userfoodtable.id = (select id from userdata where name = 'a') AND count > 0 */

        Connection c = null;
        Statement stmt = null;
        HashMap<Grocery, Integer> list = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();

            String sql =
                    "SELECT food.id, name, subgroup, calories, protein, carbs, fat, diet, count FROM userfoodtable" +
                            "            JOIN food ON userfoodtable.foodkey = food.id" +
                            "            WHERE userfoodtable.id = (select id from userdata where name = 'a') AND count > 0";

//            ResultSet rs = stmt.executeQuery(sql);
//            rs.next();
//            System.out.println(rs.getObject(8));

//            AND count > 0

            list = new HashMap<Grocery, Integer>();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String subgrounp = rs.getString(3);
                String temp = rs.getObject(8).toString();

                if (temp.equals("Vegan")) {
                    Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.VEGAN);
                    Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                    list.put(grocery, rs.getInt(9));
                } else if (temp.equals("Vegetarian")) {
                    Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.VEGETARIAN);
                    Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                    list.put(grocery, rs.getInt(9));
                } else if (temp.equals("Pescaterian")) {
                    Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.PESCETARIAN);
                    Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                    list.put(grocery, rs.getInt(9));
                } else if (temp.equals("Omnivore")) {
                    Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.OMNIVORE);
                    Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                    list.put(grocery, rs.getInt(9));

                }
            }

//            System.out.println(list);

            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        return list;
    }

    public static void overwriteList(String userName, HashMap<Grocery, Integer> newList) {
        //TODO: overwrite the current shopping list in the database with the new list


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");




            Iterator<HashMap.Entry<Grocery, Integer>> iterator = newList.entrySet().iterator();
            while(iterator.hasNext()){
                stmt = c.createStatement();
                Map.Entry<Grocery, Integer> entry = iterator.next();
                Grocery grocery = entry.getKey();
                int count = entry.getValue();
                int foodId = grocery.getId();

                String sql =

                        "UPDATE userfoodtable SET count = '"+count+"'" +

                                "WHERE id IN (SELECT id FROM userdata WHERE userdata.name = '" + userName + "') AND userfoodtable.foodkey = '" + foodId + "' ";



                stmt.executeUpdate(sql);
                stmt.close();
            }

            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static List<Grocery> searchGroceries(String userName, String input) {
        //TODO: return the list of groceries that match the input,
        // ordered by this user's preference
        return null;
    }

    public static ArrayList<Float> getPreferenceVector(String userName) {
        //TODO: return this user's preference array,
        // with each index matching the primary key of the food
        String DB_URL = "";
        String USER = "";
        String PASS = "";
        String query = "SELECT Preference FROM (TABLE NAME) WHERE Username LIKE " + userName +" ORDER BY Foodname";
        ArrayList<Float> list = new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            while(rs.next()){
                //Display values
                System.out.print("Preference: " + rs.getString("Preference"));
                list.add(Float.valueOf(rs.getString("Preference")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void setPreferenceVector(String userName, float[] prefs) {
        //TODO: set prefs to be new preference values
    }
}
