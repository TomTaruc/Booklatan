package Model;

import java.util.Date;

public class Fine {
    private String fineID;
    private String staffID;
    private String memberID;
    private String memberName;
    private String book_title;
    private String isbn;
    private Date due_date;
    private Date return_date;
    private int days_overdue;
    private double amount;
    private String _status;
    private Date dateIssued;
    private Date paid_date;
    private String description;
    private String reason;

    public Fine() {
    }

    public Fine(String fineID, String staffID, String memberID, String memberName,
                double amount, String reason, Date dateIssued, String _status,
                String book_title, Date due_date, Date return_date, int days_overdue,
                String isbn, String description) {
        this.fineID = fineID;
        this.staffID = staffID;
        this.memberID = memberID;
        this.memberName = memberName;
        this.amount = amount;
        this.reason = reason;
        this.dateIssued = dateIssued;
        this._status = _status;
        this.book_title = book_title;
        this.due_date = due_date;
        this.return_date = return_date;
        this.days_overdue = days_overdue;
        this.isbn = isbn;
        this.description = description;
    }

    public String getFineID() {
        return fineID;
    }

    public void setFineID(String fineID) {
        this.fineID = fineID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Date getDue_date() {
        return due_date;
    }

    public void setDue_date(Date due_date) {
        this.due_date = due_date;
    }

    public Date getReturn_date() {
        return return_date;
    }

    public void setReturn_date(Date return_date) {
        this.return_date = return_date;
    }

    public int getDays_overdue() {
        return days_overdue;
    }

    public void setDays_overdue(int days_overdue) {
        this.days_overdue = days_overdue;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public Date getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = dateIssued;
    }

    public Date getPaid_date() {
        return paid_date;
    }

    public void setPaid_date(Date paid_date) {
        this.paid_date = paid_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "Fine{" +
               "fineID='" + fineID + '\'' +
               ", staffID='" + staffID + '\'' +
               ", memberID='" + memberID + '\'' +
               ", memberName='" + memberName + '\'' +
               ", book_title='" + book_title + '\'' +
               ", isbn='" + isbn + '\'' +
               ", due_date=" + due_date +
               ", return_date=" + return_date +
               ", days_overdue=" + days_overdue +
               ", amount=" + amount +
               ", _status='" + _status + '\'' +
               ", dateIssued=" + dateIssued +
               ", paid_date=" + paid_date +
               ", description='" + description + '\'' +
               ", reason='" + reason + '\'' +
               '}';
    }
}
