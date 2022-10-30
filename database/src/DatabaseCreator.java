package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DatabaseCreator {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    static final String USER = "postgres";
    static final String PASS = "ana";
    //static final String QUERY = "SELECT Category, EnglishName, NEVOCode, EnergyKJ, EnergyKCAL FROM THEFOOD";


    public static void main(String[] args) {
        //run the code below only once to set up the database the the table containing the food
        //creator();
        createTable();
        inserter();
        //     query("omato");

    }

    public static void query(String s){
        String QUERY2 = "SELECT * FROM THEFOOD WHERE EnglishName Like '%"+s+"%'";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY2);
        ) {
            while(rs.next()){
                //Display values
                System.out.print("Category: " + rs.getString("Category"));
                System.out.println(", EnglishName: " + rs.getString("EnglishName"));
                System.out.print(", NEVOCode: " + rs.getString("NEVOCode"));
                System.out.print(", EnergyKJ: " + rs.getString("EnergyKJ"));
                System.out.println(", EnergyKCAL: " + rs.getString("EnergyKCAL"));
                //System.out.println(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void creator(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            String sql = "CREATE DATABASE CAPROJECT";
            stmt.executeUpdate(sql);
            System.out.println("Database created successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(){
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
        ) {
            //Category, EnglishName, NEVOCode, EnergyKJ, EnergyKCAL
            String sql = "CREATE TABLE Food " +
                    "(Name VARCHAR(255), " +
                    " Subgroup VARCHAR(255), " +
                    " Group VARCHAR(255), " +
                    " Diet VARCHAR(255), " +
                    " Calories NUMERIC(1,1), " +
                    " Protein NUMERIC(1,1), " +
                    " Carbs NUMERIC(1,1), " +
                    " Fat NUMERIC(1,1), "+
                    " PRIMARY KEY (Name))";

            stmt.executeUpdate(sql);

            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserter() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "ana";

        String csvFilePath = "newdata.csv";

        int batchSize = 20;

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(jdbcURL, username, password);
            connection.setAutoCommit(false);
            //Name, Subgroup, Group, Diet, Calories, Protein, Carbs, Fat
            String sql = "INSERT INTO FOOD (Name, Subgroup, Group, Diet, Calories, Protein, Carbs, Fat) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);

            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;

            int count = 0;

            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                System.out.println(lineText);
                String[] data = lineText.split(",");
                System.out.println(data);
                String Name = data[3];
                String Subgroup = data[2];
                String Group = data[1];
                String Diet = data[0];
                String Calories = data[6];
                String Protein = data[7];
                String Carbs = data[9];
                String Fat = data[8]

                statement.setString(1, Name);
                statement.setString(2, Subgroup);
                statement.setString(3, Group);
                statement.setString(4, Diet);
                statement.setString(5, Calories);
                statement.setString(5, Protein);
                statement.setString(5, Carbs);
                statement.setString(5, Fat);

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


