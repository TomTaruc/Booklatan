/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import Model.AuthDAO;
import Model.User;
import Controller.AdminAppCon;
import Controller.LibAppCon;
import Controller.MemAppCon;

import View.AdminApplication;
import View.LibrarianApplication;
import View.MemberApplication;


/**
 *
 * @author Dinel
 */

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel statusLabel;
    private JPanel mainPanel;

    public LoginFrame() {
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setupFrame();
    }

    private void initializeComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        statusLabel = new JLabel(" ", SwingConstants.CENTER);
        mainPanel = new JPanel();
        styleComponents();
    }

    private void styleComponents() {
        Font fieldFont = new Font("Tahoma", Font.PLAIN, 14);
        Font buttonFont = new Font("Tahoma", Font.BOLD, 16);

        usernameField.setFont(fieldFont);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        passwordField.setFont(fieldFont);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        loginButton.setFont(buttonFont);
        loginButton.setBackground(new Color(54, 84, 150));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        statusLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        statusLabel.setForeground(Color.RED);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("BOOKLATAN", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 28));
        titleLabel.setForeground(new Color(54, 84, 150));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginButton);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(statusLabel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(44, 74, 140));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(54, 84, 150));
            }
        });

        usernameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                clearStatus();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                clearStatus();
            }
        });

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                showError("Please enter username and password.");
                return;
            }

                User user = AuthDAO.authenticateUser(username, password);

                if (user != null) {
                    showSuccess("Login successful! Welcome, " + user.getUsername());
                    dispose();
                    switch (user.getType()) {
                        case ADMIN -> new AdminAppCon(new AdminApplication(user)).openApp();
                        case LIBRARIAN -> new LibAppCon(new LibrarianApplication(user)).openApp();
                        case MEMBER -> new MemAppCon(new MemberApplication(user)).openApp();
                        default -> showError("Unknown user type");
                    }
            } else {
                showError("Invalid username or password.");
            }
        });
    }

    private void setupFrame() {
        setTitle("Booklatan - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void showError(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        statusLabel.setText(message);
        statusLabel.setForeground(new Color(0, 150, 0));
    }

    private void clearStatus() {
        statusLabel.setText(" ");
    }
}