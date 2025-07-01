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
public class MemberDAO extends DAO {
    
    public void addMember(Member member) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO member(userID, name, phone, email, address, dateJoined, _status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setInt(1, member.getUserID());
            stmt.setString(2, member.getName());
            stmt.setString(3, member.getPhone());
            stmt.setString(4, member.getEmail());
            stmt.setString(5, member.getAddress());
            stmt.setDate(6, Date.valueOf(member.getDateJoined()));
            stmt.setString(7, member.getStatus().toString().toLowerCase());
            stmt.executeUpdate();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void deleteMember(Member member) {
        Connection con = super.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM MEMBER WHERE memberID = " + member.getMemberID()+";");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateMember(Member member) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE member SET name = ?, phone = ?, email = ?, address = ?, dateJoined = ?, _status = ? WHERE memberID = ?");
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getPhone());
            stmt.setString(3, member.getEmail());
            stmt.setString(4, member.getAddress());
            stmt.setDate(5, Date.valueOf(member.getDateJoined()));
            stmt.setString(6, member.getStatus().toString().toLowerCase());
            stmt.setInt(7, member.getMemberID());
            stmt.executeUpdate();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
