/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.*;
/**
 *
 * @author Joseph Rey
 */
public abstract class  DAO{
    private String username = "root";
    private String password = "KaiserLycan081505";
    private String dbURL = "jdbc:mysql://localhost:3306/booklatan";
    
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
