/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Book;
import Model.BookDAO;
import Model.BookStatus;
import Model.Loan;
import Model.LoanDAO;
import Model.LoanStatus;
import Model.Member;
import Model.Staff;
import Model.UserMemberDAO;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class LoanInformationCon {
    private Loan loan;
    private Member member;
    private ArrayList<Book> books;
    private UserMemberDAO memDAO;
    private LoanInformationForm view;
    private LoanDAO loanDAO;
    private Staff staff;
    private Runnable updateTable;
    private BookDAO bookDAO;
    
    public LoanInformationCon(Staff staff, Loan loan, Runnable updateTable) {
        this.updateTable = updateTable;
        this.staff = staff;
        this.view = new LoanInformationForm();
        this.memDAO = new UserMemberDAO();
        this.loanDAO = new LoanDAO();
        this.bookDAO = new BookDAO();
        this.loan = loan;
        this.member = memDAO.getMemberByID(loan.getMemberID());
        this.books = this.loanDAO.getLoanedBooks(this.loan.getLoanID());
        
        
        updateField();
        
        this.view.deleteLoan.addActionListener(e -> {
           int confirm = JOptionPane.showConfirmDialog(view, "Are you sure?", "Confirmation", JOptionPane.YES_NO_OPTION);
           if(confirm == JOptionPane.YES_OPTION) {
               for(Book book : books) {
                    try {
                        book.setStatus(BookStatus.AVAILABLE);
                        bookDAO.updateBook(book);
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            
                loanDAO.deleteLoan(loan);
                this.view.dispose();
                this.updateTable.run();
           }
        });
        
        this.view.markAsReturned.addActionListener(e -> {
            if(loan.getStatus() == LoanStatus.PENDING) {
                loan.setStatus(LoanStatus.RETURNED);
            }
            loan.setReturnDate(LocalDate.now());
            loanDAO.updateLoanStatus(loan);
            
            for(Book book : books) {
                try {
                    book.setStatus(BookStatus.AVAILABLE);
                    bookDAO.updateBook(book);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
            
            this.updateTable.run();
            this.updateField();
        });
        
        if(loan.getStatus() == LoanStatus.OVERDUE) {
            LocalDate today = LocalDate.now();
            Period period = Period.between(today, loan.getDueDate());
            double minimumFine  = 15.75d;
            double totalFine = minimumFine * Math.abs(period.getDays());
            String description;
            
            int confirm = JOptionPane.showConfirmDialog(view, member.getName() + "'s loan is " + Math.abs(period.getDays()) + " days overdue. Would you like to issue a fine?", "Overdue Book", JOptionPane.YES_NO_OPTION);
            
            if(confirm == JOptionPane.YES_OPTION) {
                this.view.dispose();
                description = "Failure to return the following books on time: ";
                for(Book book : books) {
                    description = description + " " + book.getTitle();
                    if(!books.getLast().equals(book)) {
                        description = description + ",";
                    }
                }
                
                FineFormCon con = new FineFormCon(staff, updateTable);
                con.setInitialLoanDetails(member, totalFine, description, today, loan);
                
            }
        }
        
    }
    
    private void updateField() {
        view.loanIDField.setText(String.format("%06d", loan.getLoanID()));
        view.memberNameField.setText(member.getName());
        view.memberIDField.setText(String.format("%06d", member.getMemberID()));
        view.issueDateField.setText(loan.getIssueDate().toString());
        view.dueDateField.setText(loan.getDueDate().toString());
        view.returnDateField.setText(loan.getReturnDate() != null ? loan.getReturnDate().toString() : "Not yet returned");
        view.statusField.setText(loan.getStatus().toString());
        
        //"#", "Title", "Category", "Author", "Publisher", "Publication Year"
        view.model.setRowCount(0);
        for(Book book : books) {
            
            view.model.addRow(new Object[] {
                String.format("%06d", book.getBookID()), 
                book.getTitle(), 
                book.getCategory(),
                book.getAuthors().size() >= 2 ? book.getAuthors().get(0).getName() + ", et al." : book.getAuthors().get(0).getName(),
                book.getPublisher().getName()
            });
        }
    }
    
}
