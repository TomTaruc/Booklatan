/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Components.Forms.MemberReservationsFormCon;
import Model.Reservation;
import Model.ReservationDAO;
import Model.Member;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 * Controller for member-specific reservations manager
 * @author Generated
 */
public class MemberReservationsManagerController {
    private ReservationsManager view;
    private ReservationDAO reservationDAO;
    private Member member;
    private Reservation selectedReservation;
    
    public MemberReservationsManagerController(ReservationsManager view, Member member) {        
        this.view = view;
        this.reservationDAO = new ReservationDAO();
        this.member = member;
        
        // Update button text and functionality for members
        view.addBtn.setText("New Reservation");
        view.claimBtn.setVisible(false); // Members can't claim reservations
        view.cancelBtn.setText("Cancel Reservation");
        
        updateReservationsTable();
        
        view.addBtn.addActionListener(new AddReservationEvent());
        view.reservationsTable.addMouseListener(new SelectReservationEvent());
        view.cancelBtn.addActionListener(new CancelReservationEvent());
        view.searchBar.getDocument().addDocumentListener(new SearchReservationEvent());
        view.statusFilter.addItemListener(new FilterReservationStatusEvent());
    }
    
    private void filterSearch() {
        String statusText = (String) view.statusFilter.getSelectedItem();
        Reservation.ReservationStatus status = getStatusFromString(statusText);
        
        if(view.searchBar.getText().equals("Search reservations") || view.searchBar.getText().trim().isEmpty()){
            if(status != null) {
                updateReservationsTable(filterByStatus(reservationDAO.getReservationsByMemberID(member.getMemberID()), status));
            } else {
                updateReservationsTable();
            }
        } else {
            ArrayList<Reservation> reservations = reservationDAO.getReservationsByMemberID(member.getMemberID());
            ArrayList<Reservation> filteredReservations = new ArrayList<>();
            
            String searchText = view.searchBar.getText().toLowerCase();
            
            for(Reservation reservation : reservations) {
                boolean matchesSearch = false;
                
                // Search in book titles
                for(Model.Book book : reservation.getBooks()) {
                    if(book.getTitle().toLowerCase().contains(searchText)) {
                        matchesSearch = true;
                        break;
                    }
                }
                
                // Search in reservation ID
                if(String.valueOf(reservation.getReservationID()).contains(searchText)) {
                    matchesSearch = true;
                }
                
                // Search in notes if available
                if(reservation.getNotes() != null && reservation.getNotes().toLowerCase().contains(searchText)) {
                    matchesSearch = true;
                }
                
                if(matchesSearch && (status == null || reservation.getStatus() == status)) {
                    filteredReservations.add(reservation);
                }
            }
            
            updateReservationsTable(filteredReservations);
        }
    }
    
    private ArrayList<Reservation> filterByStatus(ArrayList<Reservation> reservations, Reservation.ReservationStatus status) {
        ArrayList<Reservation> filtered = new ArrayList<>();
        for(Reservation reservation : reservations) {
            if(reservation.getStatus() == status) {
                filtered.add(reservation);
            }
        }
        return filtered;
    }
    
    private Reservation.ReservationStatus getStatusFromString(String statusText) {
        switch(statusText) {
            case "Pending":
                return Reservation.ReservationStatus.PENDING;
            case "Claimed":
                return Reservation.ReservationStatus.CLAIMED;
            case "Canceled":
                return Reservation.ReservationStatus.CANCELLED;
            default:
                return null;
        }
    }
    
    private void updateReservationsTable() {
        DefaultTableModel table = view.reservationsTableModel;
        ArrayList<Reservation> reservations = reservationDAO.getReservationsByMemberID(member.getMemberID());
        
        //Clear the table
        table.setRowCount(0);
        
        if(reservations != null) {
            for(Reservation reservation : reservations) {
                Object[] row = new Object[5];
                row[0] = reservation.getReservationID();
                row[1] = reservation.getMemberName();
                
                // Concatenate book titles
                StringBuilder bookTitles = new StringBuilder();
                for(int i = 0; i < reservation.getBooks().size(); i++) {
                    if(i > 0) bookTitles.append(", ");
                    bookTitles.append(reservation.getBooks().get(i).getTitle());
                }
                row[2] = bookTitles.toString();
                
                row[3] = reservation.getDateReserved().toString();
                row[4] = reservation.getStatus().toString().toLowerCase();
                // Capitalize first letter
                String status = (String) row[4];
                row[4] = status.substring(0, 1).toUpperCase() + status.substring(1);
                
                table.addRow(row);
            }
        }
    }
    
    private void updateReservationsTable(ArrayList<Reservation> reservations) {
        DefaultTableModel table = view.reservationsTableModel;
        
        //Clear the table
        table.setRowCount(0);
        
        if(reservations != null) {
            for(Reservation reservation : reservations) {
                Object[] row = new Object[5];
                row[0] = reservation.getReservationID();
                row[1] = reservation.getMemberName();
                
                // Concatenate book titles
                StringBuilder bookTitles = new StringBuilder();
                for(int i = 0; i < reservation.getBooks().size(); i++) {
                    if(i > 0) bookTitles.append(", ");
                    bookTitles.append(reservation.getBooks().get(i).getTitle());
                }
                row[2] = bookTitles.toString();
                
                row[3] = reservation.getDateReserved().toString();
                row[4] = reservation.getStatus().toString().toLowerCase();
                // Capitalize first letter
                String status = (String) row[4];
                row[4] = status.substring(0, 1).toUpperCase() + status.substring(1);
                
                table.addRow(row);
            }
        }
    }
    
    // ***** Event Handlers ******
    /// *** Add ***
    private class AddReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new MemberReservationsFormCon(member, () -> updateReservationsTable());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error opening reservation form: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
        
    }
    
    /// *** Cancel ***
    private class CancelReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(selectedReservation == null) {
                    throw new Exception("Please select a reservation to cancel.");
                }
                
                // Only allow canceling pending reservations
                if(selectedReservation.getStatus() != Reservation.ReservationStatus.PENDING) {
                    throw new Exception("Only pending reservations can be canceled.");
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    null, 
                    "Are you sure you want to cancel this reservation?", 
                    "Confirm Cancel", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if(confirm == JOptionPane.YES_OPTION) {
                    // Update reservation status to canceled
                    reservationDAO.updateReservationStatus(selectedReservation.getReservationID(), Reservation.ReservationStatus.CANCELLED);
                    updateReservationsTable();
                    selectedReservation = null;
                    JOptionPane.showMessageDialog(null, "Reservation has been canceled.");
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
    }
    
    // *** Select *** 
    private class SelectReservationEvent extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int selectedRow = view.reservationsTable.getSelectedRow();
            if(selectedRow >= 0) {
                int reservationId = (int) view.reservationsTable.getValueAt(selectedRow, 0);
                
                // Find the reservation object
                ArrayList<Reservation> reservations = reservationDAO.getReservationsByMemberID(member.getMemberID());
                for(Reservation reservation : reservations) {
                    if(reservation.getReservationID() == reservationId) {
                        selectedReservation = reservation;
                        break;
                    }
                }
            }
        }   
    }
    
    // *** Search ***
    private class SearchReservationEvent implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            filterSearch();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterSearch();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterSearch();
        }   
    }
    
    private class FilterReservationStatusEvent implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                filterSearch();
            }
        }
    }
}
