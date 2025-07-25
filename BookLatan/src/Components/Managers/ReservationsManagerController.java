package Components.Managers;

import Components.Forms.FineFormCon;
import Components.Forms.FineInformationForm;
import Components.Forms.ReservationsForm;
import Components.Forms.ReservationsFormCon;
import Model.Reservation;
import Model.ReservationDAO;
import Model.Member;
import Components.Managers.ReservationsManager;
import Model.Staff;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Joseph Rey
 */
public class ReservationsManagerController {
    private ReservationsManager view;
    private ReservationDAO reservationDAO;
    private Reservation selectedReservation;
    private ReservationsFormCon con;
    private ReservationsForm form;
    private Staff staff;
    
    public ReservationsManagerController(ReservationsManager view, boolean isAdmin, Staff staff) {        
        this.view = view;
        this.reservationDAO = new ReservationDAO();
        this.staff = staff;
        
        updateReservationsTable(); //automatically refresh instantiation.
        view.addBtn.addActionListener(new AddReservationEvent());
        view.reservationsTable.addMouseListener(new SelectReservationEvent());
        view.claimBtn.addActionListener(new ClaimReservationEvent());
        view.cancelBtn.addActionListener(new CancelReservationEvent());
        view.searchBar.getDocument().addDocumentListener(new SearchReservationEvent());
        view.statusFilter.addItemListener(new FilterReservationStatusEvent());
    }
    
    private void filterSearch() {
        String statusText = (String) view.statusFilter.getSelectedItem();
        Reservation.ReservationStatus status = getStatusFromString(statusText);
        
        if(view.searchBar.getText().equals("Search reservations") || view.searchBar.getText().trim().isEmpty()){
            updateReservationsTable(reservationDAO.getReservations(status));   
        }
        else {
            updateReservationsTable(reservationDAO.getReservations(view.searchBar.getText(), status));
        }
    }
    
    private Reservation.ReservationStatus getStatusFromString(String statusText) {
        switch(statusText) {
            case "Pending":
                return Reservation.ReservationStatus.PENDING;
            case "Claimed":
                return Reservation.ReservationStatus.CLAIMED;
            case "Cancelled":
                return Reservation.ReservationStatus.CANCELLED;
            default:
                return Reservation.ReservationStatus.ALL;
        }
    }
    
