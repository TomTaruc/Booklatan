package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class FineDAO extends DAO {

    public boolean addFine(Fine fine) {
        String sql = "INSERT INTO fine (fineID, staffID, memberID, amount, reason, dateIssued, _status, book_title, due_date, return_date, days_overdue, isbn, paid_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fine.getFineID());
            pstmt.setString(2, fine.getStaffID());
            pstmt.setString(3, fine.getMemberID());
            pstmt.setDouble(4, fine.getAmount());
            pstmt.setString(5, fine.getReason());
            pstmt.setTimestamp(6, fine.getDateIssued() != null ? new Timestamp(fine.getDateIssued().getTime()) : null);
            pstmt.setString(7, fine.get_status());
            pstmt.setString(8, fine.getBook_title());
            pstmt.setTimestamp(9, fine.getDue_date() != null ? new Timestamp(fine.getDue_date().getTime()) : null);
            pstmt.setTimestamp(10, fine.getReturn_date() != null ? new Timestamp(fine.getReturn_date().getTime()) : null);
            pstmt.setInt(11, fine.getDays_overdue());
            pstmt.setString(12, fine.getIsbn());
            pstmt.setTimestamp(13, null);
            pstmt.setString(14, fine.getDescription());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFine(Fine fine) {
        String sql = "UPDATE fine SET staffID=?, memberID=?, amount=?, reason=?, dateIssued=?, _status=?, book_title=?, due_date=?, return_date=?, days_overdue=?, isbn=?, paid_date=?, description=? WHERE fineID=?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fine.getStaffID());
            pstmt.setString(2, fine.getMemberID());
            pstmt.setDouble(3, fine.getAmount());
            pstmt.setString(4, fine.getReason());
            pstmt.setTimestamp(5, fine.getDateIssued() != null ? new Timestamp(fine.getDateIssued().getTime()) : null);
            pstmt.setString(6, fine.get_status());
            pstmt.setString(7, fine.getBook_title());
            pstmt.setTimestamp(8, fine.getDue_date() != null ? new Timestamp(fine.getDue_date().getTime()) : null);
            pstmt.setTimestamp(9, fine.getReturn_date() != null ? new Timestamp(fine.getReturn_date().getTime()) : null);
            pstmt.setInt(10, fine.getDays_overdue());
            pstmt.setString(11, fine.getIsbn());
            pstmt.setTimestamp(12, fine.getPaid_date() != null ? new Timestamp(fine.getPaid_date().getTime()) : null);
            pstmt.setString(13, fine.getDescription());
            pstmt.setString(14, fine.getFineID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFine(String fineID) {
        String sql = "DELETE FROM fine WHERE fineID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fineID);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error deleting fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Fine> getAllFines() {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT * FROM fine";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                fines.add(mapResultSetToFine(rs));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving all fines: " + e.getMessage());
            e.printStackTrace();
        }
        return fines;
    }

    public List<Fine> getFines(String searchTerm, String statusFilter) {
        List<Fine> fines = new ArrayList<>();
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM fine WHERE 1=1");

        if (statusFilter != null && !statusFilter.equalsIgnoreCase("All") && !statusFilter.isEmpty()) {
            sqlBuilder.append(" AND _status = ?");
        }

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sqlBuilder.append(" AND (book_title LIKE ? OR isbn LIKE ?)");
        }

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            int paramIndex = 1;

            if (statusFilter != null && !statusFilter.equalsIgnoreCase("All") && !statusFilter.isEmpty()) {
                pstmt.setString(paramIndex++, statusFilter);
            }

            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                String likeTerm = "%" + searchTerm.trim() + "%";
                pstmt.setString(paramIndex++, likeTerm);
                pstmt.setString(paramIndex++, likeTerm);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    fines.add(mapResultSetToFine(rs));
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving filtered fines: " + e.getMessage());
            e.printStackTrace();
        }
        return fines;
    }

    public boolean payFine(String fineID) {
        String sql = "UPDATE fine SET _status = ?, paid_date = ? WHERE fineID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Paid");
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setString(3, fineID);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error paying fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Fine mapResultSetToFine(ResultSet rs) throws SQLException {
        Fine fine = new Fine();
        fine.setFineID(rs.getString("fineID"));
        fine.setStaffID(rs.getString("staffID"));
        fine.setMemberID(rs.getString("memberID"));
        fine.setAmount(rs.getDouble("amount"));
        fine.setReason(rs.getString("reason"));
        fine.setDateIssued(rs.getTimestamp("dateIssued"));
        fine.set_status(rs.getString("_status"));
        fine.setBook_title(rs.getString("book_title"));
        fine.setDue_date(rs.getTimestamp("due_date"));
        fine.setReturn_date(rs.getTimestamp("return_date"));
        fine.setDays_overdue(rs.getInt("days_overdue"));
        fine.setIsbn(rs.getString("isbn"));
        fine.setPaid_date(rs.getTimestamp("paid_date"));
        fine.setDescription(rs.getString("description"));
        return fine;
    }
}
