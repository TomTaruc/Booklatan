/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import java.util.List;
import Model.Member;
import Model.Reservation;
import Model.Reservation.status;
import java.util.ArrayList;
import Model.Book;
import java.util.Date;

/**
 *
 * @author user
 */
public class ReservationController {
    Date currentDate = new Date();
    private List<Reservation> reservations;
    private List<Book> books;     
    private List<Member> members;
    
    public ReservationController() {
    this.reservations = new ArrayList<>();
    }
    public void reserveBook(int bookID, int memberID) {
        // Find the book and member by ID
        Book selectedBook = null;
        Member selectedMember = null;

        for (Book b : books) {
            if (b.getBookID() == bookID) {
                selectedBook = b;
                break;
            }
        }

        for (Member m : members) {
            if (m.getMemberID() == memberID) {
                selectedMember = m;
                break;
            }
        }

        if (selectedBook == null || selectedMember == null) {
            System.out.println("Book or Member not found.");
            return;
        }

        
        Reservation reservation = new Reservation();
        reservation.setReservationID(generateReservationID()); 
        reservation.setBook(selectedBook);
        reservation.setMember(selectedMember);
        reservation.setDateReservered(new Date());
        reservation.setStatus(status.pending);
        reservation.setDateClaimed(null); 

        reservations.add(reservation);

        System.out.println("Reservation successful for member: " + memberID);
    }
    
    public int cancelReservation(int reservationID) {
        for (Reservation res : reservations) {
            if (res.getReservationID() == reservationID && res.getStatus() == status.pending) {
                res.setStatus(status.cancelled);
                System.out.println("🔄 Reservation " + reservationID + " canceled.");
                return 1; // success
            }
        }
        System.out.println("⚠️ Reservation not found or not cancelable.");
        return -1;
    }

    private int generateReservationID() {
        return reservations.size() + 1;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }
}