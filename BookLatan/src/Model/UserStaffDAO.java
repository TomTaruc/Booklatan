/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class UserStaffDAO extends DataAccessObject{
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet results;
    private ArrayList<Staff> staff = new ArrayList<>();
    
    public UserStaffDAO() {};
    
    
    public ArrayList<Staff> getStaff ()  {
        con = super.getConnection();
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM StaffUser;");
            Staff individual;

            staff.clear(); // Removes previous selection

            while(results.next()) {
                individual = new Staff(results.getString("userName"), results.getString("password"));
                individual.setStaffID(results.getInt("staffID"));
                individual.setName(results.getString("name"));
                individual.setPhone(results.getString("phone"));
                individual.setEmail(results.getString("email"));
                individual.setAddress(results.getString("address"));
                individual.setDateHired(results.getDate("dateHired").toLocalDate());
                individual.setType(User.UserType.fromString(results.getString("type")));
                staff.add(individual);
            }

            results.close();
            stmt.close();
            con.close();

            return staff;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public ArrayList<Staff> getStaff (User.UserType type) {
        try {
            con = super.getConnection();
            Staff individual;
            staff.clear(); // removes previous selection

            if(type.equals(User.UserType.ALL)) {
                pstmt = con.prepareStatement("SELECT * FROM StaffUser");
            }
            else {
                pstmt = con.prepareStatement("SELECT * FROM StaffUser WHERE type = ?;");
                pstmt.setString(1, type.toString().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                individual = new Staff(results.getString("userName"), results.getString("password"));
                individual.setStaffID(results.getInt("staffID"));
                individual.setName(results.getString("name"));
                individual.setPhone(results.getString("phone"));
                individual.setEmail(results.getString("email"));
                individual.setAddress(results.getString("address"));
                individual.setDateHired(results.getDate("dateHired").toLocalDate());
                individual.setType(User.UserType.fromString(results.getString("type")));
                staff.add(individual);
            }

            results.close();
            pstmt.close();
            con.close();

            return staff;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public ArrayList<Staff> getStaff (String search, User.UserType type) {
        try {
            con = super.getConnection();
            Staff individual;
            staff.clear(); // Removes previous selection

            if(type.equals(User.UserType.ALL)) {
                pstmt = con.prepareStatement("SELECT * FROM StaffUser WHERE name LIKE ?;");
                pstmt.setString(1, "%" + search + "%");
            }
            else {
                pstmt = con.prepareStatement("SELECT * FROM StaffUser WHERE name LIKE ? AND type = ?;");
                pstmt.setString(1, "%" + search + "%");
                pstmt.setString(2, type.toString().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                individual = new Staff(results.getString("userName"), results.getString("password"));
                individual.setStaffID(results.getInt("staffID"));
                individual.setName(results.getString("name"));
                individual.setPhone(results.getString("phone"));
                individual.setEmail(results.getString("email"));
                individual.setAddress(results.getString("address"));
                individual.setDateHired(results.getDate("dateHired").toLocalDate());
                individual.setType(User.UserType.fromString(results.getString("type")));
                staff.add(individual);
            }

            results.close();
            pstmt.close();
            con.close(); 
            return staff;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public Staff getStaffByID(int staffID) {
        try {
            con = super.getConnection();
            Staff individual;

            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM StaffUser WHERE staffID = "+ staffID +";");
            results.next();
            individual = new Staff(results.getString("userName"), results.getString("password"));
            individual.setStaffID(results.getInt("staffID"));
            individual.setName(results.getString("name"));
            individual.setPhone(results.getString("phone"));
            individual.setEmail(results.getString("email"));
            individual.setAddress(results.getString("address"));
            individual.setDateHired(results.getDate("dateHired").toLocalDate());
            individual.setType(User.UserType.fromString(results.getString("type")));

            results.close();
            stmt.close();
            con.close();

            return individual;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public void addStaff(Staff individual) {
        try {
            con = super.getConnection();
            pstmt = con.prepareStatement("{CALL addStaff(?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, individual.getName());
            pstmt.setString(2, individual.getUsername());
            pstmt.setString(3, individual.getPassword());
            pstmt.setString(4, individual.getPhone());
            pstmt.setString(5, individual.getEmail());
            pstmt.setString(6, individual.getAddress());
            pstmt.setDate(7, Date.valueOf(individual.getDateHired()));
            pstmt.setString(8, individual.getType().toString().toLowerCase());
            pstmt.executeUpdate();

            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    
    public void deleteStaff(Staff individual) {
        try{
            con = super.getConnection();

            pstmt = con.prepareStatement("{CALL deleteStaff(?)}");
            pstmt.setInt(1, individual.getStaffID());
            pstmt.execute();

            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    public void updateStaff(Staff  individual) {
        try {
            con = super.getConnection();
            pstmt = con.prepareStatement("UPDATE STAFF SET name = ?, phone = ?, email = ?, address = ?, dateHired = ? WHERE staffID = ?");

            pstmt.setString(1, individual.getName());
            pstmt.setString(2, individual.getPhone());
            pstmt.setString(3, individual.getEmail());
            pstmt.setString(4, individual.getAddress());
            pstmt.setDate(5, Date.valueOf(individual.getDateHired()));
            pstmt.setInt(6, individual.getStaffID());
            pstmt.execute();
            pstmt.close();

            pstmt = con.prepareStatement("{CALL updateUserTypeStaff(?, ?, ?, ?)}");
            pstmt.setString(1, individual.getUsername());
            pstmt.setString(2, individual.getPassword());
            pstmt.setString(3, individual.getType().toString().toLowerCase());
            pstmt.setInt(4, individual.getStaffID());
            pstmt.execute();

            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
    }
    
    
    
}
