/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Model.Member;
import Model.Staff;
import Model.User;
import Model.UserMemberDAO;
import Model.UserStaffDAO;
import Components.Forms.RegistrationForm;
import java.time.LocalDate;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Joseph Rey
 */
public class RegisterStaffCon {
    private  RegistrationForm view;
    private  UserStaffDAO staffModel;
    private Runnable onRisgterSuccess;
    
    public RegisterStaffCon(RegistrationForm view, Runnable onRisgterSuccess) {
        this.view = view;
        this.staffModel = new UserStaffDAO();
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
            Staff individual;
            int answer = JOptionPane.YES_OPTION;
            String name = view.fields.get(0).getText();
            String username = view.fields.get(1).getText();
            String phone = view.fields.get(2).getText();
            String email = view.fields.get(3).getText();
            String address = view.fields.get(4).getText();
            String password = view.fields.get(5).getText();
            String role = view.fields.get(6).getText();
            String dateHired = view.fields.get(7).getText();
            
            try {
                individual = new Staff();
                individual.setName(name);
                individual.setUsername(username, true);
                individual.setPhone(phone);
                individual.setEmail(email);
                individual.setAddress(address);
                individual.setPassword(password, true);
                individual.setType(User.UserType.fromString(role));
                individual.setDateHired(LocalDate.parse(dateHired));
                staffModel.addStaff(individual);
                
                this.onRisgterSuccess.run();
                
                answer = JOptionPane.showConfirmDialog(null, "Staff added. Do you want to add more staff?", "Successfully Registered", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.NO_OPTION) {
                    view.dispose();
                }
                
                for(JTextField field : view.fields) {
                    field.setText("");
                }
            }
            catch(Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
        
    }
    
}
