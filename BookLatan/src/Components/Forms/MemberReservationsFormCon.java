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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Controller for member-specific reservation form
 * @author Generated
 */
public class MemberReservationsFormCon {
    private MemberReservationsForm view;
    private ReservationDAO reservationDAO;
    private BookDAO bookDAO;
    private Member member;
    private Runnable updateTableCallback;
    
    public MemberReservationsFormCon(Member member, Runnable updateTable) {
        this.view = new MemberReservationsForm();
        this.reservationDAO = new ReservationDAO();
        this.bookDAO = new BookDAO();
        this.member = member;
        this.updateTableCallback = updateTable;
        
        loadBooks();
        
        view.setVisible(true);
        
        view.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validate inputs
                    Book selectedBook = (Book) view.bookComboBox.getSelectedItem();
                    
                    if (selectedBook == null) {
                        view.showErrorMessage("Please select a book");
                        return;
                    }
                    
                    // Create reservation
                    Reservation reservation = new Reservation();
                    reservation.setMemberID(member.getMemberID());
                    reservation.setMemberName(member.getName());
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
                        "Your reservation for \"" + selectedBook.getTitle() + "\" has been created successfully.\nWould you like to create another reservation?", 
                        "Success", 
                        JOptionPane.YES_NO_OPTION
                    );
                    
                    if (confirm == JOptionPane.NO_OPTION) {
                        view.dispose();
                    } else {
                        // Reset form for new reservation
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
