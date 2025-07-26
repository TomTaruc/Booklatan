/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Dinel
 */
public class ReservationDAO extends DataAccessObject {
    private Connection con;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet results;
    private ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationDAO() {}

    private Book createSimpleBook(int bookId, String title) {
        return new Book(
            bookId, 
            title, 
            null, // authors
            null, // publisher
            "", // category
            new java.util.Date(), // pubDate
            "", // language
            BookStatus.AVAILABLE, // status
            "" // shelfLocation
        );
    }

    public ArrayList<Reservation> getReservations() {
        con = super.getConnection();
        
        try {
            String sql = "SELECT r.resID, r.memberID, r.dateReserved, r._status, " +
                "m.name as memberName, " +
                "GROUP_CONCAT(b.title SEPARATOR ', ') as bookTitles, " +
                "GROUP_CONCAT(b.bookID SEPARATOR ', ') as bookIDs " +
                "FROM reservation r " +
                "JOIN member m ON r.memberID = m.memberID " +
                "LEFT JOIN reservationbook rb ON r.resID = rb.resID " +
                "LEFT JOIN book b ON rb.bookID = b.bookID " +
                "GROUP BY r.resID, r.memberID, r.dateReserved, r._status, m.name " +
                "ORDER BY r.dateReserved DESC";
            stmt = con.createStatement();
            results = stmt.executeQuery(sql);
            
            reservations.clear();
            
            while(results.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(results.getInt("resID"));
                reservation.setMemberID(results.getInt("memberID"));
                reservation.setMemberName(results.getString("memberName"));
                reservation.setDateReserved(results.getDate("dateReserved").toLocalDate());
                
                String statusStr = results.getString("_status");
                reservation.setStatus(Reservation.ReservationStatus.fromString(statusStr));
                
                // Handle books - for now we'll store titles as a simple string
                // In a full implementation, you might want to load the actual Book objects
                String bookTitles = results.getString("bookTitles");
                if (bookTitles != null && !bookTitles.isEmpty()) {
                    // Create simple Book objects with just titles for display
                    String[] titles = bookTitles.split(", ");
                    String[] bookIds = results.getString("bookIDs").split(", ");
                    
                    for (int i = 0; i < titles.length; i++) {
                        Book book = createSimpleBook(Integer.parseInt(bookIds[i]), titles[i]);
                        reservation.addBook(book);
                    }
                }
                
                reservations.add(reservation);
            }
            
            results.close();
            stmt.close();
            con.close();
            return reservations;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Reservation> getReservations(Reservation.ReservationStatus status) {
        con = super.getConnection();
        reservations.clear();
        
        try {
            if(status.equals(Reservation.ReservationStatus.ALL)) {
                return getReservations();
            }
            else {
                pstmt = con.prepareStatement(
                    "SELECT r.resID, r.memberID, r.dateReserved, r._status, " +
                    "m.name as memberName, " +
                    "GROUP_CONCAT(b.title SEPARATOR ', ') as bookTitles, " +
                    "GROUP_CONCAT(b.bookID SEPARATOR ', ') as bookIDs " +
                    "FROM reservation r " +
                    "JOIN member m ON r.memberID = m.memberID " +
                    "LEFT JOIN reservationbook rb ON r.resID = rb.resID " +
                    "LEFT JOIN book b ON rb.bookID = b.bookID " +
                    "WHERE r._status = ? " +
                    "GROUP BY r.resID, r.memberID, r.dateReserved, r._status, m.name " +
                    "ORDER BY r.dateReserved DESC"
                );
                pstmt.setString(1, status.name().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(results.getInt("resID"));
                reservation.setMemberID(results.getInt("memberID"));
                reservation.setMemberName(results.getString("memberName"));
                reservation.setDateReserved(results.getDate("dateReserved").toLocalDate());
                
                String statusStr = results.getString("_status");
                reservation.setStatus(Reservation.ReservationStatus.fromString(statusStr));
                
                String bookTitles = results.getString("bookTitles");
                if (bookTitles != null && !bookTitles.isEmpty()) {
                    String[] titles = bookTitles.split(", ");
                    String[] bookIds = results.getString("bookIDs").split(", ");
                    
                    for (int i = 0; i < titles.length; i++) {
                        Book book = createSimpleBook(Integer.parseInt(bookIds[i]), titles[i]);
                        reservation.addBook(book);
                    }
                }
                
                reservations.add(reservation);
            }

            results.close();
            pstmt.close();
            con.close();
            return reservations;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Reservation> getReservations(String search, Reservation.ReservationStatus status) {
        con = super.getConnection();
        reservations.clear();

        try {
            String query = 
                "SELECT r.resID, r.memberID, r.dateReserved, r._status, " +
                "m.name as memberName, " +
                "GROUP_CONCAT(b.title SEPARATOR ', ') as bookTitles, " +
                "GROUP_CONCAT(b.bookID SEPARATOR ', ') as bookIDs " +
                "FROM reservation r " +
                "JOIN member m ON r.memberID = m.memberID " +
                "LEFT JOIN reservationbook rb ON r.resID = rb.resID " +
                "LEFT JOIN book b ON rb.bookID = b.bookID " +
                "WHERE (m.name LIKE ? OR b.title LIKE ? OR r.resID LIKE ?)";
            
            if(!status.equals(Reservation.ReservationStatus.ALL)) {
                query += " AND r._status = ?";
            }
            
            query += " GROUP BY r.resID, r.memberID, r.dateReserved, r._status, m.name " +
                    "ORDER BY r.dateReserved DESC";
            
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, "%" + search + "%");
            pstmt.setString(2, "%" + search + "%");
            pstmt.setString(3, "%" + search + "%");
            
            if(!status.equals(Reservation.ReservationStatus.ALL)) {
                pstmt.setString(4, status.name().toLowerCase());
            }

            results = pstmt.executeQuery();

            while(results.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(results.getInt("resID"));
                reservation.setMemberID(results.getInt("memberID"));
                reservation.setMemberName(results.getString("memberName"));
                reservation.setDateReserved(results.getDate("dateReserved").toLocalDate());
                
                String statusStr = results.getString("_status");
                reservation.setStatus(Reservation.ReservationStatus.fromString(statusStr));
                
                String bookTitles = results.getString("bookTitles");
                if (bookTitles != null && !bookTitles.isEmpty()) {
                    String[] titles = bookTitles.split(", ");
                    String[] bookIds = results.getString("bookIDs").split(", ");
                    
                    for (int i = 0; i < titles.length; i++) {
                        Book book = createSimpleBook(Integer.parseInt(bookIds[i]), titles[i]);
                        reservation.addBook(book);
                    }
                }
                
                reservations.add(reservation);
            }

            results.close();
            pstmt.close();
            con.close();
            return reservations;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<Reservation> getReservationsByMemberID(int memberID) {
        con = super.getConnection();
        reservations.clear();
        
        try {
            String sql = "SELECT r.resID, r.memberID, r.dateReserved, r._status, " +
                "m.name as memberName, " +
                "GROUP_CONCAT(b.title SEPARATOR ', ') as bookTitles, " +
                "GROUP_CONCAT(b.bookID SEPARATOR ', ') as bookIDs " +
                "FROM reservation r " +
                "JOIN member m ON r.memberID = m.memberID " +
                "LEFT JOIN reservationbook rb ON r.resID = rb.resID " +
                "LEFT JOIN book b ON rb.bookID = b.bookID " +
                "WHERE r.memberID = ? " +
                "GROUP BY r.resID " +
                "ORDER BY r.dateReserved DESC";
            
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, memberID);
            results = pstmt.executeQuery();
            
            while (results.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(results.getInt("resID"));
                reservation.setMemberID(results.getInt("memberID"));
                reservation.setMemberName(results.getString("memberName"));
                reservation.setDateReserved(results.getDate("dateReserved").toLocalDate());
                String statusStr = results.getString("_status");
                reservation.setStatus(Reservation.ReservationStatus.valueOf(statusStr.toUpperCase()));
                
                // Handle books
                String bookTitles = results.getString("bookTitles");
                String bookIDs = results.getString("bookIDs");
                
                if (bookTitles != null && bookIDs != null) {
                    String[] titles = bookTitles.split(", ");
                    String[] ids = bookIDs.split(", ");
                    
                    for (int i = 0; i < titles.length && i < ids.length; i++) {
                        Book book = createSimpleBook(Integer.parseInt(ids[i]), titles[i]);
                        reservation.addBook(book);
                    }
                }
                
                reservations.add(reservation);
            }
            
            results.close();
            pstmt.close();
            con.close();
            return reservations;
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public Reservation getReservationByID(int reservationID) {
        con = super.getConnection();

        try {
            pstmt = con.prepareStatement(
                "SELECT r.resID, r.memberID, r.dateReserved, r._status, " +
                "m.name as memberName, " +
                "GROUP_CONCAT(b.title SEPARATOR ', ') as bookTitles, " +
                "GROUP_CONCAT(b.bookID SEPARATOR ', ') as bookIDs " +
                "FROM reservation r " +
                "JOIN member m ON r.memberID = m.memberID " +
                "LEFT JOIN reservationbook rb ON r.resID = rb.resID " +
                "LEFT JOIN book b ON rb.bookID = b.bookID " +
                "WHERE r.resID = ? " +
                "GROUP BY r.resID, r.memberID, r.dateReserved, r._status, m.name"
            );
            pstmt.setInt(1, reservationID);
            results = pstmt.executeQuery();
            
            if (results.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(results.getInt("resID"));
                reservation.setMemberID(results.getInt("memberID"));
                reservation.setMemberName(results.getString("memberName"));
                reservation.setDateReserved(results.getDate("dateReserved").toLocalDate());
                
                String statusStr = results.getString("_status");
                reservation.setStatus(Reservation.ReservationStatus.fromString(statusStr));
                
                String bookTitles = results.getString("bookTitles");
                if (bookTitles != null && !bookTitles.isEmpty()) {
                    String[] titles = bookTitles.split(", ");
                    String[] bookIds = results.getString("bookIDs").split(", ");
                    
                    for (int i = 0; i < titles.length; i++) {
                        Book book = createSimpleBook(Integer.parseInt(bookIds[i]), titles[i]);
                        reservation.addBook(book);
                    }
                }
                
                results.close();
                pstmt.close();
                con.close();
                return reservation;
            } else {
                results.close();
                pstmt.close();
                con.close();
                return null;
            }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return null;
        }
    }

    public void addReservation(Reservation reservation) {
        con = super.getConnection();
        try {
            // Start transaction
            con.setAutoCommit(false);
            
            // Insert reservation
            pstmt = con.prepareStatement(
                "INSERT INTO reservation (memberID, dateReserved, _status) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            pstmt.setInt(1, reservation.getMemberID());
            pstmt.setDate(2, Date.valueOf(reservation.getDateReserved()));
            pstmt.setString(3, reservation.getStatus().name().toLowerCase());
            pstmt.executeUpdate();
            
            // Get generated reservation ID
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int reservationId = 0;
            if (generatedKeys.next()) {
                reservationId = generatedKeys.getInt(1);
                reservation.setReservationID(reservationId);
            }
            
            // Insert books
            if (reservation.getBooks() != null && !reservation.getBooks().isEmpty()) {
                pstmt = con.prepareStatement("INSERT INTO reservationbook (resID, bookID) VALUES (?, ?)");
                for (Book book : reservation.getBooks()) {
                    pstmt.setInt(1, reservationId);
                    pstmt.setInt(2, book.getBookID());
                    pstmt.executeUpdate();
                }
            }
            
            con.commit();
            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void updateReservation(Reservation reservation) {
        con = super.getConnection();
        try {
            con.setAutoCommit(false);
            
            // Update reservation
            pstmt = con.prepareStatement(
                "UPDATE reservation SET memberID = ?, dateReserved = ?, _status = ? WHERE resID = ?"
            );
            pstmt.setInt(1, reservation.getMemberID());
            pstmt.setDate(2, Date.valueOf(reservation.getDateReserved()));
            pstmt.setString(3, reservation.getStatus().name().toLowerCase());
            pstmt.setInt(4, reservation.getReservationID());
            pstmt.executeUpdate();
            
            /**
            // Delete existing books
            pstmt = con.prepareStatement("DELETE FROM reservationbook WHERE resID = ?");
            pstmt.setInt(1, reservation.getReservationID());
            pstmt.executeUpdate();
            */
            
            // Insert new books
            if (reservation.getBooks() != null && !reservation.getBooks().isEmpty()) {
                pstmt = con.prepareStatement("INSERT INTO reservationbook (resID, bookID) VALUES (?, ?)");
                for (Book book : reservation.getBooks()) {
                    pstmt.setInt(1, reservation.getReservationID());
                    pstmt.setInt(2, book.getBookID());
                    pstmt.executeUpdate();
                }
            }
            
            con.commit();
            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void deleteReservation(Reservation reservation) {
        con = super.getConnection();
        try {
            con.setAutoCommit(false);
            
            // Delete reservation books first (due to foreign key constraint)
            pstmt = con.prepareStatement("DELETE FROM reservationbook WHERE resID = ?");
            pstmt.setInt(1, reservation.getReservationID());
            pstmt.executeUpdate();
            
            // Delete reservation
            pstmt = con.prepareStatement("DELETE FROM reservation WHERE resID = ?");
            pstmt.setInt(1, reservation.getReservationID());
            pstmt.executeUpdate();
            
            con.commit();
            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            try {
                con.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public void updateReservationStatus(int reservationID, Reservation.ReservationStatus status) {
        con = super.getConnection();
        try {
            pstmt = con.prepareStatement("UPDATE reservation SET _status = ? WHERE resID = ?");
            pstmt.setString(1, status.name().toLowerCase());
            pstmt.setInt(2, reservationID);
            pstmt.executeUpdate();
            
            pstmt.close();
            con.close();
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public ArrayList<Member> getMembers() {
        UserMemberDAO memberDAO = new UserMemberDAO();
        return memberDAO.getMembers();
    }
}
