/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Fine;
import Model.FineDAO;
import Model.FineStatus;
import Model.Member;
import Model.Staff;
import java.time.LocalDate;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class FineInformationFormCon {
    private FineInformationForm view;
    private FineDAO fineDAO;
    private Fine fine;
    private Staff staff;
    private Member member;
    
    public FineInformationFormCon (Fine fine, Staff staff, Member member, Runnable updateTable) {
        this.fine = fine;
        this.staff = staff;
        this.member = member; 
        
        view = new FineInformationForm();
        fineDAO = new FineDAO();
        view.setVisible(true);
        
        
        getDetails(this.fine, this.staff, this.member);
        
        view.payBtn.addActionListener(e -> {
            this.fine.setStatus(FineStatus.PAID);
            this.fine.setPaid_date(LocalDate.now());
            fineDAO.updateFineStatus(fine);
            getDetails(this.fine, this.staff, this.member);
            JOptionPane.showMessageDialog(view, "Fine status has been updated.");
            updateTable.run();
        });
        
        view.delBtn.addActionListener(e -> {
            fineDAO.deleteFine(fine);
            JOptionPane.showMessageDialog(view, "Fine " + String.format("%06d", fine.getFineID()) + " has been deleted");
            updateTable.run();
            view.dispose();
        });
        
    }
    
    private void getDetails(Fine fine, Staff staff, Member member) {
        view.fineIDField.setText(String.format("%06d", fine.getFineID()));
        view.staffNameField.setText(staff.getName());
        view.staffIDField.setText(String.format("%06d", staff.getStaffID()));
        view.memberNameField.setText(member.getName());
        view.memberIDField.setText(String.format("%06d", member.getMemberID()));
        view.amountField.setText(String.format("%.2f", fine.getAmount()));
        view.statusField.setText(fine.getStatus().toString());
        view.issuedDateField.setText(fine.getDateIssued().toString());
        view.dueDateField.setText(fine.getDue_date().toString());
        view.paidDateField.setText(fine.getPaid_date() != null ? fine.getPaid_date().toString() : "Not Paid Yet");
        view.descriptionField.setText(fine.getDescription());
    }
    
}
