import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class buildDatabase {

    public static void run(){
        build();
    }
    static String csvFilePath = "C:\\Users\\lukaj\\Code\\Repos\\GroceriesAssistant\\src\\main\\java\\newdata.csv";
    static String DB_URL = "jdbc:postgresql://localhost:5432/test";
    static String USER = "postgres";
    static String PASS = "admin";

    public static void query(String s){
        String QUERY2 = "SELECT * FROM FOOD WHERE name Like '%"+s+"%'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY2);
        ) {
            while(rs.next()){
                //Display values
                System.out.print("name: " + rs.getString("name"));
                System.out.println(", subgroup: " + rs.getString("subgroup"));
                System.out.print(", Group: " + rs.getString("Group"));
                System.out.print(", diet: " + rs.getString("diet"));
                System.out.print(", calories: " + rs.getString("calories"));
                System.out.print(", protein: " + rs.getString("protein"));
                System.out.print(", carbs: " + rs.getString("carbs"));
                System.out.println(", fat: " + rs.getString("fat"));
                //System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void build(){
        try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();
        ) {
            //Category, Englishname, NEVOCode, EnergyKJ, EnergyKCAL
            String querry = "CREATE TABLE FOOD " +
                    "(id SERIAL PRIMARY KEY," +
                    " name VARCHAR(255), " +
                    " subgroup VARCHAR(255), " +
                    " maingroup VARCHAR(255), " +
                    " diet VARCHAR(255), " +
                    " calories DECIMAL(7,2), " +
                    " protein DECIMAL(7,2), " +
                    " carbs DECIMAL(7,2), " +
                    " fat DECIMAL(7,2)) ";

            stmt.executeUpdate(querry);

            System.out.println("Created table in given database...");

            connection.setAutoCommit(false);
            //name, subgroup, Group, diet, calories, protein, carbs, fat
            querry = "INSERT INTO FOOD (name, subgroup, maingroup, diet, calories, protein, carbs, fat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(querry);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {

                String[] data = lineText.split(",");
                String name = data[3].replace('_', ' ');
                String subgroup = data[2].replace('_', ' ');
                String maingroup = data[1].replace('_', ' ');
                String diet = data[0];
                String calories = data[6];
//                calories.replace(".", ",");
                String protein = data[7];
//                protein.replace(".", ",");
                String carbs = data[9];
//                carbs.replace(".", ",");
                String fat = data[8];
//                fat.replace(".", ",");

                statement.setString(1, name);
                statement.setString(2, subgroup);
                statement.setString(3, maingroup);
                statement.setString(4, diet);
                statement.setFloat(5, Float.valueOf(calories) );
                statement.setFloat(6, Float.valueOf(protein));
                statement.setFloat(7, Float.valueOf(carbs));
                statement.setFloat(8, Float.valueOf(fat));

                statement.addBatch();
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
        } catch (Exception e) {
            System.out.println("database setup failed");
            e.printStackTrace();
        }
    }

    public static void createUserTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            System.out.println("connected");


            stmt = c.createStatement();

            String sql =    "CREATE TABLE user " +
                    "(id SERIAL PRIMARY KEY," +
                    " NAME TEXT UNIQUE NOT NULL," +
                    " HEIGHT            INT, " +
                    " WEIGHT            INT, " +
                    " AGE               INT, " +
                    " SEX               TEXT," +
                    " CALORIES          FLOAT," +
                    " PROTEIN           FLOAT," +
                    " CARBS             FLOAT," +
                    " FATS              FLOAT," +
                    " DIET              diettype)";


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("created user table");
    }

    public static void createShoppingTable() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(DB_URL, USER, PASS);
            System.out.println("connected");

            stmt = c.createStatement();

            String sql =

                    "CREATE TABLE USERFOODTABLE " +
                            "(userid          INT NOT NULL," +
                            "foodid      INT NOT NULL," +
                            " COUNT            INT, " +
                            " PREF             FLOAT" +
                            "PRIMARY KEY(id, foodid))";


            stmt.executeUpdate(sql);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

        System.out.println("created shopping table");

    }

}