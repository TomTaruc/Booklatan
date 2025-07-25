/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Book;
import Model.BookDAO;
import Model.Member;
import Model.Reservation;
import Model.ReservationDAO;
import Model.Staff;
import Model.UserMemberDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class ReservationsFormCon {
    private ReservationsForm view;
    private ReservationDAO reservationDAO;
    private UserMemberDAO memberDAO;
    private BookDAO bookDAO;
    private Runnable updateTableCallback;
    
    public ReservationsFormCon(Staff staff, Runnable updateTable) {
        this.view = new ReservationsForm();
        this.memberDAO = new UserMemberDAO();
        this.reservationDAO = new ReservationDAO();
        this.bookDAO = new BookDAO();
        this.updateTableCallback = updateTable;
        
        loadMembers();
        loadBooks();
        
        view.setVisible(true);
        
        view.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate inputs
                    Member selectedMember = (Member) view.memberComboBox.getSelectedItem();
                    Book selectedBook = (Book) view.bookComboBox.getSelectedItem();
                    
                    if (selectedMember == null) {
                        view.showErrorMessage("Please select a member");
                        return;
                    }
                    
                    if (selectedBook == null) {
                        view.showErrorMessage("Please select a book");
                        return;
                    }
                    
                    // Create reservation
                    Reservation reservation = new Reservation();
                    reservation.setMemberID(selectedMember.getMemberID());
                    reservation.setMemberName(selectedMember.getName());
                    reservation.setDateReserved(((Date) view.reservationDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    reservation.setStatus(Reservation.ReservationStatus.PENDING);
                    reservation.setNotes(view.notes.getText().trim());
                    
                    // Add the selected book to the reservation
                    reservation.addBook(selectedBook);
                    
                    // Save reservation
                    reservationDAO.addReservation(reservation);
                    
                    // Update the table
                    if (updateTableCallback != null) {
                        updateTableCallback.run();
                    }
                    
                    // Show success message and ask if user wants to create another reservation
                    int confirm = JOptionPane.showConfirmDialog(
                        view, 
                        "Reservation for " + selectedMember.getName() + " has been created successfully.\nWould you like to create another reservation?", 
                        "Success", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirm == JOptionPane.NO_OPTION) {
                        view.dispose();
                    } else {
                        // Reset form for new reservation
                        view.memberComboBox.setSelectedIndex(0);
                        view.bookComboBox.setSelectedIndex(0);
                        view.reservationDate.setValue(new Date());
                        view.notes.setText("");
                    }
                    
                } catch (Exception ex) {
                    view.showErrorMessage("Error creating reservation: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        view.cancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                view, 
                "Are you sure you want to cancel? Any unsaved changes will be lost.", 
                "Cancel", 
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
            }
        });
    }
    
    private void loadMembers() {
        try {
            List<Member> members = memberDAO.getMembers();
            // Filter only active members
            members.removeIf(member -> member.getStatus() != Member.MembershipStatus.ACTIVE);
            view.setMembers(members);
        } catch (Exception ex) {
            view.showErrorMessage("Error loading members: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            // Filter only available books
            books.removeIf(book -> book.getStatus() != Model.BookStatus.AVAILABLE);
            view.setBooks(books);
        } catch (Exception ex) {
            view.showErrorMessage("Error loading books: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
