/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author motar
 */
import java.util.Date;

public class Fine {
    private String fineId;
    private String memberId;
    private String memberName;
    private String bookTitle;
    private String isbn;
    private Date dueDate;
    private Date returnDate;
    private int daysOverdue;
    private double amount;
    private String status;
    private Date issuedDate;
    private Date paidDate;
    private String description;
    
    public Fine() {}
    
    public Fine(String fineId, String memberId, String memberName, String bookTitle, 
                String isbn, Date dueDate, Date returnDate, int daysOverdue, 
                double amount, String status, Date issuedDate, String description) {
        this.fineId = fineId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.daysOverdue = daysOverdue;
        this.amount = amount;
        this.status = status;
        this.issuedDate = issuedDate;
        this.description = description;
    }
    
    // Getters and Setters
    public String getFineId() { return fineId; }
    public void setFineId(String fineId) { this.fineId = fineId; }
    
    public String getMemberId() { return memberId; }
    public void setMemberId(String memberId) { this.memberId = memberId; }
    
    public String getMemberName() { return memberName; }
    public void setMemberName(String memberName) { this.memberName = memberName; }
    
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    
    public Date getDueDate() { return dueDate; }
    public void setDueDate(Date dueDate) { this.dueDate = dueDate; }
    
    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
    
    public int getDaysOverdue() { return daysOverdue; }
    public void setDaysOverdue(int daysOverdue) { this.daysOverdue = daysOverdue; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getIssuedDate() { return issuedDate; }
    public void setIssuedDate(Date issuedDate) { this.issuedDate = issuedDate; }
    
    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
