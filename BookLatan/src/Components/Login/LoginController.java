/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Login;

import Applications.AdminAppCon;
import Applications.LibAppCon;
import Applications.MemAppCon;
import Model.AuthDAO;
import Model.User;
import static Model.User.UserType.ADMIN;
import static Model.User.UserType.LIBRARIAN;
import static Model.User.UserType.MEMBER;
import Applications.AdminApplication;
import Applications.LibrarianApplication;
import Applications.MemberApplication;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class handles all user interactions on LoginView. <br>
 * This doesn't include Animations and UI effects.
 * 
 * @author Dinel
 * @author Joseph
 */
public class LoginController {
    private LoginView view;
    
    public LoginController() {
        view = new LoginView();
        
        // Login the user
        view.getLoginButton().addActionListener(e -> loginEvent());
        view.getUsernameField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginEvent();
                }
            } 
        });
        
        view.getPasswordField().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginEvent();
                }
            }
            
        });
        
    }
    
    //Delete this
//    public void bypassLib() {
//        view.getUsernameField().setText("KaiserLycan");
//        view.getPasswordField().setText("KaiserLycan@081505");
//        loginEvent();
//    }
    
    private void loginEvent() {
        //Declaration of Variables
        User user;
        String username;
        String password;
        
        view.getUsernameField().setEditable(false);
        view.getPasswordField().setEditable(false);
        view.getLoginButton().setEnabled(false);

        //Get user input
        username = view.getUsernameField().getText().trim();
        password = new String(view.getPasswordField().getPassword());

        //Check if either fields of user input is empty.
        if (username.isEmpty() || password.isEmpty()) {
            view.showError("Please enter username and password.");
            return; // If empty then don't login.
        }

        //Get the user info.
        user = AuthDAO.authenticateUser(username, password);

        //If user exists, then open frame by user type. If not then show error.
        if (user != null) {
            view.showSuccess("Login successful! Welcome, " + user.getUsername());
            view.dispose();
            switch (user.getType()) {
                case ADMIN -> new AdminAppCon(new AdminApplication(), user).openApp();
                case LIBRARIAN -> new LibAppCon(new LibrarianApplication(), user).openApp();
                case MEMBER -> new MemAppCon(new MemberApplication(), user).openApp();
            }
        } else {
            view.getUsernameField().setEditable(true);
            view.getPasswordField().setEditable(true);
            view.getLoginButton().setEnabled(true);
            view.showError("Invalid username or password.");
        }
    }
    
}
