package Components.Login;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import Utilities.Design;

/**
 * This class handles all the the things that the user <br>
 * see in the LoginView.
 * 
 * This class is used to for login.
 * @author Dinel Christian P. Robles - Creator
 * @author Joseph Rey R. Panti - Revisor
 */
public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel mainPanel;
    
    public LoginView() {
        initComponents();
        setupLayout();
        setupEffects();
        initFrame();
    }

    private void initComponents() {
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        mainPanel = new JPanel();
        styleComponents();
    }
    
    private void initFrame() {
        setTitle("Booklatan - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Design.FRAME_SIZE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        setIconImage(Design.appIcon);
    }

    private void styleComponents() {
        Font fieldFont = Design.PRIME_FONT.deriveFont(Font.PLAIN, 14);
        Font buttonFont = Design.PRIME_FONT.deriveFont(Font.BOLD, 16);

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
        loginButton.setPreferredSize(new Dimension(355, 50));
        loginButton.setBackground(Design.CLR1);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    
    private void setupLayout() {
        setLayout(new BorderLayout());

        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        JLabel img = new JLabel();
        img.setIcon(new ImageIcon(Design.appIcon.getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
        img.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("BOOKLATAN", SwingConstants.CENTER);
        titleLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 45));
        titleLabel.setForeground(Design.CLR1);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Library Management System", SwingConstants.CENTER);
        subtitleLabel.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 20));
        subtitleLabel.setForeground(Design.TEXT_COLOR_DINEL);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setPreferredSize(new Dimension(Design.FRAME_SIZE.width, 200));
        formPanel.setMaximumSize(formPanel.getPreferredSize());
        formPanel.setBackground(Design.PRIME_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 14));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(usernameField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 14));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Design.PRIME_COLOR);
        buttonPanel.add(loginButton);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(img);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
    }

    private void setupEffects() {
        Design.addHoverEffect(loginButton, Design.BTN_COLOR_DINEL, Design.HOVER_BTN_DINEL);
    }
    
    /**
     * Opens a JOptionPane Message for Errors.
     * @param String error message.
     **/
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Opens a JOptionPane Message for Successful Login.
     * @param String success message.
     **/
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    public JTextField getUsernameField() {
        return usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }
}