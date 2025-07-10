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

/**
 *
 * @author user
 */
public class ReservationDAO extends DAO{
    public ArrayList<Reservation> getReservation () {
        Connection con = super.getConnection();
        ArrayList<Reservation> reservations = new ArrayList<>();
        Statement stmt;
        ResultSet results;
        try{
            stmt = con.createStatement();
            results = stmt.executeQuery("SELECT * FROM Reservation;");
            while(results.next()) {
                Reservation reservation = new Reservation();
                // Member Attributes
                reservation.setReservationID(results.getInt("reservtionID"));
                reservation.getMember().getMemberID();
                reservations.add(reservation);
            }
            results.close();
            stmt.close();
            con.close();
            return reservations;
        }
        catch(SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
   /*  
    public void deleteReservation(Reservation reservation) {
        Connection con = super.getConnection();
        try {
            Statement stmt = con.createStatement();
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0;");
            stmt.execute("DELETE FROM RESERVATION WHERE resID =" + reservation.getReservationID() + ";");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1;");
            stmt.close();
            con.close();   
        }
        catch (SQLException ex) {
        }
    }
    public void updateUser(User user) {
        Connection con = super.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("UPDATE USERS SET userName = ?, password = ?, type = ? WHERE userID = ?");
            stmt.setString(1, user.getUserName());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getType().toString().toLowerCase());
            stmt.setInt(4, user.getUserID());
            stmt.execute();
            stmt.close();
            con.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
*/
}
