package Components.Managers;


import Components.Forms.RegisterMemberCon;
import Model.Member;
import Model.UserMemberDAO;
import Components.Managers.MembersManager;
import Components.Forms.RegisterMemberCon;
import Components.Forms.RegistrationForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Joseph Rey
 */
public class MemberManagerController {
    private MembersManager view;
    private  UserMemberDAO memberModel;
    private Member selectedMember;
    private boolean isAdmin = false;
    
    public MemberManagerController(MembersManager view, boolean isAdmin) 
    {        
        this.view = view;
        this.memberModel = new UserMemberDAO();
        this.isAdmin = isAdmin;
        
        updateMembersTable(); //automatically refresh instantiation.
        view.registerBtn.addActionListener(new RegisterMemberEvent());
        view.membersTable.addMouseListener(new SelectMemberEvent());
        view.updateBtn.addActionListener(new UpdateMemberEvent());
        view.deleteBtn.addActionListener(new DeleteMemberEvent());
        view.searchBar.getDocument().addDocumentListener(new SearchMemberEvent());
        view.filterStatus.addItemListener(new FilterMemberStatusEvent());
    }
    
    private void filterSearch() {
        if(view.searchBar.getText().equals("Search member")){
            updateMembersTable(memberModel.getMembers((Member.MembershipStatus) view.filterStatus.getSelectedItem()));   
        }
        else {
            updateMembersTable(memberModel.getMembers(view.searchBar.getText(), (Member.MembershipStatus) view.filterStatus.getSelectedItem()));
        }
    }
    
    private  void updateMembersTable() {
        DefaultTableModel table = view.membersTableModel;
        ArrayList<Member> members = memberModel.getMembers();
        
        //Clear the table
        table.setRowCount(0);
        
        for(Member member : members) {
            //"#", "Name", "Status", "Phone", "Email"
            table.addRow(new Object[] {
                String.format("%06d", member.getMemberID()),
                member.getName(),
                member.getStatus(),
                member.getPhone(),
                member.getEmail()
            });
        }
    }
    
    private  void updateMembersTable(ArrayList<Member> members) {
        DefaultTableModel table = view.membersTableModel;
        
        //Clear the table
        table.setRowCount(0);
        
        for(Member member : members) {
            //"#", "Name", "Status", "Phone", "Email"
            table.addRow(new Object[] {
                String.format("%06d", member.getMemberID()),
                member.getName(),
                member.getStatus(),
                member.getPhone(),
                member.getEmail()
            });
        }
    }
    
    
    // ***** Event Handles ******
    /// *** Register *** 
    private class RegisterMemberEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterMemberCon register = new RegisterMemberCon(new RegistrationForm(false), () -> updateMembersTable());
        }
        
    }
    
    /// *** Update ***
    private class UpdateMemberEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedMember == null) {
                    throw new Exception("Please select a member to update.");
                }
                
                selectedMember.setName(fields.get(0).getText());
                selectedMember.setUsername(fields.get(1).getText(), true);
                selectedMember.setStatus(Member.MembershipStatus.valueOf(fields.get(2).getText().strip().toUpperCase()));
                selectedMember.setEmail(fields.get(3).getText());
                selectedMember.setPhone(fields.get(4).getText());
                selectedMember.setAddress(fields.get(5).getText());
                if(isAdmin) {
                    selectedMember.setPassword(fields.get(6).getText(), false);
                    selectedMember.setDateJoined(LocalDate.parse(fields.get(7).getText()));
                }
                else {
                    selectedMember.setDateJoined(LocalDate.parse(fields.get(6).getText()));
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            memberModel.updateMember(selectedMember);
            updateMembersTable();
            JOptionPane.showMessageDialog(null, "Member has been updated.");
        } 
    }
    
    /// *** Delete ***
    private class DeleteMemberEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedMember == null) {
                    throw new Exception("Please select a member to delete.");
                }
                
                for(JTextField field : fields) {
                    field.setText("");
                }
                memberModel.deleteMember(selectedMember);
                updateMembersTable();
                selectedMember = null;
                JOptionPane.showMessageDialog(null, "Member has been deleted.");
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
    }
    
    // *** Select *** 
    private class SelectMemberEvent extends MouseAdapter {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable table = view.membersTable;
                DefaultTableModel tableModel = view.membersTableModel;
                ArrayList<JTextField> fields = view.fields;
                int selectedRowIndex = table.getSelectedRow();
                
                int memberID;
                
                try {
                    memberID = Integer.parseInt(table.getValueAt(selectedRowIndex, 0).toString());
                }
                catch(Exception ex) {
                    return;
                }
                
                selectedMember = memberModel.getMemberByID(memberID);
                fields.get(0).setText(selectedMember.getName());
                fields.get(1).setText(selectedMember.getUsername());
                fields.get(2).setText(selectedMember.getStatus().toString().toLowerCase());
                fields.get(3).setText(selectedMember.getEmail());
                fields.get(4).setText(selectedMember.getPhone());
                fields.get(5).setText(selectedMember.getAddress()); 
                
                if(isAdmin) {
                    fields.get(6).setText(selectedMember.getPassword());
                    fields.get(7).setText(selectedMember.getDateJoined().toString());
                }
                else {
                    fields.get(6).setText(selectedMember.getDateJoined().toString());
                }
            }   
        }
    
    // *** Search ***
    private class SearchMemberEvent implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            filterSearch();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filterSearch();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filterSearch();
        }   
    }
    
    private class FilterMemberStatusEvent implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            filterSearch();
        }
    }
}
