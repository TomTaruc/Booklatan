/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Dinel
 */
public enum BookStatus {
    LOANED,
    AVAILABLE,
    NOT_AVAILABLE,
    RESERVED;
    
    public String toReadableString() {
        String name = this.name().toLowerCase().replace('_', ' ');
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
    
    public static BookStatus fromString(String value) {
        for(BookStatus status : BookStatus.values()) {
            if(status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
    
}
