package fines_taruc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/library_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Aninmot965_"; 

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Java app connected to MySQL database (via Workbench's server) successfully!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found. Ensure mysql-connector-j-X.X.X.jar is correctly added to your project's Libraries.");
            throw new SQLException("JDBC Driver not found", e);
        } catch (SQLException e) {
            System.err.println("Database connection failed for Java app. Check URL, username, password in DatabaseConnection.java. Also, ensure MySQL Server is running and accessible. Error: " + e.getMessage());
            throw e;
        }
    }
}

