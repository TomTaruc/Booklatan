    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import Utilities.Validators;
import java.time.LocalDate;

/**
 *
 * @author Joseph Rey
 */
public class Staff extends User {
    
    private int staffID; 
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateHired;
    
    public Staff(String username, String password) throws IllegalArgumentException {
        super(username, password);
    }
    
    // **** Setters ****

    public void setStaffID(int staffID) {
        Validators.validateEmptyVariable(staffID, "StaffID");
        this.staffID = staffID;
    }

    public void setName(String name) {
        Validators.validateEmptyVariable(name, "Name");
        this.name = name;
    }

    public void setPhone(String phone) {
        Validators.validatePhone(phone);
        this.phone = phone;
    }

    public void setEmail(String email) {
        Validators.validateEmail(email);
        this.email = email;
    }

    public void setDateHired(LocalDate dateHired) {
        Validators.validateEmptyVariable(dateHired, "Date Hired");
        this.dateHired = dateHired;
    }

    public void setAddress(String address) { this.address = address; }
    
    // **** Getters ****
    public int getStaffID() { return staffID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public LocalDate getDateHired() { return dateHired; }
    
    // **** Overrides ****

    @Override
    public String toString() {
        return staffID + "\t" + name +  "\t" + super.toString() + "\t" + phone + "\t" + email + "\t" + dateHired + "\t" + address;
    }

    @Override
    public void setType(UserType type) {
        if(type == UserType.ALL || type == UserType.MEMBER) {
            throw new IllegalArgumentException("User Type for Staff cannot be ALL or MEMBER.");
        }
        super.setType(type); 
    }
    
}
