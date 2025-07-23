/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Forms;

import Model.Fine;
import Model.FineDAO;
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
    private Runnable udpateTable;
    
    public FineFormCon(Staff staff, Runnable updateTable) {
        this.view = new FineForm();
        this.memberDAO = new UserMemberDAO();
        this.fineDAO = new FineDAO();
        this.udpateTable = updateTable;
        view.setVisible(true);
        
        view.memberName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
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
        });
        
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
                    updateTable.run();
                    int confirm = JOptionPane.showConfirmDialog(view, "A fine under " + view.memberName.getText() + " has been issued. Would you like to add issue fine?", "Success", JOptionPane.YES_NO_OPTION);
                    if(confirm == JOptionPane.NO_OPTION)
                        view.dispose();
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
