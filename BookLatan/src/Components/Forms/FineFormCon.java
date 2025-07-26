/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Fine;
import Model.FineDAO;
import Model.Loan;
import Model.LoanDAO;
import Model.LoanStatus;
import Model.Member;
import Model.Staff;
import Model.UserMemberDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.util.Date;
import java.time.ZoneId;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class FineFormCon {
    private FineForm view;
    private FineDAO fineDAO;
    private UserMemberDAO memberDAO;
    private Runnable updateTable;
    private Staff staff;
    private LoanDAO loanDAO;
    private Loan loan = null;
    
    public FineFormCon(Staff staff, Runnable updateTable) {
        this.view = new FineForm();
        this.staff = staff;
        this.memberDAO = new UserMemberDAO();
        this.fineDAO = new FineDAO();
        this.loanDAO = new LoanDAO();
        this.updateTable = updateTable;
        view.setVisible(true);
        attachListeners();
    }
    
    public void setInitialLoanDetails(Member member, double amount, String description, LocalDate dateIssued, Loan loan) {
        view.memberName.setText(member.getName());
        view.memberNo.setText(String.format("%06d", member.getMemberID()));
        view.amount.setValue(amount);
        view.description.setText(description);
        this.loan = loan;
    }
    
    public void attachListeners() {
        
        view.memberName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_TAB) {
                    Member mem = memberDAO.getMemberByName(view.memberName.getText().trim());
                    if(mem  == null) {
                        view.showErrorMessage("User Does Not Exist");
                        view.memberName.setText("");
                        view.memberNo.setText("");
                    }
                    else {
                        view.memberName.setText(mem.getName());
                        view.memberNo.setText(String.format("%06d", mem.getMemberID()));
                    }
                }
            }
        });
        
        view.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Fine fine = new Fine();
                    fine.setMemberID(Integer.parseInt(view.memberNo.getText()));
                    fine.setStaffID(staff.getStaffID());
                    fine.setAmount(((Number)view.amount.getValue()).doubleValue());
                    fine.setDateIssued(LocalDate.now());
                    fine.setDue_date(((Date)view.dueDate.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    fine.setDescription(view.description.getText());
                    fineDAO.addFine(fine);
                    
                    if(loan != null) {
                        loan.setStatus(LoanStatus.FINED);
                        loanDAO.updateLoanStatus(loan);
                        updateTable.run();
                        JOptionPane.showMessageDialog(null, "A fine under " + view.memberName.getText() + " has been issued.");
                        view.dispose();
                    }
                    else {
                        updateTable.run();
                        int confirm = JOptionPane.showConfirmDialog(view, "A fine under " + view.memberName.getText() + " has been issued. Would you like to add issue fine?", "Success", JOptionPane.YES_NO_OPTION);
                        if(confirm == JOptionPane.NO_OPTION)
                            view.dispose();
                    }
                } catch (Exception ex) {
                    view.showErrorMessage(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });
        
        view.cancel.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure?", "Cancel", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                view.dispose();
            }
        });
    
    }
}
