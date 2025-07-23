package Components.Forms;

import Utilities.Design;
import View.Components.CustomButton;
import View.Components.HeaderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.text.NumberFormatter;


public class FineForm extends JDialog {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel btnHolder;
    private HeaderPanel header;
    CustomButton save;
    CustomButton cancel;
    JTextField memberName;
    JTextField memberNo;
    JFormattedTextField amount;
    JTextField description;
    JSpinner dueDate;
    private JLabel memberNameLabel;
    private JLabel memberNoLabel;
    private JLabel amountLabel;
    private JLabel descriptionLabel;
    private JLabel dueDateLabel;

    public FineForm() {
        initDialog();
        initComponents();
        initLayout();
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width /2, Design.FRAME_SIZE.height - 200));
        this.setIconImage(Design.appIcon);
        this.setTitle("Issue a Fine");
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        //Header
        header = new HeaderPanel(new Dimension(Design.FRAME_SIZE.width /2, 100));
        header.setTitle("Issue a Fine");
        header.setSubtitle("Please enter the following information");
        
        formPanel = new JPanel();
        formPanel.setBackground(Design.PRIME_COLOR);
        
        memberNameLabel = new JLabel("Member Name:");
        memberNameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18).deriveFont(Font.BOLD, 18));
        memberName = new JTextField(50);
        memberName.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        memberName.setPreferredSize(new Dimension(memberName.getPreferredSize().width, 50));
        memberName.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        memberNoLabel = new JLabel("Member ID:");
        memberNoLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18).deriveFont(Font.BOLD, 18));
        memberNo = new JTextField(50);
        memberNo.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        memberNo.setPreferredSize(new Dimension(memberName.getPreferredSize().width, 50));
        memberNo.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberNo.setEnabled(false);
        memberNo.setBackground(Color.DARK_GRAY);
        
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMaximumFractionDigits(2);
        
        NumberFormatter formatter = new NumberFormatter(currencyFormat);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false); 
        formatter.setMinimum(0.0); 
        
        amountLabel = new JLabel("Amount (₱):");
        amountLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        amount = new JFormattedTextField(formatter);
        amount.setColumns(50);
        amount.setValue(0.0);
        amount.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        amount.setPreferredSize(new Dimension(amount.getPreferredSize().width, 50));
        amount.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        amount.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> amount.selectAll());
            }
            
        });
        
        descriptionLabel = new JLabel("Description");
        descriptionLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        description = new JTextField(50);
        description.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        description.setPreferredSize(new Dimension(description.getPreferredSize().width, 50));
        description.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        
        dueDateLabel = new JLabel("Due Date(YYYY-MM-DD):");
        dueDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        SpinnerDateModel model = new SpinnerDateModel();
        dueDate = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dueDate, "yyyy-MM-dd");
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        dueDate.setEditor(editor);
        dueDate.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        dueDate.setPreferredSize(new Dimension(dueDate.getPreferredSize().width, 50));
        dueDate.setBorder(BorderFactory.createEmptyBorder());
        
        btnHolder = new JPanel();
        btnHolder.setBackground(Design.PRIME_COLOR);
        
        save = new CustomButton("Save");
        save.setPrimaryColor(Design.BTN2);
        save.setHoverColor(Design.BTN2_HOVER);
        cancel = new CustomButton("Cancel");
        cancel.setPrimaryColor(Design.BTN3);
        cancel.setHoverColor(Design.BTN3_HOVER);
        
    }
    
    private void initLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        formPanel.setLayout(new GridBagLayout());
        btnHolder.setLayout(new BoxLayout(btnHolder, BoxLayout.X_AXIS));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth = 100;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(memberNameLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(memberName, gbc);
        gbc.gridy = 2;
        formPanel.add(memberNoLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(memberNo, gbc);
        gbc.gridy = 4;
        formPanel.add(amountLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(amount, gbc);
        gbc.gridy = 6;
        formPanel.add(descriptionLabel, gbc);
        gbc.gridy = 7;
        formPanel.add(description, gbc);
        gbc.gridy = 8;
        formPanel.add(dueDateLabel, gbc);
        gbc.gridy = 9;
        formPanel.add(dueDate, gbc);
        gbc.gridy = 10;
        gbc.weighty = 1.0;
        formPanel.add(Box.createVerticalGlue(), gbc);
        
        btnHolder.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        btnHolder.add(Box.createHorizontalGlue());
        btnHolder.add(save);
        btnHolder.add(Box.createHorizontalStrut(10));
        btnHolder.add(cancel);
        
        mainPanel.add(header);
        mainPanel.add(formPanel);
        mainPanel.add(btnHolder);
        this.add(mainPanel);
    }
    
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}