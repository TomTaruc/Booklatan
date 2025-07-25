/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dinel
 */
public class Reservation {
    private int reservationID;
    private int memberID;
    private String memberName;
    private LocalDate dateReserved;
    private ReservationStatus status;
    private List<Book> books;
    private String notes;

    // Constructors
    public Reservation() {
        this.books = new ArrayList<>();
    }

    public Reservation(int memberID, LocalDate dateReserved) {
        this();
        this.memberID = memberID;
        this.dateReserved = dateReserved;
        this.status = ReservationStatus.PENDING;
    }

    public Reservation(int reservationID, int memberID, String memberName, LocalDate dateReserved, ReservationStatus status) {
        this();
        this.reservationID = reservationID;
        this.memberID = memberID;
        this.memberName = memberName;
        this.dateReserved = dateReserved;
        this.status = status;
    }

    // Getters and Setters
    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public LocalDate getDateReserved() {
        return dateReserved;
    }

    public void setDateReserved(LocalDate dateReserved) {
        this.dateReserved = dateReserved;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books != null ? books : new ArrayList<>();
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }

    public void removeBook(Book book) {
        if (this.books != null) {
            this.books.remove(book);
        }
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Helper methods
    public String getBookTitles() {
        if (books == null || books.isEmpty()) {
            return "";
        }
        
        if (books.size() == 1) {
            return books.get(0).getTitle();
        }
        
        StringBuilder titles = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            titles.append(books.get(i).getTitle());
            if (i < books.size() - 1) {
                titles.append(", ");
            }
        }
        return titles.toString();
    }

    public String getBookIDs() {
        if (books == null || books.isEmpty()) {
            return "";
        }
        
        StringBuilder ids = new StringBuilder();
        for (int i = 0; i < books.size(); i++) {
            ids.append(books.get(i).getBookID());
            if (i < books.size() - 1) {
                ids.append(", ");
            }
        }
        return ids.toString();
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", memberID=" + memberID +
                ", memberName='" + memberName + '\'' +
                ", dateReserved=" + dateReserved +
                ", status=" + status +
                ", books=" + getBookTitles() +
                ", notes='" + notes + '\'' +
                '}';
    }

    // Enumerator for Reservation Status
    public enum ReservationStatus {
        ALL("All Statuses"), // For filtering
        PENDING("Pending"),
        CLAIMED("Claimed"),
        CANCELLED("Cancelled");

        private final String label;

        ReservationStatus(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }

        public static ReservationStatus fromString(String value) {
            if (value == null) return PENDING;
            
            for (ReservationStatus status : ReservationStatus.values()) {
                if (status.name().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            return PENDING;
        }
    }
}
