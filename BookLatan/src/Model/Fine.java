package Model;

import Utilities.Validators;
import java.time.LocalDate;
import java.util.Date;

public class Fine {
    private int fineID;
    private int staffID;
    private int memberID;
    private LocalDate dueDate;
    private double amount;
    private FineStatus status;
    private LocalDate dateIssued;
    private LocalDate paidDate;
    private String description;

    // **** Getters ******

    public int getFineID() {
        return fineID;
    }

    public int getStaffID() {
        return staffID;
    }

    public int getMemberID() {
        return memberID;
    }


    public LocalDate getDue_date() {
        return dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public FineStatus getStatus() {
        return status;
    }

    public LocalDate getDateIssued() {
        return dateIssued;
    }

    public LocalDate getPaid_date() {
        return paidDate;
    }

    public String getDescription() {
        return description;
    }
    
    
    // ****** Setterss *****

    public void setFineID(int fineID) {
        Validators.validateEmptyVariable(fineID, "Fine ID");
        this.fineID = fineID;
    }

    public void setStaffID(int staffID) {
        Validators.validateEmptyVariable(staffID, "Staff ID");
        this.staffID = staffID;
    }

    public void setMemberID(int memberID) {
        Validators.validateEmptyVariable(memberID, "Member ID");
        this.memberID = memberID;
    }

    public void setDue_date(LocalDate due_date) {
        Validators.validateEmptyVariable(due_date, description);
        this.dueDate = due_date;
    }

    public void setAmount(double amount) {
        Validators.validateEmptyVariable(amount, "Amount");
        Validators.validateLimitValue(amount, 10.75, 10000.0);
        this.amount = amount;
    }

    public void setStatus(FineStatus status) {
        Validators.validateEmptyVariable(status, "Status");
        this.status = status;
    }

    public void setDateIssued(LocalDate dateIssued) {
        Validators.validateEmptyVariable(dateIssued, "Date Issued");
        this.dateIssued = dateIssued;
    }

    public void setPaid_date(LocalDate paid_date) {
        this.paidDate = paid_date;
    }

    public void setDescription(String description) {
        Validators.validateEmptyVariable(description, "Description");
        this.description = description;
    }
    
}