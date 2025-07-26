/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
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
            
            if(results.next()) {
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
    
    
}
