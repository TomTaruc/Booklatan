package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;

public class FineDAO extends DataAccessObject {
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet results;
    private ArrayList<Fine> fines = new ArrayList<>();
    
    
    public ArrayList<Fine> getFines(FineStatus status) {
        con =  super.getConnection();
        fines.clear();
        try {
            if (status == FineStatus.ALL) {
                pstmt = con.prepareStatement("Select * FROM FineMember ORDER BY due_date ASC");
            }
            else {
                pstmt = con.prepareStatement("Select * From FineMember WHERE _status = ? ORDER BY due_date ASC");
                pstmt.setString(1, status.toString().toLowerCase());
            }
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                Fine fine = new Fine();
                fine.setMemberID(results.getInt("memberID"));
                fine.setStaffID(results.getInt("staffID"));
                fine.setAmount(results.getDouble("amount"));
                fine.setDateIssued(results.getDate("dateIssued").toLocalDate());
                fine.setDue_date(results.getDate("due_date").toLocalDate());
                java.sql.Date date = results.getDate("paid_date");
                if (date != null) {
                    fine.setPaid_date(date.toLocalDate());
                }
                fine.setDescription(results.getString("description"));
                fine.setFineID(results.getInt("fineID"));
                fine.setStatus(FineStatus.fromString(results.getString("_status")));
                fines.add(fine);
            }
            
            results.close();
            pstmt.close();
            con.close();
            return fines;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public ArrayList<Fine> getFines(String search, FineStatus status) {
        con =  super.getConnection();
        fines.clear();
        try {
            if (status == FineStatus.ALL) {
                pstmt = con.prepareStatement("Select * FROM FineMember WHERE name like ? ORDER BY due_date ASC");
                pstmt.setString(1, "%" + search +"%");
            }
            else {
                pstmt = con.prepareStatement("Select * From FineMember WHERE _status = ? and name like ? ORDER BY due_date ASC");
                pstmt.setString(1, status.toString().toLowerCase());
                pstmt.setString(2, "%" + search +"%");
            }
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                Fine fine = new Fine();
                fine.setMemberID(results.getInt("memberID"));
                fine.setStaffID(results.getInt("staffID"));
                fine.setAmount(results.getDouble("amount"));
                fine.setDateIssued(results.getDate("dateIssued").toLocalDate());
                fine.setDue_date(results.getDate("due_date").toLocalDate());
                java.sql.Date date = results.getDate("paid_date");
                if (date != null) {
                    fine.setPaid_date(date.toLocalDate());
                }
                fine.setDescription(results.getString("description"));
                fine.setFineID(results.getInt("fineID"));
                fine.setStatus(FineStatus.fromString(results.getString("_status")));
                fines.add(fine);
            }
            
            results.close();
            pstmt.close();
            con.close();
            return fines;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public ArrayList<Fine> getFines(Member member, FineStatus status) {
        con =  super.getConnection();
        fines.clear();
        try {
            if (status == FineStatus.ALL) {
                pstmt = con.prepareStatement("Select * FROM FineMember WHERE memberID = ? ORDER BY due_date ASC");
                pstmt.setInt(1, member.getMemberID());
            }
            else {
                pstmt = con.prepareStatement("Select * From FineMember WHERE _status = ? and memberID = ? ORDER BY due_date ASC");
                pstmt.setString(1, status.toString().toLowerCase());
                pstmt.setInt(2, member.getMemberID());
            }
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                Fine fine = new Fine();
                fine.setMemberID(results.getInt("memberID"));
                fine.setStaffID(results.getInt("staffID"));
                fine.setAmount(results.getDouble("amount"));
                fine.setDateIssued(results.getDate("dateIssued").toLocalDate());
                fine.setDue_date(results.getDate("due_date").toLocalDate());
                java.sql.Date date = results.getDate("paid_date");
                if (date != null) {
                    fine.setPaid_date(date.toLocalDate());
                }
                fine.setDescription(results.getString("description"));
                fine.setFineID(results.getInt("fineID"));
                fine.setStatus(FineStatus.fromString(results.getString("_status")));
                fines.add(fine);
            }
            
            results.close();
            pstmt.close();
            con.close();
            return fines;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public Fine getFine(int id) {
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("SELECT * FROM Fine where fineID = ?");
            pstmt.setInt(1, id);
            results = pstmt.executeQuery();
            Fine fine = null;
            if(results.next()) {
                fine = new Fine();
                fine.setMemberID(results.getInt("memberID"));
                fine.setStaffID(results.getInt("staffID"));
                fine.setAmount(results.getDouble("amount"));
                fine.setDateIssued(results.getDate("dateIssued").toLocalDate());
                fine.setDue_date(results.getDate("due_date").toLocalDate());
                java.sql.Date date = results.getDate("paid_date");
                if (date != null) {
                    fine.setPaid_date(date.toLocalDate());
                }
                fine.setDescription(results.getString("description"));
                fine.setFineID(results.getInt("fineID"));
                fine.setStatus(FineStatus.fromString(results.getString("_status")));   
            }
            
            results.close();
            pstmt.close();
            con.close();
            return fine;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
            return null;
        }
    }
    
    public void addFine(Fine fine) {
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("{CALL addFine(?, ?, ?, ?, ? ,?)}");
            pstmt.setInt(1, fine.getStaffID());
            pstmt.setInt(2, fine.getMemberID());
            pstmt.setDouble(3, fine.getAmount());
            pstmt.setDate(4, java.sql.Date.valueOf(fine.getDateIssued()));
            pstmt.setString(5, fine.getDescription());
            pstmt.setDate(6, java.sql.Date.valueOf(fine.getDue_date()));
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
    
    public void updateFineStatus(Fine fine) {
        con = super.getConnection(); 
        try {
            pstmt = con.prepareStatement("UPDATE fine set _status = ?, paid_date = ? where fineID = ?;");
            pstmt.setString(1, fine.getStatus().toString().toLowerCase());
            pstmt.setDate(2, java.sql.Date.valueOf(fine.getPaid_date()));
            pstmt.setInt(3, fine.getFineID());
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
    
    public void deleteFine(Fine fine) {
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("DELETE FROM fine WHERE fineID = ?");
            pstmt.setInt(1, fine.getFineID());
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
    
    public int countUnpaidFine() {
        con = super.getConnection();
        int totalUnpaid = 0;
        try {
            stmt = con.createStatement();
            results = stmt.executeQuery("Select count(_status) as totalUnpaid from fine where _status = 'pending' group by _status;");
            if(results.next()) {
                totalUnpaid = results.getInt("totalUnpaid");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);   
        }
        
        return totalUnpaid;
    }
    
    public double totalUnpaidFine(Member member) {
        double amount = 0d;
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("Select sum(amount) as sumlUnpaid From Fine where memberID = ? and _status = 'pending' group by memberID;");
            pstmt.setInt(1, member.getMemberID());
            results = pstmt.executeQuery();
            if(results.next()) {
                amount = results.getDouble("sumlUnpaid");
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            System.exit(0);
        }
        
        return amount;
    }
}
