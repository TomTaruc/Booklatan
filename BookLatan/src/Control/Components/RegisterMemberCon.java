/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Components;

import Model.Member;
import Model.UserMemberDAO;
import View.Components.RegistrationFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Joseph Rey
 */
public class RegisterMemberCon {
    private  RegistrationFrame view;
    private  UserMemberDAO memberModel;
    private Runnable onRisgterSuccess;
    
    public RegisterMemberCon(RegistrationFrame view, Runnable onRisgterSuccess) {
        this.view = view;
        this.memberModel = new UserMemberDAO();
        this.onRisgterSuccess = onRisgterSuccess;
        
        attachListeners();
        this.view.setVisible(true);
    }
    
    public void attachListeners() {
        view.cancelBtn.addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel registration?", "Cancel Registration", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                view.dispose();
            }
        });
        
        view.registerBtn.addActionListener(e -> {
            // "Name", "Username", "Phone", "Email", "Address", "Password"
            Member member;
            int answer = JOptionPane.YES_OPTION;
            String name = view.fields.get(0).getText();
            String username = view.fields.get(1).getText();
            String phone = view.fields.get(2).getText();
            String email = view.fields.get(3).getText();
            String address = view.fields.get(4).getText();
            String password = view.fields.get(5).getText();
            
            try {
                member = new Member();
                member.setName(name);
                member.setUsername(username, true);
                member.setPhone(phone);
                member.setEmail(email);
                member.setAddress(address);
                member.setPassword(password, true);
                memberModel.addMember(member);
                
                this.onRisgterSuccess.run();
                
                answer = JOptionPane.showConfirmDialog(null, "Member added. Do you want to add more members?", "Successfully Registered", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.NO_OPTION) {
                    view.dispose();
                }
                
                for(JTextField field : view.fields) {
                    field.setText("");
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
    }
    
}
