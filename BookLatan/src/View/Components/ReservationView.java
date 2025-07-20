/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;

import Model.Reservation;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author user
 */
public class ReservationView {
    public void showMemberReservations(DefaultTableModel model, ArrayList<Reservation> reservations) {
        model.setRowCount(0);
        for(Reservation reservation : reservations) {
            model.addRow(new Object[] {reservation.getReservationID(), reservation.getBook(), reservation.getMember().getMemberID(), reservation.getDateReservered()});
        }
    }
    
    public void MemberReservations() {
    }
    
    public void showReservationList(DefaultTableModel model, ArrayList<Reservation> reservations) {
        model.setRowCount(0);
        for(Reservation reservation : reservations) {
            model.addRow(new Object[] {reservation.getReservationID(), reservation.getBook(), reservation.getMember().getMemberID(), reservation.getDateReservered(), reservation.getStatus()});
        }
    }
    
    public void showReservationList() {
    }
}
