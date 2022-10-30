import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class FoodTable {

    public static void runner(){
        createTable();
        inserter();
    }

    public static void query(String s){
        String DB_URL = "jdbc:postgresql://localhost:5432/test";
        String USER = "postgres";
        String PASS = "TO BE ADDED";
        String QUERY2 = "SELECT * FROM FOOD WHERE Name Like '%"+s+"%'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY2);
        ) {
            while(rs.next()){
                //Display values
                System.out.print("Name: " + rs.getString("Name"));
                System.out.println(", Subgroup: " + rs.getString("Subgroup"));
                System.out.print(", Group: " + rs.getString("Group"));
                System.out.print(", Diet: " + rs.getString("Diet"));
                System.out.print(", Calories: " + rs.getString("Calories"));
                System.out.print(", Protein: " + rs.getString("Protein"));
                System.out.print(", Carbs: " + rs.getString("Carbs"));
                System.out.println(", Fat: " + rs.getString("Fat"));
                //System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//        public static void creator(){
//            String DB_URL = "jdbc:postgresql://localhost:5432/test";
//            String USER = "postgres";
//            String PASS = "TO BE ADDED";
//            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//                Statement stmt = conn.createStatement();
//            ) {
//                String sql = "CREATE DATABASE CAPROJECT";
//                stmt.executeUpdate(sql);
//                System.out.println("Database created successfully...");
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }

    public static void createTable(){
        String DB_URL = "jdbc:postgresql://localhost:5432/test";
        String USER = "postgres";
        String PASS = "TO BE ADDED";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            //Category, EnglishName, NEVOCode, EnergyKJ, EnergyKCAL
            String sql = "CREATE TABLE FOOD " +
                    "(Name VARCHAR(255), " +
                    " Subgroup VARCHAR(255), " +
                    " Maingroup VARCHAR(255), " +
                    " Diet VARCHAR(255), " +
                    " Calories DECIMAL(7,3), " +
                    " Protein DECIMAL(7,3), " +
                    " Carbs DECIMAL(7,3), " +
                    " Fat DECIMAL(7,3), "+
                    " PRIMARY KEY (Name))";

            stmt.executeUpdate(sql);

            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserter() {

        String DB_URL = "jdbc:postgresql://localhost:5432/test";
        String USER = "postgres";
        String PASS = "TO BE ADDED";

        String csvFilePath = "C:\\Users\\arsla\\Downloads\\groceries-assistant-Zhu-Zhao\\src\\main\\java\\newdata.csv";

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(DB_URL, USER, PASS);
            connection.setAutoCommit(false);
            //Name, Subgroup, Group, Diet, Calories, Protein, Carbs, Fat
            String sql = "INSERT INTO FOOD (Name, Subgroup, Maingroup, Diet, Calories, Protein, Carbs, Fat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {

                String[] data = lineText.split(",");
                String Name = data[3];
                String Subgroup = data[2];
                String Maingroup = data[1];
                String Diet = data[0];
                String Calories = data[6];
                Calories.replace(".", ",");
                String Protein = data[7];
                Protein.replace(".", ",");
                String Carbs = data[9];
                Carbs.replace(".", ",");
                String Fat = data[8];
                Fat.replace(".", ",");

                statement.setString(1, Name);
                statement.setString(2, Subgroup);
                statement.setString(3, Maingroup);
                statement.setString(4, Diet);
                statement.setFloat(5, Float.valueOf(Calories) );
                statement.setFloat(6, Float.valueOf(Protein));
                statement.setFloat(7, Float.valueOf(Carbs));
                statement.setFloat(8, Float.valueOf(Fat));



                statement.addBatch();

                if (count % batchSize == 0) {
                    statement.executeBatch();
                }
            }

            lineReader.close();

            // execute the remaining queries
            statement.executeBatch();

            connection.commit();
            connection.close();

        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


}