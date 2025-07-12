/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

/**
 *
 * @author Joseph Rey
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginForm() {
        setTitle("Login Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(2, 2));
        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton loginBtn = new JButton("Login");

        loginBtn.addActionListener(e -> {
            String user = usernameField.getText();
            String pass = new String(passwordField.getPassword());

            // Replace with DB validation later
            if (user.equals("admin") && pass.equals("1234")) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                // Proceed to next frame
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        });

        buttonPanel.add(loginBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    
        this.setVisible(true);
    }
    
}
