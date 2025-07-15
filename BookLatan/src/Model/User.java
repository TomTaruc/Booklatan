/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import Utilities.Validators;
import java.util.regex.Pattern;

/**
 *
 * @author Joseph Rey
 */
public class User {
    //Variables
    private String username;
    private String password;
    private UserType type;
    
    public User() {};
    
    public User(String username, String password) {
        setUsername(username, true);
        setPassword(password, true);
    }
    
    
    // **** Setters ****
    
    public void setUsername(String username, boolean applyValidation) {
        if (applyValidation)
            Validators.validateUsername(username);
        this.username = username;
    }
    
    public void setPassword(String password, boolean applyValidation) {
        if(applyValidation)
            Validators.validatePassword(password);
        this.password = password;
    }
    
        public void setType(UserType type) {
        Validators.validateEmptyVariable(username, "User Type");
        this.type = type;
    }


    
    // **** Getters ****
     public String getUsername() { return username; }
    public String getPassword() { return password; }
    public UserType getType() { return type; }
    
    // **** Overrides ****

    @Override
    public String toString() {
        return username + "\t" + password + "\t" + type;
    }
    
    // **** Enumerators **** 
    public static enum UserType {
        ALL("All"), //for filtering
        MEMBER("Member"),
        LIBRARIAN("Librarian"),
        ADMIN("Admin");
        
        private final String label;
        
        UserType(String label) {
            this.label = label;
        }
        
        public static UserType fromString(String value) {
            for(UserType type : UserType.values()) {
                if(type.name().equalsIgnoreCase(value)) {
                    return type;
                }
            }
            return null;
        }

        @Override
        public String toString() { return label; }
    }
}
