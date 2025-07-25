 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componenents.Members;

import Model.Fine;
import Model.FineDAO;
import Model.FineStatus;
import Model.Member;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 *
 * @author Joseph Rey
 */
public class FineViewCon {
    private FineView view;
    private FineDAO fineDAO;
    private Member member;
    
    public FineViewCon(FineView view, Member member) {
        this.view = view;
        this.member = member;
        this.fineDAO = new FineDAO();
        
        this.view.totalUnpaidField.setValue(fineDAO.totalUnpaidFine(member));
        
        this.view.cmbStatusFilter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateTable();
            }
            
        });
        
        updateTable();
    }
    
    private void updateTable() {
        view.finesTableModel.setRowCount(0);
        ArrayList<Fine> fines = fineDAO.getFines(member, (FineStatus) view.cmbStatusFilter.getSelectedItem());
        for(Fine fine : fines) {
            view.finesTableModel.addRow(new Object[] {
                String.format("%06d", fine.getFineID()),
                String.format("%.2f", fine.getAmount()),
                fine.getStatus().toString(),
                fine.getDescription(),
                fine.getDateIssued().toString(),
                fine.getDue_date().toString(),
                fine.getPaid_date() != null ? fine.getPaid_date().toString() : "Not Paid"
            });
        }
    }
}
