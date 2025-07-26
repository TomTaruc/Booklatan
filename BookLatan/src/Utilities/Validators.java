/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 *This class is used to validate user information. <br>
 * Such as email, phone, empty input, password, user name <br>
 * and more.
 * @author Joseph Rey
 */
public class Validators {
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]*+@+[A-Za-z0-9+_.-]+.+[A-Za-z]$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+63|0)?9\\d{9}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{6,50}$");
    
    public static void validateEmail(String email) {
        validateEmptyVariable(email, "Email");
        
        if(!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    };
    
    public static void validatePassword(String password) {
        validateEmptyVariable(password, "Password");
        
        if(!PASSWORD_PATTERN.matcher(password).matches()) {
            throw new IllegalArgumentException("Password must be 6–50 characters and include at least one uppercase letter, one lowercase letter, one number, and one special character (@#$%^&+=!).");
        }
    };
    
    public static void validatePhone(String phone) {
        validateEmptyVariable(phone, "Phone");
        if(!PHONE_PATTERN.matcher(phone).matches()) {
            throw new IllegalArgumentException("Invalid Phone number");
        }
    };
    
    public static void validateUsername(String username) {
        validateEmptyVariable(username, "Username");
        
        if(!USERNAME_PATTERN.matcher(username).matches()) {
          throw new IllegalArgumentException("Username must be 3–20 characters (letters, numbers, underscores only).");
        }
    };
    
    public static void validateEmptyVariable(String variable, String variableName) {
        if(variable == null || variable.toString().trim().isEmpty()) {
            throw new IllegalArgumentException(variableName + " cannot be empty");
        }
    };
    
    public static void validateEmptyVariable(LocalDate variable, String variableName) {
        if(variable == null) {
            throw new IllegalArgumentException(variableName + " cannot be empty");
        }
    };
    
    public static void validateEmptyVariable(Integer variable, String variableName) {
        if(variable == null) {
            throw new IllegalArgumentException(variableName + " cannot be empty");
        }
    };
    
    public static void validateEmptyVariable(Double variable, String variableName) {
        if(variable == null) {
            throw new IllegalArgumentException(variableName + " cannot be empty");
        }
    };
    
    public static void validateEmptyVariable(Enum variable, String variableName) {
        if(variable == null) {
            throw new IllegalArgumentException(variableName + " cannot be empty");
        }
    };
    
    public static void validateLimitValue(Double variable, Double min, Double max) {
        if(variable > max || variable < min) {
            throw new IllegalArgumentException(variable + " should be set between " + min + "-" + max + ".");
        }
    }
    
    public static void validateLimitValue(Integer variable, Integer min, Integer max) {
        if(variable > max || variable < min) {
            throw new IllegalArgumentException(variable + " should be set between " + min + "-" + max + ".");
        }
    }
    
}
