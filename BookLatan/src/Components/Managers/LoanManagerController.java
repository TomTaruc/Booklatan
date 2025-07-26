/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Components.Forms.LoanController;
import Components.Forms.LoanForm;
import Components.Forms.LoanInformationCon;
import Components.Forms.LoanInformationForm;
import Model.Loan;
import Model.LoanDAO;
import Model.LoanStatus;
import Model.Member;
import Model.Staff;
import Model.User;
import Model.User.UserType;
import Model.UserMemberDAO;
import Model.UserStaffDAO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Joseph Rey
 */
public class LoanManagerController {
    private LoanManager view;
    private LoanDAO loanDAO;
    private UserMemberDAO memDAO;
    private UserStaffDAO staffDAO;
    private User user;
    private Staff staff;
    private Member member;
    
    public LoanManagerController(LoanManager view, User user) {
        this.view = view;
        this.loanDAO = new LoanDAO();
        this.memDAO = new UserMemberDAO ();
        this.staffDAO = new UserStaffDAO();
        this.user = user;
        
        
        if(user.getType().equals(UserType.LIBRARIAN)) {
            staff = staffDAO.getStaffByUserID(user.getUserId());
        }
        else {
            member = memDAO.getMemberByID(memDAO.getMemberIDByUSerID(user.getUserId()));
        }
        
        
        
        this.view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = view.table.getSelectedRow();
                Loan loan = loanDAO.getLoan(Integer.parseInt(view.table.getValueAt(rowIndex, 0).toString()));
                
                if(user.getType() == UserType.LIBRARIAN) 
                    new LoanInformationCon(staff, loan, () -> filterTable());
                else 
                    new LoanInformationCon(loan);
            } 
        });
        
        this.view.createLoanBtn.addActionListener(e -> {
            LoanController creator = new LoanController(this.user, () -> filterTable());
        });
        
        
        
        this.view.searchbar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
        
        this.view.statusSelection.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                filterTable();
            }
        });
        
        filterTable();
    }
    
    public void updateTable(ArrayList<Loan> loans) {
        view.model.setRowCount(0);
        Member member;
        for(Loan loan : loans) {
            member =  memDAO.getMemberByID(loan.getMemberID());
            //"#", "Member","Issue Date", "Due Date", "Return Date", "Status"
            view.model.addRow(new Object[] {
                String.format("%06d", loan.getLoanID()),
                member.getName(),
                loan.getIssueDate().toString(),
                loan.getDueDate().toString(),
                loan.getReturnDate() != null? loan.getReturnDate().toString() : "Not Yet Returned",
                loan.getStatus().toString()
            });
        }
    }
    
    public void filterTable() {
        if (view.searchbar.getText().trim().equalsIgnoreCase(view.SEARCH_PLACEHORDER)) {
            if(user.getType() == UserType.LIBRARIAN)
                updateTable(loanDAO.getLoans( (LoanStatus) view.statusSelection.getSelectedItem()));
            else
                updateTable(loanDAO.getLoans((LoanStatus) view.statusSelection.getSelectedItem(), member.getMemberID()));
        }
        else {
            if(user.getType() == UserType.LIBRARIAN) {
                updateTable(loanDAO.getLoans(view.searchbar.getText().trim(), (LoanStatus) view.statusSelection.getSelectedItem()));
            }
            else {
                updateTable(loanDAO.getLoans(view.searchbar.getText().trim(), (LoanStatus) view.statusSelection.getSelectedItem(), member.getMemberID()));
            }
        }
    }
}
