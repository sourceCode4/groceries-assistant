import furhatos.app.groceriesassistant.memory.entity.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DatabaseConnection {

    private final Connection c;
    private final Statement stmt;

    public DatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection(URL, USER, PWD);
        stmt = c.createStatement();
    }

    public void close() throws SQLException {
        stmt.close();
        c.close();
    }

    final String URL = "jdbc:postgresql://localhost:5432/groceriesassistant";
    final String USER = "postgres";
    final String PWD  = "admin";
    public User getUser(String name) throws SQLException {
        //TODO: if the user with this name exists, return that,
        //  otherwise return null

        String sql = "SELECT *  FROM users WHERE users.name = '" + name +"'";

        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next() == false){
            return null;
        }

        String username = rs.getString(2);

        int height = Integer.parseInt(rs.getString(3));
        int weight = Integer.parseInt(rs.getString(4));
        int age = Integer.parseInt(rs.getString(5));
        Sex sex = Objects.equals(rs.getString(6), "MALE") ? Sex.MALE : Sex.FEMALE;
        String dietName = rs.getString(11);
        Diet diet;
        if (dietName.equals("VEGAN")) {
            diet = Diet.VEGAN;
        } else if (dietName.equals("VEGETARIAN")) {
            diet = Diet.VEGETARIAN;
        } else if (dietName.equals("PESCATERIAN")) {
            diet = Diet.PESCETARIAN;
        } else {
            diet = Diet.OMNIVORE;
        }

        Nutrition nutrition = new Nutrition(-1,-1,-1,-1, diet);

        User user = new User(username,height,weight,age,sex,nutrition);

        System.out.println(user);

        return user;
    }

    public List<String> getKinds() throws SQLException {
        String query = "SELECT DISTINCT subgroup FROM food";
        ResultSet rs = stmt.executeQuery(query);
        List<String> kinds = new LinkedList<>();
        while (rs.next()) kinds.add(rs.getString(1));
        return kinds;
    }

    public void updateUser(User user) throws SQLException {
        //TODO:update the age, weight, height, sex and diet
        //assume it is an existing user
        String name = user.getName();
        int age = user.getAge();
        int weight = user.getWeight();
        int height = user.getHeight();
        Nutrition nutrition = user.nutrition;
        int calories = nutrition.getCalories();
        int protein = nutrition.getProtein();
        int carbs = nutrition.getCarbs();
        int fats = nutrition.getFats();
        String sex = user.getSex().toString();
        Diet diet = user.getNutrition().getDiet();

        String sql = "UPDATE users SET (name, age, weight, height, sex, calories, protein, carbs, fats, diet) = " +
                "('" + name + "','" + age + "','" + weight + "','" + height + "','" + sex + "','" +
                        calories +"','" + protein + "','" + carbs + "','" + fats + "','" + diet + "')" +
                        "WHERE name = '" + name + "'";

        stmt.executeUpdate(sql);
    }
    public void addShoppingTable(String x) throws SQLException {
        String sql =
                "INSERT INTO shopping(userid, foodid, COUNT, PREF)" +
                        "SELECT (SELECT id FROM users WHERE users.name = '" + x + "'), id, 0 as COUNT, 1 as PREF FROM FOOD";

        stmt.executeUpdate(sql);

    }

    public void addNewUser(User user) throws SQLException {
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

        String sql =
                "INSERT INTO users(name, age, weight, height, sex, calories, protein, carbs, fats, diet)" +
                        "VALUES ('" + name + "','" + age + "','" + weight + "','" + height + "','" + sex + "','" + calories +"','" + protein + "','" + carbs + "','" + fats + "','" + diet + "')";

        stmt.executeUpdate(sql);

        addShoppingTable(name);
    }

    public void currentList(String userName, HashMap<Grocery, Integer> list) throws SQLException {
        list.clear();

            String sql =
                    "SELECT food.id, name, subgroup, calories, protein, carbs, fat, diet, count FROM shopping" +
                            "            JOIN food ON shopping.foodid = food.id" +
                            "            WHERE shopping.userid = (select id from users where name = '" + userName + "') AND count > 0";

        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            int id = rs.getInt(1);
            String name = rs.getString(2);
            String subgrounp = rs.getString(3);
            String temp = rs.getObject(8).toString();

            if (temp.equals("VEGAN")) {
                Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.VEGAN);
                Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                list.put(grocery, rs.getInt(9));
            } else if (temp.equals("VEGETARIAN")) {
                Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.VEGETARIAN);
                Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                list.put(grocery, rs.getInt(9));
            } else if (temp.equals("PESCATERIAN")) {
                Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.PESCETARIAN);
                Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                list.put(grocery, rs.getInt(9));
            } else if (temp.equals("OMNIVORE")) {
                Nutrition nutrition = new Nutrition(rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), Diet.OMNIVORE);
                Grocery grocery = new Grocery(id, name, subgrounp, nutrition);
                list.put(grocery, rs.getInt(9));

            }
        }
    }

    private void setPreferenceVector() {
        try {
            Process p = Runtime.getRuntime().exec("recommendations.py");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void overwriteList(String userName, HashMap<Grocery, Integer> newList) throws SQLException {
        //TODO: overwrite the current shopping list in the database with the new list

        String query = "SELECT id FROM users WHERE name = '" + userName + "'";
        ResultSet rs = stmt.executeQuery(query);
        rs.next();
        int userId = rs.getInt(1);

        query = "UPDATE shopping SET count = 0 WHERE userid = " + userId;
        stmt.executeUpdate(query);

        c.setAutoCommit(false);
        for (Map.Entry<Grocery, Integer> entry : newList.entrySet()) {
            Grocery grocery = entry.getKey();
            int count = entry.getValue();
            int foodId = grocery.getId();

            query = "UPDATE shopping SET count = '" + count + "'" +
                        "WHERE userid = " + userId + " AND shopping.foodid = '" + foodId + "' ";

            stmt.executeUpdate(query);
            setPreferenceVector();
        }
        c.commit();
        c.setAutoCommit(true);
    }

    private List<Grocery> toGroceryList(ResultSet rs) throws SQLException {
        ArrayList<Grocery> list = new ArrayList<>();
        while(rs.next()){
            //Display values
            Diet d = Diet.OMNIVORE;
            switch(rs.getString("Diet")){
                case ("VEGAN"): d = Diet.VEGAN;
                    break;
                case ("VEGETARIAN"): d = Diet.VEGETARIAN;
                    break;
                case ("PESCATERIAN"): d = Diet.PESCETARIAN;
                    break;
            }

            Grocery g = new Grocery(rs.getInt("id"), rs.getString("name"), rs.getString("subgroup"),
                    new Nutrition(rs.getInt("calories"), rs.getInt("protein"),
                            rs.getInt("carbs"), rs.getInt("fat"), d));
            list.add(g);
        }

        return list;
    }

    public List<Grocery> searchGroceries(String username, String item) throws SQLException {
        String query =  "SELECT * FROM food " +
                "JOIN shopping ON food.id = foodid " +
                "WHERE userid = (SELECT id FROM users WHERE name = '" + username +"') " +
                "AND '" + item + "' ILIKE '%' || subgroup || '%' "+
                "ORDER BY pref DESC " +
                "LIMIT 10";
        ResultSet rs = stmt.executeQuery(query);
        return toGroceryList(rs);
    }

    public List<Grocery> recommendItems(String username) throws SQLException {
        String query = "SELECT * FROM food " +
                        "JOIN shopping ON food.id = foodid " +
                        "WHERE userid = (SELECT id FROM users WHERE name = '" + username +"') " +
                        "ORDER BY pref DESC " +
                        "LIMIT 10";
        ResultSet rs = stmt.executeQuery(query);
        return toGroceryList(rs);
    }

    private String acceptableDiets(User user) {
        int max = user.nutrition.getDiet().getAsInt();
        String res = "";
        for (int n = 0; n < max; n++) {
            res += "diet ILIKE '" + Diet.Companion.fromInt(n) + "' OR ";
        }
        return res + "diet ILIKE '" + Diet.Companion.fromInt(max) + "'";
    }
    public List<Grocery> recommendSimilar(
        User user, Grocery item, int weight, int currentCalories, int maxCalories
    ) throws SQLException {
        Nutrition info = item.getInfo();
        String username = user.getName();
        String query = "SELECT * FROM food " +
                            "JOIN shopping ON food.id = foodid " +
                            "WHERE userid = (SELECT id FROM users WHERE name = '" + username + "') " +
                            "AND (" + acceptableDiets(user) + ")" +
                            " AND calories * " + (weight / 100) + " + " + currentCalories + "<" + maxCalories +
                            " AND NOT maingroup = 'Alcoholic beverages'" +
                            " ORDER BY " +
                            "abs(calories - " + info.getCalories() + ") + " +
                            "abs(protein - " + info.getProtein() + ") + " +
                            "abs(carbs - " + info.getCarbs() + ") + " +
                            "abs(fat - " + info.getFats() + ") ASC, pref DESC " +
                            "LIMIT 10";
        System.out.println(query);
        ResultSet rs = stmt.executeQuery(query);
        return toGroceryList(rs);
    }
}
