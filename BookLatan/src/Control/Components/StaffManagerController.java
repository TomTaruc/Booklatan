/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Components;

import Model.Member;
import Model.Staff;
import Model.User;
import Model.UserMemberDAO;
import Model.UserStaffDAO;
import View.Components.MembersManager;
import View.Components.RegistrationFrame;
import View.Components.StaffManager;
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

/**
 *
 * @author Joseph Rey
 */
public class StaffManagerController {
    private StaffManager view;
    private  UserStaffDAO model;
    private Staff selectedUser;
    
    public StaffManagerController(StaffManager view) 
    {        
        this.view = view;
        this.model = new UserStaffDAO();
        
        updateTable(); //automatically refresh instantiation.
        view.registerBtn.addActionListener(new RegisterMemberEvent());
        view.table.addMouseListener(new SelectMemberEvent());
        view.updateBtn.addActionListener(new UpdateMemberEvent());
        view.deleteBtn.addActionListener(new DeleteMemberEvent());
        view.searchBar.getDocument().addDocumentListener(new SearchMemberEvent());
        view.dropdownRole.addItemListener(new FilterRoleEvent());
    }
    
    private void filterSearch() {
        if(view.searchBar.getText().equals("Search staff")) {
            updateTable(model.getStaff(User.UserType.fromString(view.dropdownRole.getSelectedItem().toString())));   
        }
        else {
            updateTable(model.getStaff(view.searchBar.getText(), User.UserType.fromString(view.dropdownRole.getSelectedItem().toString())));
        }
    }
    
    private  void updateTable() {
        DefaultTableModel table = view.tableModel;
        ArrayList<Staff> staff = model.getStaff();
        
//Clear the table
        table.setRowCount(0);
        
        for(Staff individual : staff) {
            //"#", "Name", "Role", "Phone", "Email"
            table.addRow(new Object[] {
                String.format("%06d", individual.getStaffID()),
                individual.getName(),
                individual.getType(),
                individual.getPhone(),
                individual.getEmail()
            });
        }
    }
    
    private  void updateTable(ArrayList<Staff> staff) {
        DefaultTableModel table = view.tableModel;
        
        //Clear the table
        table.setRowCount(0);
        
        for(Staff individual : staff) {
            //"#", "Name", "Role", "Phone", "Email"
            table.addRow(new Object[] {
                String.format("%06d", individual.getStaffID()),
                individual.getName(),
                individual.getType(),
                individual.getPhone(),
                individual.getEmail()
            });
        }
    }
    
    
    // ***** Event Handles ******
    /// *** Register *** 
    private class RegisterMemberEvent implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            RegisterStaffCon register = new RegisterStaffCon(new RegistrationFrame(true), () -> updateTable());
        }
        
    }
    
    /// *** Update ***
    private class UpdateMemberEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedUser == null) {
                    throw new Exception("Please select a staff to update.");
                }
                
                selectedUser.setName(fields.get(0).getText());
                selectedUser.setUsername(fields.get(1).getText(), true);
                selectedUser.setType(User.UserType.fromString(fields.get(2).getText()));
                selectedUser.setEmail(fields.get(3).getText());
                selectedUser.setPhone(fields.get(4).getText());
                selectedUser.setAddress(fields.get(5).getText());
                selectedUser.setDateHired(LocalDate.parse(fields.get(6).getText()));
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            model.updateStaff(selectedUser);
            updateTable();
            JOptionPane.showMessageDialog(null, "Staff has been updated.");
        } 
    }
    
    /// *** Delete ***
    private class DeleteMemberEvent implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<JTextField> fields = view.fields;
            
            try {
                if(selectedUser == null) {
                    throw new Exception("Please select a staff to delete.");
                }
                
                for(JTextField field : fields) {
                    field.setText("");
                }
                model.deleteStaff(selectedUser);
                updateTable();
                selectedUser = null;
                JOptionPane.showMessageDialog(null, "Staff has been deleted.");
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
                JTable table = view.table;
                DefaultTableModel tableModel = view.tableModel;
                ArrayList<JTextField> fields = view.fields;
                int selectedRowIndex = table.getSelectedRow();
                
                int memberID;
                
                try {
                    memberID = Integer.parseInt(table.getValueAt(selectedRowIndex, 0).toString());
                }
                catch(Exception ex) {
                    return;
                }
                
                selectedUser = model.getStaffByID(memberID);
                fields.get(0).setText(selectedUser.getName());
                fields.get(1).setText(selectedUser.getUsername());
                fields.get(2).setText(selectedUser.getType().toString().toLowerCase());
                fields.get(3).setText(selectedUser.getEmail());
                fields.get(4).setText(selectedUser.getPhone());
                fields.get(5).setText(selectedUser.getAddress());
                fields.get(6).setText(selectedUser.getDateHired().toString()); 
                fields.get(7).setText(selectedUser.getPassword().toString());
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
    
    private class FilterRoleEvent implements ItemListener{

        @Override
        public void itemStateChanged(ItemEvent e) {
            filterSearch();
        }
    }
}
