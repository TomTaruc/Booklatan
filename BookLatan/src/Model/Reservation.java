/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.util.Date;
/**
 *
 * @author user
 */
public class Reservation {

    void add(Reservation reservation) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

   
    public enum status {
        cancelled, claimed, pending
    }

    private status status; 

    public void setStatus(status status) {
        this.status = status;
    }

    public status getStatus() {
        return this.status;
    }
    
    private int reservationID;
    private Book book;
    private Member member;
    private Date dateReservered;
    private Date dateClaimed;

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Date getDateReservered() {
        return dateReservered;
    }

    public void setDateReservered(Date dateReservered) {
        this.dateReservered = dateReservered;
    }

    public Date getDateClaimed() {
        return dateClaimed;
    }

    public void setDateClaimed(Date dateClaimed) {
        this.dateClaimed = dateClaimed;
    }
    
}
