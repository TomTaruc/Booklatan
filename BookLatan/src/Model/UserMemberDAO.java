package Model;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class UserMemberDAO extends DataAccessObject{
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet results;
    private ArrayList<Member> members = new ArrayList<>();

    public UserMemberDAO() {};
    
    public int getMemberIDByUSerID(int userID) {
        con = super.getConnection();
        int memberID = -1;
        try {
            pstmt = con.prepareStatement("SELECT memberID FROM MemberUser WHERE userID = ?");
            pstmt.setInt(1, userID);
            results = pstmt.executeQuery();
            
            if(results.next()) {
                memberID = results.getInt("memberID");
            }
            
            return memberID;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return 0;
        }
    }

    public ArrayList<Member> getMembers () {
        con = super.getConnection();

        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM MemberUser;");
            Member member;
            members.clear(); // Removes previous selection

            while(results.next()) {
                System.out.println("Checking username: " + results.getString("userName")); 
                member = new Member(results.getString("userName"), results.getString("password"));
                
                // Set Member Attributes
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
                members.add(member);
            }

            results.close();
            stmt.close();
            con.close();
            return members;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public ArrayList<Member> getMembers (Member.MembershipStatus status) {
        con = super.getConnection();
        Member member;
        members.clear(); // removes previous selection
        try {
            if(status.equals(Member.MembershipStatus.ALL)) {
                pstmt = con.prepareStatement("SELECT * FROM MemberUser");
            }
            else {
                pstmt = con.prepareStatement("SELECT * FROM MemberUser WHERE _status = ?;");
                pstmt.setString(1, status.toString().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                member = new Member(results.getString("userName"), results.getString("password"));
                // Member Attributes
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
                
                members.add(member);
            }

            results.close();
            pstmt.close();
            con.close();

            return members;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }


    public ArrayList<Member> getMembers (String search, Member.MembershipStatus status) {
        con = super.getConnection();
        Member member;
        members.clear();

        try {
            if(status.equals(Member.MembershipStatus.ALL)) {
            pstmt = con.prepareStatement("SELECT * FROM MemberUser WHERE name LIKE ?;");
            pstmt.setString(1, "%" + search + "%");
            }
            else {
                pstmt = con.prepareStatement("SELECT * FROM MemberUser WHERE name LIKE ? AND _status = ?;");
                pstmt.setString(1, "%" + search + "%");
                pstmt.setString(2, status.toString().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                member = new Member(results.getString("userName"), results.getString("password"));
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
                members.add(member);
            }

            results.close();
            pstmt.close();
            con.close();
            return members;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public Member getMemberByName(String name) {
        Member member;
        con = super.getConnection();

        try {

            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM MemberUser WHERE name  LIKE '%"+ name +"%';");
            // Check if there's a result before calling results.next()
            if (results.next()) {
                member = new Member(results.getString("userName"), results.getString("password"));
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
            } else {
                member = null;
            }

            results.close();
            stmt.close();
            con.close();        
            return member;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public Member getMemberByID(int memberID) {
        Member member;
        con = super.getConnection();

        try {

            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM MemberUser WHERE memberID = "+ memberID +";");
            // Check if there's a result before calling results.next()
            if (results.next()) {
                member = new Member(results.getString("userName"), results.getString("password"));
                member.setMemberID(results.getInt("memberID"));
                member.setName(results.getString("name"));
                member.setAddress(results.getString("address"));
                member.setDateJoined(results.getDate("dateJoined").toLocalDate());
                member.setEmail(results.getString("email"));
                member.setPhone(results.getString("phone"));
                member.setStatus(Member.MembershipStatus.fromString(results.getString("_status")));
            } else {
                member = null;
            }

            results.close();
            stmt.close();
            con.close();        
            return member;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }

    public void addMember(Member member) throws SQLException {
        con = super.getConnection();
        pstmt = con.prepareStatement("{CALL addMember(?, ?, ?, ?, ?, ?, ?)}");

        pstmt.setString(1, member.getName());
        pstmt.setString(2, member.getUsername());
        pstmt.setString(3, member.getPassword());
        pstmt.setString(4, member.getPhone());
        pstmt.setString(5, member.getEmail());
        pstmt.setString(6, member.getAddress());
        pstmt.setDate(7, Date.valueOf(LocalDate.now()));
        pstmt.executeUpdate();

        pstmt.close();
        con.close();

    }

    public void deleteMember(Member member) {
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("{CALL deleteMember(?)}");
            pstmt.setInt(1, member.getMemberID());
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

    public void updateMember(Member member) {
        con = super.getConnection();

        try {
            pstmt = con.prepareStatement("UPDATE member SET name = ?, phone = ?, email = ?, address = ?, dateJoined = ?, _status = ? WHERE memberID = ?");

            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getPhone());
            pstmt.setString(3, member.getEmail());
            pstmt.setString(4, member.getAddress());
            pstmt.setDate(5, Date.valueOf(member.getDateJoined()));
            pstmt.setString(6, member.getStatus().toString().toLowerCase());
            pstmt.setInt(7, member.getMemberID());
            pstmt.execute(); 

            pstmt = con.prepareStatement("{CALL updateUserTypeMember(?, ?, ?)}");
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getPassword());
            pstmt.setInt(3, member.getMemberID());
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

    public int countActiveMembers() {
        con = super.getConnection();
        int totalActive = 0;
        
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT COUNT(_status) as totalActive FROM member WHERE _status = 'active';");
            results.next(); 

            totalActive = results.getInt("totalActive");

            results.close();
            stmt.close();
            con.close();
            
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }

        return totalActive; 
    }

}