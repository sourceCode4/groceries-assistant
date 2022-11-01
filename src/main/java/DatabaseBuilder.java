import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

public class DatabaseBuilder {

    static String csvFilePath = "newdata.csv"; //TODO: fill your absolute path to newdata.csv
    static String DB_URL = "jdbc:postgresql:groceriesassistant";
    static String USER = "postgres";
    static String PASS = "ana"; //TODO: fill in your password here

    public static void build(){
        try {
            Class.forName("org.postgresql.Driver");

            Connection init = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", USER, PASS);
            Statement initStmt = init.createStatement();
            String query = "CREATE DATABASE TEST4";
            initStmt.execute(query);
            initStmt.close();
            init.close();
            System.out.println("created database");

            Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = connection.createStatement();

            query = "CREATE TABLE FOOD3 " +
                    "(id SERIAL PRIMARY KEY," +
                    " name VARCHAR(255), " +
                    " subgroup VARCHAR(255), " +
                    " maingroup VARCHAR(255), " +
                    " diet VARCHAR(255), " +
                    " calories DECIMAL(7,2), " +
                    " protein DECIMAL(7,2), " +
                    " carbs DECIMAL(7,2), " +
                    " fat DECIMAL(7,2)) ";

            stmt.execute(query);

            System.out.println("created food table");

            connection.setAutoCommit(false);
            //name, subgroup, Group, diet, calories, protein, carbs, fat
            query = "INSERT INTO FOOD (name, subgroup, maingroup, diet, calories, protein, carbs, fat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

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
                String protein = data[7];
                String carbs = data[9];
                String fat = data[8];

                statement.setString(1, name);
                statement.setString(2, subgroup);
                statement.setString(3, maingroup);
                statement.setString(4, diet);
                statement.setFloat(5, Float.parseFloat(calories) );
                statement.setFloat(6, Float.parseFloat(protein));
                statement.setFloat(7, Float.parseFloat(carbs));
                statement.setFloat(8, Float.parseFloat(fat));

                statement.addBatch();
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();
            statement.close();
            connection.commit();

            System.out.println("created food table");

            connection.setAutoCommit(true);

            String sql =    "CREATE TABLE USERS" +
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
                    " DIET              TEXT)";

            stmt.execute(sql);
            System.out.println("created user table");

            query = "CREATE TABLE shopping " +
                            "(userid     INT NOT NULL," +
                            "foodid      INT NOT NULL," +
                            " COUNT            INT, " +
                            " PREF             FLOAT," +
                            " AMOUNT              INT," +
                            "PRIMARY KEY(userid, foodid))";
            stmt.execute(query);
            System.out.println("created shopping table");

            stmt.close();
            connection.close();

        } catch (Exception e) {
            System.out.println("database setup failed");
            e.printStackTrace();
        }
    }
}