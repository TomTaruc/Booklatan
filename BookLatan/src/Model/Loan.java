/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDate;

/**
 *
 * @author Joseph Rey
 */
public class Loan {
    private int loanID;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private int memberID;
    private LoanStatus status;
    
    public int getLoanID() { return loanID; }
    public void setLoanID(int loanID) { this.loanID = loanID; }
    public LocalDate getIssueDate() { return issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public int getMemberID() { return memberID; }
    public void setMemberID(int memberID) { this.memberID = memberID; }
    public LoanStatus getStatus() { return status; };
    public void setStatus(LoanStatus status) { this.status = status; }
}
