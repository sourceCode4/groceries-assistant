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
        creator();
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
            String sql = "CREATE TABLE THEFOODTEST " +
                    "(Category VARCHAR(255) SET UTF8 COLLATE UTF8_GENERAL_CI, " +
                    " EnglishName VARCHAR(255), " +
                    " NEVOCode VARCHAR(255), " +
                    " EnergyKJ VARCHAR(255), " +
                    " EnergyKCAL VARCHAR(255)) ";

            stmt.executeUpdate(sql);

            String query = "ALTER";
            System.out.println("Created table in given database...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void inserter() {
        String jdbcURL = "jdbc:postgresql://localhost:5432/";
        String username = "postgres";
        String password = "ana";

        String csvFilePath = "C:\\Users\\ekz\\Desktop\\Copy of NEVO2021_v7.0.csv";

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

    public static void fetch(){

    }

}


