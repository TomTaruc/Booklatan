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
public class Member extends User{
    private int memberID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateJoined;
    private MembershipStatus status;
    
    public Member() {};
    
    public Member(String username, String password) throws IllegalArgumentException {
        super(username, password);
        setType(User.UserType.MEMBER);
    }

   // **** Setters ****
    public void setMemberID(int memberID) {
        Validators.validateEmptyVariable(memberID, "MemberID");
        this.memberID = memberID;
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

    public void setAddress(String address) {
        Validators.validateEmptyVariable(address, "Address");
        this.address = address;
    }

    public void setDateJoined(LocalDate dateJoined) {
        Validators.validateEmptyVariable(dateJoined, "Date Joined");
        this.dateJoined = dateJoined;
    }

    public void setStatus(MembershipStatus status) {
        Validators.validateEmptyVariable(status, "Status");
        this.status = status;
    }
    

    // **** Getters ****
    public LocalDate getDateJoined() { return dateJoined; }
    public int getMemberID() { return memberID; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; } 
    public MembershipStatus getStatus() { return status; }
    
    // **** override ****

    @Override
    public String toString() {
        return memberID + "\t" + name +  "\t" + super.toString() + "\t" + phone + "\t" + email + "\t" + status + "\t" + dateJoined + "\t" + address;
    }
    
    
    // **** Enumerators ****
    public enum MembershipStatus {
        ALL("All"), // For filtering
        ACTIVE("Active"),
        DEACTIVATED("Deactivated"),
        SUSPENDED("Suspended");
        
        private final String label;
        
        MembershipStatus(String label) {
            this.label = label;
        }
        
        @Override
        public String toString() {
            return label;
        }
        
        public static MembershipStatus fromString(String value) {
            for(MembershipStatus status : MembershipStatus.values()) {
                if(status.name().equalsIgnoreCase(value)) {
                    return status;
                }
            }
            return null;
        }
        
    };
}
