package Model;
import java.sql.*;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Joseph Rey
 */
public class StaffDAO extends DAO{
    public void registerStaff(Staff staff) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO STAFF (userID, name, phone, email, dateHired, role) VALUES (?, ?, ?, ?, ?, ?);");
            stmt.setInt(1, staff.getUserID());
            stmt.setString(2, staff.getName());
            stmt.setString(3, staff.getPhone());
            stmt.setString(4, staff.getEmail());
            stmt.setDate(5, Date.valueOf(staff.getDateHired()));
            stmt.setString(6, staff.getRole().toString().toLowerCase());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteStaff(Staff staff) {
        Connection con = super.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM STAFF WHERE staffID = " + staff.getStaffID() + ";");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateStaff(Staff staff) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE STAFF SET name = ?, phone = ?, email = ?, dateHired = ?, role = ? WHERE staffID = ?");
            stmt.setString(1, staff.getName());
            stmt.setString(2, staff.getPhone());
            stmt.setString(3, staff.getEmail());
            stmt.setDate(4, Date.valueOf(staff.getDateHired()));
            stmt.setString(5, staff.getRole().toString().toLowerCase());
            stmt.setInt(6, staff.getStaffID());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
