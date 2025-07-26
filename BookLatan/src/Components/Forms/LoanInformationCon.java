/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Book;
import Model.Loan;
import Model.LoanDAO;
import Model.Member;
import Model.UserMemberDAO;
import java.awt.Color;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

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
    private LoanDAO loanDOA;
    
    
    public LoanInformationCon(Loan loan) {
        this.view = new LoanInformationForm();
        this.memDAO = new UserMemberDAO();
        this.loanDOA = new LoanDAO();
        this.loan = loan;
        this.member = memDAO.getMemberByID(loan.getMemberID());
        this.books = this.loanDOA.getLoanedBooks(this.loan.getLoanID());
        
        updateField();
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
