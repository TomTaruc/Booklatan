/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author motar
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date; 
//
//public class FineDAO extends DataAccessObject { 
//
//    public FineDAO () throws Exception {};
//    
//    public boolean addFine(Fine fine) {
//        String sql = "INSERT INTO fines (fine_id, member_id, member_name, book_title, isbn, " +
//                     "due_date, return_date, days_overdue, amount, status, issued_date, description) " +
//                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection conn = getConnection(); 
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, fine.getFineId());
//            pstmt.setString(2, fine.getMemberId());
//            pstmt.setString(3, fine.getMemberName());
//            pstmt.setString(4, fine.getBookTitle());
//            pstmt.setString(5, fine.getIsbn());
//            pstmt.setDate(6, new java.sql.Date(fine.getDueDate().getTime()));
//            pstmt.setDate(7, fine.getReturnDate() != null ?
//                            new java.sql.Date(fine.getReturnDate().getTime()) : null);
//            pstmt.setInt(8, fine.getDaysOverdue());
//            pstmt.setDouble(9, fine.getAmount());
//            pstmt.setString(10, fine.getStatus());
//            pstmt.setTimestamp(11, new Timestamp(fine.getIssuedDate().getTime()));
//            pstmt.setString(12, fine.getDescription());
//
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<Fine> getAllFines() {
//        List<Fine> fines = new ArrayList<>();
//        String sql = "SELECT * FROM fines ORDER BY issued_date DESC";
//
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                Fine fine = new Fine();
//                fine.setFineId(rs.getString("fine_id"));
//                fine.setMemberId(rs.getString("member_id"));
//                fine.setMemberName(rs.getString("member_name"));
//                fine.setBookTitle(rs.getString("book_title"));
//                fine.setIsbn(rs.getString("isbn"));
//                fine.setDueDate(rs.getDate("due_date"));
//                fine.setReturnDate(rs.getDate("return_date"));
//                fine.setDaysOverdue(rs.getInt("days_overdue"));
//                fine.setAmount(rs.getDouble("amount"));
//                fine.setStatus(rs.getString("status"));
//                fine.setIssuedDate(rs.getTimestamp("issued_date"));
//                fine.setPaidDate(rs.getTimestamp("paid_date"));
//                fine.setDescription(rs.getString("description"));
//                fines.add(fine);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return fines;
//    }
//
//    public boolean markAsPaid(String fineId) {
//        String sql = "UPDATE fines SET status = 'paid', paid_date = CURRENT_TIMESTAMP WHERE fine_id = ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, fineId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public boolean deleteFine(String fineId) {
//        String sql = "DELETE FROM fines WHERE fine_id = ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, fineId);
//            return pstmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public List<Fine> searchFines(String searchTerm) {
//        List<Fine> fines = new ArrayList<>();
//        String sql = "SELECT * FROM fines WHERE member_name LIKE ? OR member_id LIKE ? OR book_title LIKE ?";
//
//        try (Connection conn = getConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            String searchPattern = "%" + searchTerm + "%";
//            pstmt.setString(1, searchPattern);
//            pstmt.setString(2, searchPattern);
//            pstmt.setString(3, searchPattern);
//
//            ResultSet rs = pstmt.executeQuery();
//            while (rs.next()) {
//                Fine fine = new Fine();
//                fine.setFineId(rs.getString("fine_id"));
//                fine.setMemberId(rs.getString("member_id"));
//                fine.setMemberName(rs.getString("member_name"));
//                fine.setBookTitle(rs.getString("book_title"));
//                fine.setIsbn(rs.getString("isbn"));
//                fine.setDueDate(rs.getDate("due_date"));
//                fine.setReturnDate(rs.getDate("return_date"));
//                fine.setDaysOverdue(rs.getInt("days_overdue"));
//                fine.setAmount(rs.getDouble("amount"));
//                fine.setStatus(rs.getString("status"));
//                fine.setIssuedDate(rs.getTimestamp("issued_date"));
//                fine.setPaidDate(rs.getTimestamp("paid_date"));
//                fine.setDescription(rs.getString("description"));
//                fines.add(fine);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return fines;
//    }
//
//    public List<Fine> getFinesByStatus(String status) {
//        List<Fine> fines = new ArrayList<>();
//        String sql = "SELECT * FROM fines WHERE status = ? ORDER BY issued_date DESC";
//
//        try (Connection conn = getConnection(); 
//             PreparedStatement pstmt = conn.prepareStatement(sql)) {
//
//            pstmt.setString(1, status);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                Fine fine = new Fine();
//                fine.setFineId(rs.getString("fine_id"));
//                fine.setMemberId(rs.getString("member_id"));
//                fine.setMemberName(rs.getString("member_name"));
//                fine.setBookTitle(rs.getString("book_title"));
//                fine.setIsbn(rs.getString("isbn"));
//                fine.setDueDate(rs.getDate("due_date"));
//                fine.setReturnDate(rs.getDate("return_date"));
//                fine.setDaysOverdue(rs.getInt("days_overdue"));
//                fine.setAmount(rs.getDouble("amount"));
//                fine.setStatus(rs.getString("status"));
//                fine.setIssuedDate(rs.getTimestamp("issued_date"));
//                fine.setPaidDate(rs.getTimestamp("paid_date"));
//                fine.setDescription(rs.getString("description"));
//                fines.add(fine);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return fines;
//    }
//}
