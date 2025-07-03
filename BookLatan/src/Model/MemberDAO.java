/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.*;
import java.util.ArrayList;
import java.time.LocalDate;
/**
 *
 * @author Joseph Rey
 */
public class MemberDAO extends DAO {
    
    public ArrayList<Member> getMembers () {
        Connection con = super.getConnection();
        ArrayList<Member> members = new ArrayList<>();
        Statement stmt;
        ResultSet results;
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM MemberUser;");
            while(results.next()) {
                Member member = new Member();
                // Member Attributes
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
                // User Attributes
                member.setUserID(results.getInt("userID"));
                member.setUserName(results.getString("userName"));
                member.setType(User.UserType.MEMBER);
                member.setPassword(results.getString("password"));
                
                members.add(member);
            }
            
            results.close();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return members;
    }
    
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
