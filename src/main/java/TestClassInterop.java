import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestClassInterop {
    public static void createTable() {


        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection("jdbc:postgresql://localhost:5432/test",
                            "postgres", "TO BE ADDED"); //Please use your own postgreSQL password
            System.out.println("connected");


            stmt = c.createStatement();
            String sql = "CREATE TABLE SHOPPINGLIST " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " ITEM           TEXT     NOT NULL, " +
                    " ADDRESS        CHAR(50), " +
                    " PRICE          FLOAT)";
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
}
