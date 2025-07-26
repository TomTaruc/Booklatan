/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Model.BookDAO;
/**
 *
 * @author Joseph Rey
 */
public class LoanDAO extends DataAccessObject{
    private Connection con;
    private PreparedStatement pstmt;
    private ResultSet results;
    private ArrayList<Loan> loans = new ArrayList<>();
    private Loan loan;
    private Book book;
    private ArrayList<Book> books = new ArrayList<>();
    private AuthorDAO authorDAO = new AuthorDAO();
    private PublisherDAO pubDAO = new PublisherDAO();
    private BookDAO bookDAO = new BookDAO();
    
    public ArrayList<Loan> getLoans(LoanStatus status) {
        con = super.getConnection();
        loans.clear();
        
        try {
            if(status == LoanStatus.ALL) {
                pstmt = con.prepareStatement("Select * FROM LoanMember ORDER BY dueDate asc");
            }
            else {
                pstmt = con.prepareStatement("Select * FROM LoanMember WHERE status = ? ORDER BY dueDate asc");
                pstmt.setString(1, status.toString().toLowerCase());
            }
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                Loan loan = new Loan();
                loan.setLoanID(Integer.parseInt(results.getString("loanID")));
                loan.setMemberID(Integer.parseInt(results.getString("memberID")));
                loan.setIssueDate(results.getDate("issueDate").toLocalDate());
                loan.setDueDate(results.getDate("dueDate").toLocalDate());
                loan.setReturnDate(results.getDate("returnDate") != null ? results.getDate("returnDate").toLocalDate() : null);
                loan.setStatus(LoanStatus.fromString(results.getString("status")));
                this.checkDueDate(loan);
                loans.add(loan);
            }
            
            results.close();
            pstmt.close();
            con.close();
            
            return loans;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
    
    public ArrayList<Loan> getLoans(String search, LoanStatus status) {
        con = super.getConnection();
        loans.clear();
        
        try {
            if(status == LoanStatus.ALL) {
                pstmt = con.prepareStatement("Select * FROM  LoanMember WHERE name like ? ORDER BY dueDate asc");
                pstmt.setString(1, "%" + search + "%");
            }
            else {
                pstmt = con.prepareStatement("Select * FROM LoanMember WHERE status = ? and name like ? ORDER BY dueDate asc");
                pstmt.setString(1, status.toString().toLowerCase());
                pstmt.setString(2, "%" + search + "%");
            }
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                Loan loan = new Loan();
                loan.setLoanID(Integer.parseInt(results.getString("loanID")));
                loan.setMemberID(Integer.parseInt(results.getString("memberID")));
                loan.setIssueDate(results.getDate("issueDate").toLocalDate());
                loan.setDueDate(results.getDate("dueDate").toLocalDate());
                loan.setReturnDate(results.getDate("returnDate") != null ? results.getDate("returnDate").toLocalDate() : null);
                loan.setStatus(LoanStatus.fromString(results.getString("status")));
                this.checkDueDate(loan);
                loans.add(loan);
            }
            
            results.close();
            pstmt.close();
            con.close();
            
            return loans;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
    public Loan getLoan(int loanID) {
        con = super.getConnection();
        loans.clear();
        
        try {
            pstmt = con.prepareStatement("SELECT * FROM LoanMember Where loanID = ?");
            pstmt.setInt(1, loanID);
            
            results = pstmt.executeQuery();
            
            if(results.next()) {
                loan = new Loan();
                loan.setLoanID(Integer.parseInt(results.getString("loanID")));
                loan.setMemberID(Integer.parseInt(results.getString("memberID")));
                loan.setIssueDate(results.getDate("issueDate").toLocalDate());
                loan.setDueDate(results.getDate("dueDate").toLocalDate());
                loan.setReturnDate(results.getDate("returnDate") != null ? results.getDate("returnDate").toLocalDate() : null);
                loan.setStatus(LoanStatus.fromString(results.getString("status")));
                this.checkDueDate(loan);
            }
            
            results.close();
            pstmt.close();
            con.close();
            
            return loan;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
    public ArrayList<Book> getLoanedBooks(int loanID) {
        con = super.getConnection();
        loans.clear();
        
        try {
            pstmt = con.prepareStatement("SELECT * FROM LoanedBooks Where loanID = ?");
            pstmt.setInt(1, loanID);
            
            results = pstmt.executeQuery();
            
            while(results.next()) {
                book = new Book();
                book.setBookID(results.getInt("bookID"));
                book.setTitle(results.getString("Title"));
                book.setAuthors(authorDAO.getAllAuthors(book.getBookID()));
                book.setPublisher(pubDAO.getPublisherById(results.getInt("pubID")));
                book.setCategory(results.getString("category"));
                book.setPubDate(results.getDate("pubDate"));
                book.setLanguage(results.getString("lang"));
                book.setShelfLocation(results.getString("shelfLocation"));
                book.setStatus(BookStatus.fromString(results.getString("_status")));
                books.add(book);
            }
            
            results.close();
            pstmt.close();
            con.close();
            
            return books;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
            return null;
        }
    }
    
    public void updateLoanStatus(Loan loan) {
        con = super.getConnection();
        loans.clear();
        
        try {
            
            if(loan.getStatus() == LoanStatus.RETURNED) {
                pstmt = con.prepareStatement("UPDATE loan SET status = ?, returnDate = ? WHERE loanID = ?");
                pstmt.setString(1, loan.getStatus().toString());
                pstmt.setDate(2, Date.valueOf(loan.getReturnDate()));
                pstmt.setInt(3, loan.getLoanID());
            }
            else {
                pstmt = con.prepareStatement("UPDATE loan SET status = ? WHERE loanID = ?");
                pstmt.setString(1, loan.getStatus().toString());
                pstmt.setInt(2, loan.getLoanID());
            }
            
            pstmt.execute();
            
            pstmt.close();
            con.close();
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public void checkDueDate(Loan loan) {
        
        if(loan.getStatus() != LoanStatus.PENDING) {
            return;
        }
        
        LocalDate today = LocalDate.now();
        Period period = Period.between(today, loan.getDueDate());
        if(period.getDays() < 0) {
            loan.setStatus(LoanStatus.OVERDUE);
            updateLoanStatus(loan);
        }
    }
    
    
    public void deleteLoan(Loan loan) {
        con = super.getConnection();
        
        try {
            pstmt = con.prepareStatement("{Call deleteLoan(?)}");
            pstmt.setInt(1, loan.getLoanID());
            pstmt.execute();
            
            pstmt.close();
            con.close();
            
        }
        catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
    public void addLoan(Loan loan, ArrayList<Book> books) {
        con = super.getConnection();
        int loanID = -1;
        try {
            pstmt = con.prepareStatement("INSERT INTO Loan(issueDate, dueDate, memberID, status) VALUES (?, ?, ?, 'pending')", Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, Date.valueOf(loan.getIssueDate()));
            pstmt.setDate(2, Date.valueOf(loan.getDueDate()));
            pstmt.setInt(3, loan.getMemberID());
            int affectedRows = pstmt.executeUpdate();
            
            if(affectedRows > 0) {
                results = pstmt.getGeneratedKeys();
                if(results.next()) {
                    loanID = results.getInt(1);
                }
            }
            
            if(loanID > -1) {
                for(Book book : books) {
                    pstmt = con.prepareStatement("INSERT INTO loanbook(loanID, bookID) VALUES (?, ?)");
                    pstmt.setInt(1, loanID);
                    pstmt.setInt(2, book.getBookID());
                    pstmt.execute();
                    
                    book.setStatus(BookStatus.LOANED);
                    bookDAO.updateBook(book);
                    
                }
            }
            
            results.close();
            pstmt.close();
            con.close();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    
}
