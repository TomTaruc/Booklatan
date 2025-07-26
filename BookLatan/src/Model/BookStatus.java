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
        // Capitalize first letter of each word
        String[] words = name.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) result.append(" ");
            if (words[i].length() > 0) {
                result.append(Character.toUpperCase(words[i].charAt(0)));
                if (words[i].length() > 1) {
                    result.append(words[i].substring(1));
                }
            }
        }
        return result.toString();
    }
    
    public static BookStatus fromString(String value) {
        // First try to match against enum names (e.g., "NOT_AVAILABLE")
        for(BookStatus status : BookStatus.values()) {
            if(status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        
        // Then try to match against readable strings (e.g., "Not Available")
        for(BookStatus status : BookStatus.values()) {
            if(status.toReadableString().equalsIgnoreCase(value)) {
                return status;
            }
        }
        
        return null;
    }
    
}
