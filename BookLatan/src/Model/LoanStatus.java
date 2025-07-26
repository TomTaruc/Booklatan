/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Joseph Rey
 */
public enum LoanStatus {
    ALL("All"),
    OVERDUE ("Overdue"),
    RETURNED("Returned"),
    PENDING("Pending");
    
    private String label;
    
    LoanStatus(String label) {
        this.label = label;
    }
    
    public static LoanStatus fromString(String value) {
        for(LoanStatus status : LoanStatus.values()) {
            if(status.name().equalsIgnoreCase(value))
                return status;
        }
        return null;
    }

    @Override
    public String toString() {
        return label;
    }
}
