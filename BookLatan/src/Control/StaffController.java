/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Model.Staff;
import Model.StaffDAO;
/**
 *
 * @author Joseph Rey
 */
public class StaffController {
    
    private StaffDAO model;

    public StaffController(StaffDAO model) {
        this.model = model;
    }
    
    public void registerStaff(Staff staff) {
        model.registerStaff(staff);
    };
    
    public void deleteStaff(Staff staff) {
        model.deleteStaff(staff);
    }
    
    public void updateStaff(Staff staff) {
        model.updateStaff(staff);
    }
}
