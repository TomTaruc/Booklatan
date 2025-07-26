/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * <b>To run this application it is necessary to setup a local .env file. and <br/>
 * import the right database on mySQL</b><br/><br/>
 * This project is shared across multiple developers through GitHub. <br/>
 * For safety, each developer needs to setup their environment, which <br/> 
 * separates private information, to connect the local MySQL Database.<br/>
 * 
 * @author Joseph Rey
 */
public class DataAccessObject {
    private final Dotenv dotenv = Dotenv.load();
    private final String username = dotenv.get("DB_USER");
    private final String password = dotenv.get("DB_PASSWORD");
    private final String dbURL = dotenv.get("DB_URL");
    protected Connection connection;
    
    protected DataAccessObject() {
        checkDrivers();
    }
    
    private void checkDrivers() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException ex) {
            System.out.print("JDBC Driver is not found. Please ensure that you have MySQL JDBC driver installed and imported to your libraries.");
            System.exit(0);
        }
    };
    
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(dbURL, username, password);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.exit(0);
        }
        
        return null;
    }
    
    public void checkDetails() {
        System.out.println(username);
        System.out.println(dbURL);
    }
    
    public void checkDetails(boolean showPassword) {
        System.out.println(username);
        System.out.println((showPassword ? password : "PASSWORD IS HIDDEN"));
        System.out.println(dbURL);
    }
    
    
}

