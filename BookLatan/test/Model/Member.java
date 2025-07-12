/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.time.LocalDate;
/**
 *
 * @author Joseph Rey
 */
public class Member extends User{
    
    public enum MembershipStatus {
        ALL,
        ACTIVE,
        SUSPENDED,
        DEACTIVATED;
        
        public static MembershipStatus fromString(String value) {
            for(MembershipStatus type : MembershipStatus.values()) {
                if(type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }
    }

    
    private int memberID;
    private String name;
    private String phone;
    private String email;
    private String address;
    private LocalDate dateJoined;
    private MembershipStatus status;

    public int getMemberID() {
        return memberID;
    }

    public void setMemberID(int memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public MembershipStatus getStatus() {
        return status;
    }

    public void setStatus(MembershipStatus status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
