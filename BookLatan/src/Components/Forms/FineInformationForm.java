/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Utilities.Design;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Joseph Rey
 */
public class FineInformationForm extends JDialog{
    private JLabel fineIDLabel;
    public JTextField fineIDField;
    private JLabel staffIDLabel;
    public JTextField staffIDField;
    private JLabel staffNameLabel;
    public JTextField staffNameField;
    private JLabel memberIDLabel;
    public JTextField memberIDField;
    private JLabel memberNameLabel;
    public JTextField memberNameField;
    private JLabel amountLabel;
    public JTextField amountField;
    private JLabel statusLabel;
    public JTextField statusField;
    private JLabel issuedDateLabel;
    public JTextField issuedDateField;
    private JLabel dueDateLabel;
    public JTextField dueDateField;
    private JLabel paidDateLabel;
    public JTextField paidDateField;
    private JLabel descriptionLabel;
    public JTextField descriptionField;
    public CustomButton payBtn;
    public CustomButton delBtn;
    private HeaderPanel header;
    private JPanel forms;
    private JPanel mainPanel;
    private ModernScrollPane scroll;
    
    public FineInformationForm() {
        initDialog();
        initComponents();
        initLayout();
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width /2, Design.FRAME_SIZE.height - 200));
        this.setIconImage(Design.appIcon);
        this.setTitle("Fine Information");
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }
    
    private void initComponents() {
        
        scroll = new ModernScrollPane();
        scroll.setPreferredSize(new Dimension(Design.FRAME_SIZE.width/2, Design.FRAME_SIZE.height));
        
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Design.FRAME_SIZE.width/2 - 100, Design.FRAME_SIZE.height/2 + 500));
        mainPanel.setMaximumSize(mainPanel.getPreferredSize());
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        header = new HeaderPanel(new Dimension(Design.FRAME_SIZE.width/2, 100));
        header.setTitle("Fine Information");
        header.setSubtitle("View Fine Information");
        
        forms = new JPanel();
        forms.setBackground(Design.PRIME_COLOR);
        
        fineIDLabel = new JLabel("Fine ID");
        fineIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        fineIDField = new JTextField(50);
        fineIDField.setPreferredSize(new Dimension(fineIDField.getPreferredSize().width, 50));
        fineIDField.setEditable(false);
        fineIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        fineIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        staffNameLabel = new JLabel("Issued By:");
        staffNameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        staffNameField = new JTextField(50);
        staffNameField.setPreferredSize(new Dimension(staffNameField.getPreferredSize().width, 50));
        staffNameField.setEditable(false);
        staffNameField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        staffNameField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        staffIDLabel = new JLabel("Staff ID:");
        staffIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        staffIDField = new JTextField(50);
        staffIDField.setPreferredSize(new Dimension(staffIDField.getPreferredSize().width, 50));
        staffIDField.setEditable(false);
        staffIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        staffIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        memberNameLabel = new JLabel("Member Name:");
        memberNameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        memberNameField = new JTextField(50);
        memberNameField.setPreferredSize(new Dimension(memberNameField.getPreferredSize().width, 50));
        memberNameField.setEditable(false);
        memberNameField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberNameField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        memberIDLabel = new JLabel("Member ID:");
        memberIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        memberIDField = new JTextField(50);
        memberIDField.setPreferredSize(new Dimension(memberIDField.getPreferredSize().width, 50));
        memberIDField.setEditable(false);
        memberIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        amountLabel = new JLabel("Amount:");
        amountLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        amountField = new JTextField(50);
        amountField.setPreferredSize(new Dimension(amountField.getPreferredSize().width, 50));
        amountField.setEditable(false);
        amountField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        amountField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        statusLabel = new JLabel("Status:");
        statusLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        statusField = new JTextField(50);
        statusField.setPreferredSize(new Dimension(statusField.getPreferredSize().width, 50));
        statusField.setEditable(false);
        statusField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        statusField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        paidDateLabel = new JLabel("Paid Date:");
        paidDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        paidDateField = new JTextField(50);
        paidDateField.setPreferredSize(new Dimension(paidDateField.getPreferredSize().width, 50));
        paidDateField.setEditable(false);
        paidDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        paidDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        issuedDateLabel = new JLabel("Date Issued: ");
        issuedDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        issuedDateField = new JTextField(50);
        issuedDateField.setPreferredSize(new Dimension(issuedDateField.getPreferredSize().width, 50));
        issuedDateField.setEditable(false);
        issuedDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        issuedDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        dueDateLabel = new JLabel("Date Due:");
        dueDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        dueDateField = new JTextField(50);
        dueDateField.setPreferredSize(new Dimension(dueDateField.getPreferredSize().width, 50));
        dueDateField.setEditable(false);
        dueDateField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        dueDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        descriptionLabel = new JLabel("Description:");
        descriptionLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        descriptionField = new JTextField(50);
        descriptionField.setPreferredSize(new Dimension(descriptionField.getPreferredSize().width, 50));
        descriptionField.setEditable(false);
        descriptionField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        descriptionField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        payBtn= new CustomButton("Mark as Paid");
        payBtn.setPrimaryColor(Design.BTN1);
        payBtn.setHoverColor(Design.BTN1_HOVER);
        delBtn = new CustomButton("Delete");
        delBtn.setPrimaryColor(Design.BTN3);
        delBtn.setHoverColor(Design.BTN3_HOVER);
    }
    
    private void initLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        forms.setLayout(new GridBagLayout());
        
        header.add(Box.createHorizontalGlue());
        header.add(payBtn);
        header.add(Box.createHorizontalStrut(10));
        header.add(delBtn);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        forms.add(fineIDLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        forms.add(fineIDField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        forms.add(staffNameLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        forms.add(staffNameField,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        forms.add(staffIDLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        forms.add(staffIDField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        forms.add(memberNameLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 5;
        forms.add(memberNameField,gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        forms.add(memberIDLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        forms.add(memberIDField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        forms.add(amountLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 7;
        forms.add(amountField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 8;
        forms.add(statusLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 9;
        forms.add(statusField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 10;
        forms.add(issuedDateLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 11;
        forms.add(issuedDateField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 12;
        forms.add(dueDateLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 13;
        forms.add(dueDateField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 14;
        forms.add(paidDateLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 15;
        forms.add(paidDateField,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 16;
        forms.add(descriptionLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 17;
        forms.add(descriptionField, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 18;
        gbc.weighty = 1;
        forms.add(Box.createVerticalGlue(), gbc);
        
        mainPanel.add(forms);
        scroll.setViewportView(mainPanel);
        this.add(header, BorderLayout.NORTH);
        this.add(scroll);
    }
    
}
