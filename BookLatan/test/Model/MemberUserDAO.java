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
public class MemberUserDAO extends DAO {
    
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
    
    public ArrayList<Member> getMembers (Member.MembershipStatus status) {
        Connection con = super.getConnection();
        ArrayList<Member> members = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet results;
        try {
            if(status.equals(Member.MembershipStatus.ALL)) {
                stmt = con.prepareStatement("SELECT * FROM MemberUser");
            }
            else {
                stmt = con.prepareStatement("SELECT * FROM MemberUser WHERE _status = ?;");
                stmt.setString(1, status.toString().toLowerCase());
            }
            
            results = stmt.executeQuery();
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
    
    //
    
    public ArrayList<Member> getMembers (String search, Member.MembershipStatus status) {
        System.out.println("Work it");
        Connection con = super.getConnection();
        ArrayList<Member> members = new ArrayList<>();
        PreparedStatement stmt;
        ResultSet results;
        try {
            if(status.equals(Member.MembershipStatus.ALL)) {
                stmt = con.prepareStatement("SELECT * FROM MemberUser WHERE name LIKE ?;");
                stmt.setString(1, "%" + search + "%");
            }
            else {
                stmt = con.prepareStatement("SELECT * FROM MemberUser WHERE name LIKE ? AND _status = ?;");
                stmt.setString(1, "%" + search + "%");
                stmt.setString(2, status.toString().toLowerCase());
            }
            
            results = stmt.executeQuery();
            
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
    
    public Member getMemberByID(int memberID) {
        Connection con = super.getConnection();
        Member member;
        Statement stmt;
        ResultSet results;
        
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM MemberUser WHERE memberID = "+ memberID +";");
            results.next();
            member = new Member();
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
            
            results.close();
            stmt.close();
            con.close();
            return member;
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public void addMember(Member member) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("{CALL addMember(?, ?, ?, ?, ?, ?, ?)}");
            stmt.setString(1, member.getName());
            stmt.setString(2, member.getUserName());
            stmt.setString(3, member.getPassword());
            stmt.setString(4, member.getPhone());
            stmt.setString(5, member.getEmail());
            stmt.setString(6, member.getAddress());
            stmt.setDate(7, Date.valueOf(member.getDateJoined()));
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
            PreparedStatement stmt = con.prepareStatement("{CALL deleteMember(?)}");
            stmt.setInt(1, member.getMemberID());
            stmt.execute();
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
            stmt = con.prepareStatement("UPDATE USERS SET userName = ? , password = ?, type = ? WHERE userID = ?");
            stmt.setString(1, member.getUserName());
            stmt.setString(2, member.getPassword());
            stmt.setString(3, member.getType().toString().toLowerCase());
            stmt.setInt(4, member.getUserID());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public int countActiveMembers() {
        Connection con = super.getConnection();
        Statement stmt;
        ResultSet results;
        try{
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT COUNT(_status) as totalActive FROM member WHERE _status = 'active';");
            results.next();
            return results.getInt("totalActive");
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}
