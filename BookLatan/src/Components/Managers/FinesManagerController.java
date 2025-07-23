package Components.Managers;

import Model.Fine;
import Model.FineDAO;
import Components.Managers.FinesManager;
import Components.Forms.FineFormCon;
import Components.Forms.FineInformationForm;
import Components.Forms.FineInformationFormCon;
import Model.FineStatus;
import Model.Member;
import Model.Staff;
import Model.UserMemberDAO;
import Model.UserStaffDAO;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class FinesManagerController {

    private FineDAO fineDAO;
    private UserMemberDAO memDAO;
    private UserStaffDAO staffDAO;
    private FinesManager view;
    private FineFormCon con;
    private FineInformationForm form;

    public FinesManagerController(FinesManager view, Staff staff) {
        this.fineDAO = new FineDAO();
        this.memDAO = new UserMemberDAO();
        this.staffDAO = new UserStaffDAO();
        this.view = view;
        
        view.issueBtn.addActionListener(e -> {
            con = new FineFormCon(staff, () -> filterTable());
        });
        
        view.cmbStatusFilter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                filterTable();
            }
        });
        
        view.txtSearch.getDocument().addDocumentListener(new DocumentListener() {
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
        
        view.finesTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = view.finesTable.getSelectedRow();
                Integer fineID = null;
                Fine fine;
                Staff staff;
                Member member;
                FineInformationFormCon fineInfo;
                
                try {
                    fineID = Integer.parseInt(view.finesTable.getValueAt(rowIndex, 0).toString());
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
                
                if(fineID != null) {
                    fine = fineDAO.getFine(fineID);
                    staff = staffDAO.getStaffByID(fine.getStaffID());
                    member = memDAO.getMemberByID(fine.getMemberID());
                    fineInfo = new FineInformationFormCon(fine, staff, member, () -> filterTable());
                    
                }
            }
            
        });
        
        filterTable();
    }
    
    private void updateTable(ArrayList<Fine> fines) {
        DefaultTableModel model = view.finesTableModel;
        
        model.setRowCount(0);
        for(Fine fine : fines) {
            Member member = memDAO.getMemberByID(fine.getMemberID());
            model.addRow(new Object[] {
                String.format("%06d", fine.getFineID()),
                member.getName(),
                String.format("%.2f", fine.getAmount()),
                fine.getStatus().toString(),
                fine.getDescription()
            });
        }
    }
    
    private void filterTable() {
        if(view.txtSearch.getText().toLowerCase().equals("search member")) {
            updateTable(fineDAO.getFines((FineStatus)view.cmbStatusFilter.getSelectedItem()));
        }
        else {
            updateTable(fineDAO.getFines(view.txtSearch.getText(), (FineStatus) view.cmbStatusFilter.getSelectedItem()));
        }
        
    }

    
}