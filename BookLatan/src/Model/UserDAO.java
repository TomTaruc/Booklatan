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
public class UserDAO extends DAO{
    public void createUser(User user) {
        Connection con = super.getConnection();
        try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO USERS(userName, password, type) VALUES (?, ?, ?)");
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType().toString().toLowerCase());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteUser(User user) {
        Connection con = super.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            stmt.execute("DELETE FROM USERS WHERE userID =" + user.getUserID() + ";");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
            stmt.close();
            con.close();   
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateUser(User user) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE USERS SET userName = ?, password = ?, type = ? WHERE userID = ?");
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType().toString().toLowerCase());
            stmt.setInt(4, user.getUserID());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