    private void updateReservationsTable() {
        DefaultTableModel table = view.reservationsTableModel;
        ArrayList<Reservation> reservations = reservationDAO.getReservations();
        
        //Clear the table
        table.setRowCount(0);
        
        if(reservations != null) {
            for(Reservation reservation : reservations) {
                // Get book titles as a single string
                String bookTitles = "";
                if(reservation.getBooks() != null && !reservation.getBooks().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < reservation.getBooks().size(); i++) {
                        if(i > 0) sb.append(", ");
                        sb.append(reservation.getBooks().get(i).getTitle());
                    }
                    bookTitles = sb.toString();
                }
                
                // "Reservation ID", "Member Name", "Book Title", "Date Reserved", "Status"
                table.addRow(new Object[] {
                    String.format("%06d", reservation.getReservationID()),
                    reservation.getMemberName(),
                    bookTitles,
                    reservation.getDateReserved().toString(),
                    reservation.getStatus().toString().toLowerCase()
                });
            }
        }
    }
    
    private void updateReservationsTable(ArrayList<Reservation> reservations) {
        DefaultTableModel table = view.reservationsTableModel;
        
        //Clear the table
        table.setRowCount(0);
        
        if(reservations != null) {
            for(Reservation reservation : reservations) {
                // Get book titles as a single string
                String bookTitles = "";
                if(reservation.getBooks() != null && !reservation.getBooks().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < reservation.getBooks().size(); i++) {
                        if(i > 0) sb.append(", ");
                        sb.append(reservation.getBooks().get(i).getTitle());
                    }
                    bookTitles = sb.toString();
                }
                
                // "#", "Member", "Books", "Date", "Status"
                table.addRow(new Object[] {
                    String.format("%06d", reservation.getReservationID()),
                    reservation.getMemberName(),
                    bookTitles,
                    reservation.getDateReserved().toString(),
                    reservation.getStatus().toString().toLowerCase()
                });
            }
        }
    }
    
    // ***** Event Handlers ******
    /// *** Add ***
    private class AddReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {         
            // This would open a form to add a new reservation
            // For now, show a placeholder message
            //JOptionPane.showMessageDialog(null, "Add Reservation functionality would open a form here.", "Info", JOptionPane.INFORMATION_MESSAGE);
            new ReservationsFormCon(staff, () -> updateReservationsTable());
        }
        
    }
    
    /// *** Update ***
    private class UpdateReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedReservation == null) {
                    throw new Exception("Please select a reservation to update.");
                }
                
                // Parse fields - assuming field order: member name, books, date, status, notes
                if(fields.size() >= 4) {
                    // Update member ID would require looking up by name
                    // For now, we'll just update date and status
                    
                    // Parse date
                    try {
                        LocalDate date = LocalDate.parse(fields.get(2).getText());
                        selectedReservation.setDateReserved(date);
                    } catch (DateTimeParseException ex) {
                        throw new Exception("Invalid date format. Use YYYY-MM-DD format.");
                    }
                    
                    // Parse status
                    String statusText = fields.get(3).getText().trim().toUpperCase();
                    Reservation.ReservationStatus status = Reservation.ReservationStatus.valueOf(statusText);
                    selectedReservation.setStatus(status);
                    
                    // Update notes if available
                    if(fields.size() > 4) {
                        selectedReservation.setNotes(fields.get(4).getText());
                    }
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            reservationDAO.updateReservation(selectedReservation);
            updateReservationsTable();
            JOptionPane.showMessageDialog(null, "Reservation has been updated.");
        } 
    }
    /**
    /// *** Delete ***
    private class DeleteReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedReservation == null) {
                    throw new Exception("Please select a reservation to delete.");
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    null, 
                    "Are you sure you want to delete this reservation?", 
                    "Confirm Delete", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if(confirm == JOptionPane.YES_OPTION) {
                    for(JTextField field : fields) {
                        field.setText("");
                    }
                    reservationDAO.deleteReservation(selectedReservation);
                    updateReservationsTable();
                    selectedReservation = null;
                    JOptionPane.showMessageDialog(null, "Reservation has been deleted.");
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
    }
    */
    
    private class CancelReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(selectedReservation == null) {
                    throw new Exception("Please select a reservation to cancel.");
                }
                
                if(selectedReservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
                    throw new Exception("This reservation has already been canceled.");
                }
                
                if(selectedReservation.getStatus() == Reservation.ReservationStatus.CLAIMED) {
                    throw new Exception("Cannot cancel a claimed reservation.");
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    null, 
                    "Mark this reservation as cancelled?", 
                    "Confirm Cancellation", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if(confirm == JOptionPane.YES_OPTION) {
                    reservationDAO.updateReservationStatus(selectedReservation.getReservationID(), Reservation.ReservationStatus.CANCELLED);
                    selectedReservation.setStatus(Reservation.ReservationStatus.CANCELLED);
                    
                    // Update the status field if available
                    ArrayList<JTextField> fields = view.fields;
                    if(fields.size() > 3) {
                        fields.get(3).setText("cancelled");
                    }
                    
                    updateReservationsTable();
                    JOptionPane.showMessageDialog(null, "Reservation for has been marked as cancelled.");
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
    }
    
    /// *** Claim ***
    private class ClaimReservationEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                if(selectedReservation == null) {
                    throw new Exception("Please select a reservation to claim.");
                }
                
                if(selectedReservation.getStatus() == Reservation.ReservationStatus.CLAIMED) {
                    throw new Exception("This reservation has already been claimed.");
                }
                
                if(selectedReservation.getStatus() == Reservation.ReservationStatus.CANCELLED) {
                    throw new Exception("Cannot claim a canceled reservation.");
                }
                
                int confirm = JOptionPane.showConfirmDialog(
                    null, 
                    "Mark this reservation as claimed?", 
                    "Confirm Claim", 
                    JOptionPane.YES_NO_OPTION
                );
                
                if(confirm == JOptionPane.YES_OPTION) {
                    reservationDAO.updateReservationStatus(selectedReservation.getReservationID(), Reservation.ReservationStatus.CLAIMED);
                    selectedReservation.setStatus(Reservation.ReservationStatus.CLAIMED);
                    
                    // Update the status field if available
                    ArrayList<JTextField> fields = view.fields;
                    if(fields.size() > 3) {
                        fields.get(3).setText("claimed");
                    }
                    
                    updateReservationsTable();
                    JOptionPane.showMessageDialog(null, "Reservation has been marked as claimed.");
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
            JTable table = view.reservationsTable;
            DefaultTableModel tableModel = view.reservationsTableModel;
            ArrayList<JTextField> fields = view.fields;
            int selectedRowIndex = table.getSelectedRow();
            
            int reservationID;
            
            try {
                reservationID = Integer.parseInt(table.getValueAt(selectedRowIndex, 0).toString());
            }
            catch(Exception ex) {
                return;
            }
            
            selectedReservation = reservationDAO.getReservationByID(reservationID);
            
            if(selectedReservation != null && fields.size() >= 4) {
                //String[] labelNames = {"Reservation ID", "Date Reserved", "Status", "Member Name", "Member ID", "Book Title"};
                fields.get(0).setText(String.valueOf(selectedReservation.getReservationID()));
                fields.get(1).setText(selectedReservation.getDateReserved().toString());
                fields.get(2).setText(selectedReservation.getStatus().toString().toLowerCase());
                fields.get(3).setText(selectedReservation.getMemberName() != null ? selectedReservation.getMemberName() : "");
                fields.get(4).setText(String.valueOf(selectedReservation.getMemberID()));
                
                // Books field
                String bookTitles = "";
                if(selectedReservation.getBooks() != null && !selectedReservation.getBooks().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0; i < selectedReservation.getBooks().size(); i++) {
                        if(i > 0) sb.append(", ");
                        sb.append(selectedReservation.getBooks().get(i).getTitle());
                    }
                    bookTitles = sb.toString();
                }
                fields.get(5).setText(bookTitles);
            }
            
            view.claimBtn.setEnabled(!Reservation.ReservationStatus.CLAIMED.equals(selectedReservation.getStatus()));
            view.claimBtn.setPrimaryColor(view.claimBtn.isEnabled() ? view.btnPrimaryColor : view.disabledColor);
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
            filterSearch();
        }
    }
}
