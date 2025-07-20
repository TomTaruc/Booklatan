package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FineDAO extends DataAccessObject {

    public boolean addFine(Fine fine) {
        String sql = "INSERT INTO fine (fineID, staffID, memberID, amount, reason, dateIssued, _status, book_title, due_date, return_date, days_overdue, paid_date, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fine.getFineID());
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
            pstmt.setTimestamp(12, fine.getPaid_date() != null ? new Timestamp(fine.getPaid_date().getTime()) : null);
            pstmt.setString(13, fine.getDescription());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error adding fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateFine(Fine fine) {
        String sql = "UPDATE fine SET staffID=?, memberID=?, amount=?, reason=?, dateIssued=?, _status=?, book_title=?, due_date=?, return_date=?, days_overdue=?, paid_date=?, description=? WHERE fineID=?";
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
            pstmt.setTimestamp(11, fine.getPaid_date() != null ? new Timestamp(fine.getPaid_date().getTime()) : null);
            pstmt.setString(12, fine.getDescription());
            pstmt.setInt(13, fine.getFineID());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteFine(int fineID) {
        String sql = "DELETE FROM fine WHERE fineID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, fineID);
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
        String sql = "SELECT fine.*, COALESCE(member.name, 'Unknown Member') AS memberName " +
                     "FROM fine LEFT JOIN member ON fine.memberID = member.memberID " +
                     "ORDER BY fine.dateIssued DESC"; 

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
        String sql = "SELECT fine.*, COALESCE(member.name, 'Unknown Member') AS memberName " +
                     "FROM fine LEFT JOIN member ON fine.memberID = member.memberID WHERE 1=1";

        StringBuilder sqlBuilder = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();

        if (statusFilter != null && !statusFilter.trim().isEmpty() && !"All".equalsIgnoreCase(statusFilter)) {
            sqlBuilder.append(" AND fine._status = ?");
            params.add(statusFilter);
        }

        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            sqlBuilder.append(" AND (LOWER(COALESCE(fine.book_title, '')) LIKE LOWER(?) OR LOWER(COALESCE(fine.memberID, '')) LIKE LOWER(?) OR CAST(fine.fineID AS CHAR) LIKE ? OR LOWER(COALESCE(member.name, '')) LIKE LOWER(?) OR LOWER(COALESCE(fine.description, '')) LIKE LOWER(?))");
            String likeTerm = "%" + searchTerm.trim() + "%";
            params.add(likeTerm); 
            params.add(likeTerm); 
            params.add(likeTerm); 
            params.add(likeTerm); 
            params.add(likeTerm); 
        }
        
        sqlBuilder.append(" ORDER BY fine.dateIssued DESC");

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlBuilder.toString())) {

            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(i + 1, params.get(i));
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

   public List<Fine> getFinesByMemberID(String memberID) {
        List<Fine> fines = new ArrayList<>();
        String sql = "SELECT fine.*, COALESCE(member.name, 'Unknown Member') AS memberName " +
                     "FROM fine LEFT JOIN member ON fine.memberID = member.memberID " +
                     "WHERE fine.memberID = ? ORDER BY fine.dateIssued DESC"; 

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, memberID); 

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    fines.add(mapResultSetToFine(rs)); 
                }
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving fines by member ID: " + e.getMessage());
            e.printStackTrace();
        }
        return fines;
    }

    public boolean payFine(int fineID) {
        String sql = "UPDATE fine SET _status = ?, paid_date = ? WHERE fineID = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, "Paid");
            pstmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            pstmt.setInt(3, fineID);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error paying fine: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int getMaxFineID() {
        String sql = "SELECT MAX(fineID) FROM fine";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting max fine ID: " + e.getMessage());
            e.printStackTrace();
        }
        return 100000;
    }

    private Fine mapResultSetToFine(ResultSet rs) throws SQLException {
        Fine fine = new Fine();
        fine.setFineID(rs.getInt("fineID"));
        fine.setStaffID(rs.getString("staffID"));
        fine.setMemberID(rs.getString("memberID"));
        fine.setMemberName(rs.getString("memberName"));
        fine.setAmount(rs.getDouble("amount"));
        fine.setReason(rs.getString("reason"));
        fine.setDateIssued(rs.getTimestamp("dateIssued"));
        fine.set_status(rs.getString("_status"));
        fine.setBook_title(rs.getString("book_title"));
        fine.setDue_date(rs.getTimestamp("due_date"));
        fine.setReturn_date(rs.getTimestamp("return_date"));
        fine.setDays_overdue(rs.getInt("days_overdue"));
        fine.setPaid_date(rs.getTimestamp("paid_date"));
        fine.setDescription(rs.getString("description"));
        return fine;
    }
}
