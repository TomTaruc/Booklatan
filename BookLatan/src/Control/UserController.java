/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Model.User;
import Model.UserDAO;
/**
 *
 * @author Joseph Rey
 */
public class UserController {
    private UserDAO model;

    public UserController(UserDAO model) {
        this.model = model;
    }
    
    
    
    public void createUser(User user) {
        model.createUser(user);
    }
    
    public void updateUser(User user) {
        model.updateUser(user);
    }
    
    public void deleteUser(User user) {
        model.deleteUser(user);
    }
}
