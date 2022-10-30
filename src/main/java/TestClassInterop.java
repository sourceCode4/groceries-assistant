import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class TestClassInterop {
    public static void createUserTable() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();
//            String sql = "CREATE TABLE SHOPPINGLIST " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " ITEM           TEXT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " PRICE          FLOAT)";

            String sql =
                    "CREATE TYPE diettype AS ENUM ('VEGAN', 'VEGETARIAN', 'PESCETARIAN','OMNIVORE');" +
                    "CREATE TABLE USERDATA " +
                    "(NAME TEXT PRIMARY KEY NOT NULL," +
                    " HEIGHT            FLOAT, " +
                    " WEIGHT            FLOAT, " +
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

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void createShoppinglist() {


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
                            "CREATE TABLE SHOPPINGLIST " +
                            "(NAME TEXT PRIMARY KEY NOT NULL," +
                            " HEIGHT            FLOAT, " +
                            " WEIGHT            FLOAT, " +
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

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void createShoppinglogs() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();
//            String sql = "CREATE TABLE SHOPPINGLIST " +
//                    "(ID INT PRIMARY KEY     NOT NULL," +
//                    " NAME           TEXT    NOT NULL, " +
//                    " ITEM           TEXT     NOT NULL, " +
//                    " ADDRESS        CHAR(50), " +
//                    " PRICE          FLOAT)";

            String sql =
                            "CREATE TABLE SHOPPINGLOGS " +
                            "(NAME TEXT PRIMARY KEY NOT NULL," +
                            " HEIGHT            FLOAT, " +
                            " WEIGHT            FLOAT, " +
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

        System.out.println("Opened database successfully");
        System.out.println("Table created successfully");

    }

    public static void inserter() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/test";
        String username = "postgres";
        String password = "407427838";

        String csvFilePath = "C:\\Users\\arsla\\Desktop\\data.csv";

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);

            String sql = "INSERT INTO THEFOOD (Category, EnglishName, NEVOCode, EnergyKJ, EnergyKCAL) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                System.out.println(lineText);
                String[] data = lineText.split(",");
                System.out.println(data);
                String Category = data[2];
                String EnglishName = data[5];
                String NEVOCode = data[3];
                String EnergyKJ = data[11];
                String EnergyKCAL = data[12];

                statement.setString(1, Category);
                statement.setString(2, EnglishName);

                statement.setString(3, NEVOCode);
                statement.setString(4, EnergyKJ);

                statement.setString(5, EnergyKCAL);

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
