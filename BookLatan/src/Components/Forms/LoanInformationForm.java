/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Components.Designs.BorderlessTable;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import Utilities.Design;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joseph Rey
 */
public class LoanInformationForm extends JDialog{
    
    private HeaderPanel header;
    private JPanel mainPanel;
    private JPanel formsPanel;
    private ModernScrollPane scrollPanel;
    
    private JLabel loanIDLabel;
    private JLabel memberNameLabel;
    private JLabel memberIDLabel;
    private JLabel issueDateLabel;
    private JLabel dueDateLabel;
    private JLabel returnDateLabel;
    private JLabel statusLabel;
    
    public JTextField loanIDField;
    public JTextField memberNameField;
    public JTextField memberIDField;
    public JTextField issueDateField;
    public JTextField dueDateField;
    public JTextField returnDateField;
    public JTextField statusField;
    
    public CustomButton markAsReturned;
    public CustomButton issueAFine;
    public CustomButton deleteLoan;
    
    private final String[] columnNames = {"Book ID", "Title", "Category", "Author", "Publisher"};
    public ModernScrollPane scrollTable;
    public BorderlessTable loanedBooks;
    public DefaultTableModel model;
    
    
    public LoanInformationForm() {
        initDialog();
        initComponents();
        initLayout();
        this.setVisible(true);
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width /2, Design.FRAME_SIZE.height - 200));
        this.setIconImage(Design.appIcon);
        this.setTitle("Loan Information");
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }
    
    private void initComponents() {
        header = new HeaderPanel(new Dimension(Design.FRAME_SIZE.width/2, 100));
        header.setTitle("Loan Information");
        header.setSubtitle("View loan information");
        
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Design.FRAME_SIZE.width /2 - 50, 1080));
        mainPanel.setMaximumSize(mainPanel.getPreferredSize());
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        formsPanel = new JPanel();
        formsPanel.setBackground(Design.PRIME_COLOR);
        
        scrollPanel = new ModernScrollPane();
        
        //Labels
        loanIDLabel = new JLabel("Loan ID");
        loanIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        memberNameLabel = new JLabel("Member Name");
        memberNameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        memberIDLabel = new JLabel("Member ID");
        memberIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        issueDateLabel = new JLabel("Date Issued");
        issueDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        dueDateLabel = new JLabel("Date Due");
        dueDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        returnDateLabel = new JLabel("Date Returned");
        returnDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        statusLabel = new JLabel("Status");
        statusLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        //Fields
        loanIDField = new JTextField(50);
        loanIDField.setPreferredSize(new Dimension(loanIDField.getPreferredSize().width, 50));
        loanIDField.setEditable(false);
        loanIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        loanIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        
        memberNameField = new JTextField(50);
        memberNameField.setPreferredSize(new Dimension(memberNameField.getPreferredSize().width, 50));
        memberNameField.setEditable(false);
        memberNameField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberNameField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        memberIDField = new JTextField(50);
        memberIDField.setPreferredSize(new Dimension(memberIDField.getPreferredSize().width, 50));
        memberIDField.setEditable(false);
        memberIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        issueDateField = new JTextField(50);
        issueDateField.setPreferredSize(new Dimension(issueDateField.getPreferredSize().width, 50));
        issueDateField.setEditable(false);
        issueDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        issueDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        dueDateField = new JTextField(50);
        dueDateField.setPreferredSize(new Dimension(dueDateField.getPreferredSize().width, 50));
        dueDateField.setEditable(false);
        dueDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        dueDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        returnDateField = new JTextField(50);
        returnDateField.setPreferredSize(new Dimension(returnDateField.getPreferredSize().width, 50));
        returnDateField.setEditable(false);
        returnDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        returnDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        statusField = new JTextField(50);
        statusField.setPreferredSize(new Dimension(statusField.getPreferredSize().width, 50));
        statusField.setEditable(false);
        statusField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        statusField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        //Table
        loanedBooks = new BorderlessTable();
        loanedBooks.setBackground(Design.PRIME_COLOR);
        model = new DefaultTableModel(columnNames, 0);
        loanedBooks.changeModel(model);
        loanedBooks.getColumnModel().getColumn(0).setPreferredWidth(30);
        
        scrollTable = new ModernScrollPane(loanedBooks);
        scrollTable.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        scrollTable.setBackground(Design.PRIME_COLOR);
        
        //buttons
        markAsReturned = new CustomButton("Mark Returned");
        markAsReturned.setPrimaryColor(Design.BTN2);
        markAsReturned.setHoverColor(Design.BTN2_HOVER);
        
        deleteLoan = new CustomButton("Delete Loan");
        deleteLoan.setPrimaryColor(Design.BTN3);
        deleteLoan.setHoverColor(Design.BTN3_HOVER);
        
        issueAFine = new CustomButton("Issue a Fine");
        issueAFine.setPrimaryColor(Design.BTN1);
        issueAFine.setHoverColor(Design.BTN2_HOVER);
    }
    
    private void initLayout() {
        header.add(Box.createHorizontalGlue());
        header.add(markAsReturned);
        header.add(Box.createHorizontalStrut(10));
        header.add(issueAFine);
        header.add(Box.createHorizontalStrut(10));
        header.add(deleteLoan);
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        formsPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formsPanel.add(loanIDLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        formsPanel.add(loanIDField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formsPanel.add(memberNameLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formsPanel.add(memberNameField, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        formsPanel.add(memberIDLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        formsPanel.add(memberIDField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formsPanel.add(issueDateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 6;
        formsPanel.add(issueDateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 7;
        formsPanel.add(dueDateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 8;
        formsPanel.add(dueDateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 9;
        formsPanel.add(returnDateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 10;
        formsPanel.add(returnDateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        formsPanel.add(statusLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 12;
        formsPanel.add(statusField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 13;
        gbc.weighty = 1;
        formsPanel.add(Box.createVerticalGlue(), gbc);
        
        
        mainPanel.add(formsPanel);
        mainPanel.add(scrollTable);
        scrollPanel.setViewportView(mainPanel);
        
        this.add(header, BorderLayout.NORTH);
        this.add(scrollPanel, BorderLayout.CENTER);
        
    }
}
