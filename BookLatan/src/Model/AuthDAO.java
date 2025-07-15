/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import Model.User.UserType;

/**
 *
 * @author Dinel
 */


public class AuthDAO {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    
    public static User authenticateUser(String username, String password) {
        return authenticateFromDatabase(username, password);
    }
    
    private static User authenticateFromDatabase(String username, String password) {
        String sql = "SELECT u.* FROM users u " + "WHERE u.username = ? AND u.password = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String typeStr = rs.getString("type");
                UserType type = UserType.fromString(typeStr);
                int userId = rs.getInt("userID");
                
                if (type == UserType.ADMIN || type == UserType.LIBRARIAN) {
                    Staff staff = new Staff(username, password);
                    staff.setType(type);
                    staff.setUserId(userId); 
                    staff.setStaffID(userId);
                    staff.setName(rs.getString("name"));
                    Date hiredDate = rs.getDate("dateHired");
                    if (hiredDate != null) {
                        staff.setDateHired(hiredDate.toLocalDate());
                    }
                    return staff;
                } else if (type == UserType.MEMBER) {
                    User member = new User(username, password);
                    member.setType(type);
                    member.setUserId(userId); 
                    return member;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

