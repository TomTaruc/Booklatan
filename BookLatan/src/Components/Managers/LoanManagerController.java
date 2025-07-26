/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Components.Forms.LoanInformationCon;
import Components.Forms.LoanInformationForm;
import Model.Loan;
import Model.LoanDAO;
import Model.LoanStatus;
import Model.Member;
import Model.UserMemberDAO;
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
    private UserMemberDAO userDAO;
    
    public LoanManagerController(LoanManager view) {
        this.view = view;
        this.loanDAO = new LoanDAO();
        this.userDAO = new UserMemberDAO ();
        
        this.view.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = view.table.getSelectedRow();
                Loan loan = loanDAO.getLoan(Integer.parseInt(view.table.getValueAt(rowIndex, 0).toString()));
                new LoanInformationCon(loan);
                
            }
            
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
            member =  userDAO.getMemberByID(loan.getMemberID());
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
            updateTable(loanDAO.getLoans( (LoanStatus) view.statusSelection.getSelectedItem()));
        }
        else {
            updateTable(loanDAO.getLoans(view.searchbar.getText().trim(), (LoanStatus) view.statusSelection.getSelectedItem()));
        }
    }
}
