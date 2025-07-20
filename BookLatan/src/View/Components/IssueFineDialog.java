package View.Components;

import Model.Fine;
import Model.FineDAO;
import Model.UserMemberDAO;
import Model.Member;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class IssueFineDialog extends JDialog {
    private FineDAO fineDAO;
    private UserMemberDAO userMemberDAO;
    private JTextField txtFineID;
    private JTextField txtStaffID;
    private JTextField txtMemberID;
    private JTextField txtMemberName;
    private JTextField txtAmount;
    private JTextField txtBookTitle;
    private JTextArea txtDescription;
    private JScrollPane descriptionScrollPane;
    private JTextField txtDaysOverdue;
    private JTextField txtDueDate;
    private JTextField txtReturnDate;
    
    private JButton btnSave;
    private JButton btnCancel;
    
    private boolean fineSaved = false;
    private Member currentMember = null;

    public IssueFineDialog(Window parent, FineDAO fineDAO, UserMemberDAO userMemberDAO) {
        super(parent, "Issue Fine", ModalityType.APPLICATION_MODAL);
        this.fineDAO = fineDAO;
        this.userMemberDAO = userMemberDAO;
        initComponents();
        setupDialog();
        loadNextFineID();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setSize(450, 450);
        setLocationRelativeTo(getParent());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Fine ID:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtFineID = new JTextField(15);
        txtFineID.setEditable(false);
        txtFineID.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(txtFineID, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Staff ID:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtStaffID = new JTextField(15);
        mainPanel.add(txtStaffID, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Member ID:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtMemberID = new JTextField(15);
        mainPanel.add(txtMemberID, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Member Name:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtMemberName = new JTextField(15);
        txtMemberName.setEditable(false);
        txtMemberName.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(txtMemberName, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Book Title:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtBookTitle = new JTextField(15);
        mainPanel.add(txtBookTitle, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDueDate = new JTextField(15);
        mainPanel.add(txtDueDate, gbc);

        gbc.gridx = 0; gbc.gridy = 6; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Return Date (YYYY-MM-DD):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtReturnDate = new JTextField(15);
        mainPanel.add(txtReturnDate, gbc);

        gbc.gridx = 0; gbc.gridy = 7; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Days Overdue:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtDaysOverdue = new JTextField(15);
        txtDaysOverdue.setEditable(false);
        txtDaysOverdue.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(txtDaysOverdue, gbc);

        gbc.gridx = 0; gbc.gridy = 8; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Amount (₱):"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL;
        txtAmount = new JTextField(15);
        txtAmount.setEditable(false);
        txtAmount.setBackground(Color.LIGHT_GRAY);
        mainPanel.add(txtAmount, gbc);

        gbc.gridx = 0; gbc.gridy = 9; gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        txtDescription = new JTextArea(3, 15);
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setBorder(BorderFactory.createLoweredBevelBorder());
        txtDescription.setEditable(false);
        txtDescription.setBackground(Color.LIGHT_GRAY);
        descriptionScrollPane = new JScrollPane(txtDescription);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(descriptionScrollPane, gbc);

        add(mainPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnSave = new JButton("Save");
        btnSave.setBackground(new Color(59, 130, 246));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setOpaque(true);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setBackground(new Color(239, 68, 68));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.setBorderPainted(false);
        btnCancel.setOpaque(true);
        
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        addListeners();
    }

    private void addListeners() {
        txtMemberID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                validateAndLoadMember();
            }
        });

        txtDueDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateOverdueAndAmount();
            }
        });

        txtReturnDate.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                calculateOverdueAndAmount();
            }
        });

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveFine();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void validateAndLoadMember() {
        String memberID = txtMemberID.getText().trim();
        if (memberID.isEmpty()) {
            txtMemberName.setText("");
            txtMemberName.setBackground(Color.LIGHT_GRAY);
            currentMember = null;
            return;
        }

        try {
            int memberIDInt = Integer.parseInt(memberID);
            Member member = null;
            
            try {
                if (userMemberDAO.memberExists(memberIDInt)) {
                    member = userMemberDAO.getMemberByID(memberIDInt);
                } else {
                    member = null;
                }
            } catch (Exception dbException) {
                System.err.println("Database error while checking member: " + dbException.getMessage());
                member = null;
            }
            
            if (member != null) {
                txtMemberName.setText(member.getName());
                txtMemberName.setBackground(Color.WHITE);
                currentMember = member;
                generateDescription();
            } else {
                txtMemberName.setText("Member not found!");
                txtMemberName.setBackground(Color.PINK);
                currentMember = null;
            }
        } catch (NumberFormatException e) {
            txtMemberName.setText("Invalid Member ID!");
            txtMemberName.setBackground(Color.PINK);
            currentMember = null;
        }
    }

    private void calculateOverdueAndAmount() {
        String dueDateStr = txtDueDate.getText().trim();
        String returnDateStr = txtReturnDate.getText().trim();

        if (dueDateStr.isEmpty() || returnDateStr.isEmpty()) {
            txtDaysOverdue.setText("0");
            txtAmount.setText("0.00");
            generateDescription();
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = dateFormat.parse(dueDateStr);
            Date returnDate = dateFormat.parse(returnDateStr);

            long diffInMillies = returnDate.getTime() - dueDate.getTime();
            long daysOverdue = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

            if (daysOverdue > 0) {
                txtDaysOverdue.setText(String.valueOf(daysOverdue));
                double amount = daysOverdue * 50.0;
                txtAmount.setText(String.format("%.2f", amount));
            } else {
                txtDaysOverdue.setText("0");
                txtAmount.setText("0.00");
            }

            generateDescription();

        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid date format. Please use YYYY-MM-DD format.",
                "Date Format Error",
                JOptionPane.ERROR_MESSAGE);
            txtDaysOverdue.setText("0");
            txtAmount.setText("0.00");
        }
    }

    private void generateDescription() {
        StringBuilder description = new StringBuilder();
        
        String memberName = currentMember != null ? currentMember.getName() : txtMemberName.getText();
        String bookTitle = txtBookTitle.getText().trim();
        String daysOverdueStr = txtDaysOverdue.getText().trim();
        String amountStr = txtAmount.getText().trim();

        if (!memberName.isEmpty() && !memberName.equals("Member not found!") && !memberName.equals("Invalid Member ID!")) {
            description.append("Fine issued to ").append(memberName);
        }

        if (!bookTitle.isEmpty()) {
            if (description.length() > 0) description.append(" for ");
            description.append("overdue book: ").append(bookTitle);
        }

        if (!daysOverdueStr.isEmpty() && !daysOverdueStr.equals("0")) {
            if (description.length() > 0) description.append(". ");
            description.append("Book was ").append(daysOverdueStr).append(" day(s) overdue");
        }

        if (!amountStr.isEmpty() && !amountStr.equals("0.00")) {
            if (description.length() > 0) description.append(". ");
            description.append("Total fine amount: ₱").append(amountStr);
        }

        if (description.length() == 0) {
            description.append("Library fine for overdue book return");
        }

        txtDescription.setText(description.toString());
    }

    private void setupDialog() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void loadNextFineID() {
        int nextFineID = fineDAO.getMaxFineID() + 1;
        txtFineID.setText(String.valueOf(nextFineID));
    }

    private void saveFine() {
        try {
            if (txtStaffID.getText().trim().isEmpty() ||
                txtMemberID.getText().trim().isEmpty() || 
                txtBookTitle.getText().trim().isEmpty() ||
                txtDueDate.getText().trim().isEmpty() ||
                txtReturnDate.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Please fill in all required fields (Staff ID, Member ID, Book Title, Due Date, Return Date).",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String memberID = txtMemberID.getText().trim();
            if (!memberID.isEmpty()) {
                try {
                    int memberIDInt = Integer.parseInt(memberID);
                    Member member = null;
                    
                    try {
                        if (userMemberDAO.memberExists(memberIDInt)) {
                            member = userMemberDAO.getMemberByID(memberIDInt);
                        } else {
                            member = null;
                        }
                    } catch (Exception dbException) {
                        System.err.println("Database error while checking member: " + dbException.getMessage());
                        member = null;
                    }
                    
                    if (member == null) {
                        int choice = JOptionPane.showConfirmDialog(this,
                            "Member ID '" + memberID + "' is not registered in the system.\n" +
                            "Would you like to close this dialog and register the member first?",
                            "Member Not Registered",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE);
                        
                        if (choice == JOptionPane.YES_OPTION) {
                            dispose();
                        }
                        return;
                    } else {
                        currentMember = member;
                        txtMemberName.setText(member.getName());
                        txtMemberName.setBackground(Color.WHITE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric Member ID.",
                        "Invalid Member ID Format",
                        JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please enter a Member ID.",
                    "Missing Member ID",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (currentMember == null) {
                JOptionPane.showMessageDialog(this,
                    "Please enter a valid Member ID. Member must exist in the system.",
                    "Invalid Member",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            calculateOverdueAndAmount();

            Fine fine = new Fine();
            fine.setFineID(Integer.parseInt(txtFineID.getText()));
            fine.setStaffID(txtStaffID.getText());
            fine.setMemberID(txtMemberID.getText());
            fine.setMemberName(currentMember.getName());
            fine.setAmount(Double.parseDouble(txtAmount.getText()));
            fine.setReason("Overdue book return");
            fine.setDateIssued(new Date());
            fine.set_status("Pending");
            fine.setBook_title(txtBookTitle.getText());
            fine.setDays_overdue(Integer.parseInt(txtDaysOverdue.getText()));
            fine.setDescription(txtDescription.getText());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            fine.setDue_date(new java.sql.Date(dateFormat.parse(txtDueDate.getText()).getTime()));
            fine.setReturn_date(new java.sql.Date(dateFormat.parse(txtReturnDate.getText()).getTime()));

            if (fineDAO.addFine(fine)) {
                fineSaved = true;
                JOptionPane.showMessageDialog(this,
                    "Fine issued successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to issue fine. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numeric values for dates and amounts.",
                "Input Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this,
                "Invalid date format. Please use YYYY-MM-DD format.",
                "Date Format Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Error saving fine: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public boolean isFineSaved() {
        return fineSaved;
    }
}