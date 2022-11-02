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

public class Queries {

    private final Connection c;
    private final Statement stmt;

    public Queries() throws SQLException, ClassNotFoundException {
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

        Nutrition nutrition = new Nutrition(-1,-1,-1,-1, Diet.VEGAN);

        User user = new User(username,height,weight,age,sex,nutrition);

        System.out.println(user);

        return user;
    }

    public void updateUser(User user) throws SQLException {
        //TODO:update the age, weight, height, sex and diet
        //assume it is an existing user
        String name = user.getName();
        int age = user.getAge();
        int weight = user.getWeight();
        int height = user.getHeight();
        String sex= user.getSex().toString();
        Diet diet = user.getNutrition().getDiet();

        String sql = "UPDATE users SET (age, weight, height, sex,diet) = " +
                        "('" + age + "', '" + weight + "','" + height + "','" + sex + "','" + diet + "')" +
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
    }

    public void overwriteList(String userName, HashMap<Grocery, Integer> newList) throws SQLException {
        //TODO: overwrite the current shopping list in the database with the new list

        String query = "SELECT id FROM users WHERE name = '" + userName + "'";
        int userId = stmt.executeQuery(query).getInt(1);

        //c.setAutoCommit(false);

        for (Map.Entry<Grocery, Integer> entry : newList.entrySet()) {
            Grocery grocery = entry.getKey();
            int count = entry.getValue();
            int foodId = grocery.getId();

            query = "UPDATE shopping SET count = '" + count + "'" +
                        "WHERE userid IN " + userId + " AND shopping.foodid = '" + foodId + "' ";

            stmt.executeUpdate(query);
        }
//            c.commit();
    }

    private List<Grocery> toGroceryList(ResultSet rs) throws SQLException {
        ArrayList<Grocery> list = new ArrayList<>();
        while(rs.next()){
            //Display values
            Diet d = Diet.OMNIVORE;
            switch(rs.getString("Diet")){
                case ("Vegan"): d = Diet.VEGAN;
                case ("Vegetarian"): d = Diet.VEGETARIAN;
                case ("Pescaterian"): d = Diet.PESCETARIAN;
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
                "AND Subgroup ILIKE '" + item + "' "+
                "ORDER BY pref DESC " +
                "LIMIT 10";
        ResultSet rs = stmt.executeQuery(query);
        return toGroceryList(rs);
    }

    public ArrayList<String> getPreferenceVector(String userName) throws SQLException {
        //TODO: return this user's preference array,
        // with each index matching the primary key of the food
        String query =  "SELECT foodid, pref FROM shopping8 " +
                "WHERE userid = " + 0 +
                " ORDER BY PREF" +
                " LIMIT 10";
        ArrayList<String> list = new ArrayList<>();
        ResultSet rs = stmt.executeQuery(query);
        while(rs.next()){
            //Display values
            System.out.print("Preference: " + rs.getString("PREF"));
            System.out.print("foodid: " + rs.getString("PREF"));
            list.add((rs.getString("foodid")) + ", "+rs.getString("PREF"));
        }

        return list;
    }

    public void setPreferenceVector() {
        try {
            Process p = Runtime.getRuntime().exec("recommendations.py");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try {
//            Connection connection = DriverManager.getConnection(URL, USER, PWD);
//            Statement stmt = connection.createStatement();
//
//            for(int i=0; i<prefs.length; i++) {
//
//                String sql =
//                        "UPDATE shopping" +
//                        "SET pref = '" + prefs[i] + "' " +
//                        "WHERE userid = (SELECT id FROM users WHERE name = '" + userName + "') " +
//                            "AND FoodID = " + i;
//
//                stmt.executeUpdate(sql);
//            }
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
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

    public int[][] getItemsForRecommendation() throws SQLException {
        String query = "SELECT shopping userid, foodid, amount FROM shopping";
        String query2 = "SELECT COUNT(*) FROM shopping";
        ResultSet count = stmt.executeQuery(query2);
        ResultSet rs = stmt.executeQuery(query);

        count.next();
        int num = count.getInt(1);
        int[][] result = new int[num][3];
        for(int i = 0; i<num; i++){
            rs.next();
            result[i][0] = rs.getInt(1);
            result[i][1] = rs.getInt(2);
            result[i][2] = rs.getInt(3);
        }
        return result;
    }
}
