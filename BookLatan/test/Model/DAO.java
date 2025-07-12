/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;
/**
 *
 * @author Joseph Rey
 */
public abstract class  DAO{
    private final Dotenv dotenv = Dotenv.load();
    private final String username = dotenv.get("DB_USER");
    private final String password = dotenv.get("DB_PASSWORD");
    private final String dbURL = dotenv.get("DB_URL");
    
    public void checkDetails() {
        System.out.println(username);
        System.out.println(password);
        System.out.println(dbURL);
    }
    
    protected Connection getConnection() {
        try {
            return DriverManager.getConnection(dbURL, username, password);        
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
