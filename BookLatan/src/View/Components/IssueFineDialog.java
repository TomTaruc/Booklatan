package View.Components;

import Model.Fine;
import Model.FineDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.UUID;

public class IssueFineDialog extends JDialog {

    private FineDAO fineDAO;
    private boolean fineSaved = false;

    private JTextField txtFineID;
    private JTextField txtMemberID;
    private JTextField txtMemberName;
    private JTextField txtBookTitle;
    private JTextField txtDueDate;
    private JTextField txtReturnDate;
    private JTextField txtDaysOverdue;
    private JTextField txtAmount;
    private JTextArea txtDescription;

    private JButton btnCalculateFine;
    private JButton btnSaveFine;
    private JButton btnCancel;

    private Color primaryBgColor = new Color(253, 245, 230);
    private Color brownCalculateBtn = new Color(139, 69, 19);
    private Color lightBrownSaveBtn = new Color(160, 82, 45);
    private Color grayCancelBtn = new Color(150, 150, 150);

    private static final double FINE_PER_DAY = 10.0;

    public IssueFineDialog(Window owner, FineDAO fineDAO) {
        super(owner, "Issue New Fine", ModalityType.APPLICATION_MODAL);
        this.fineDAO = fineDAO;
        setSize(480, 500);
        setLocationRelativeTo(owner);
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        addListeners();
        generateFineID();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBackground(primaryBgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JPanel fineDetailsPanel = new JPanel(new GridBagLayout());
        fineDetailsPanel.setBackground(primaryBgColor);
        fineDetailsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Fine Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 12),
                Color.DARK_GRAY
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 5, 3, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        addFormField(fineDetailsPanel, gbc, "Fine ID:", txtFineID = new JTextField(12), 0, false);

        addFormField(fineDetailsPanel, gbc, "Member ID:", txtMemberID = new JTextField(12), 1, true);

        addFormField(fineDetailsPanel, gbc, "Member Name:", txtMemberName = new JTextField(12), 2, true);

        addFormField(fineDetailsPanel, gbc, "Book Title:", txtBookTitle = new JTextField(12), 3, true);

        addFormField(fineDetailsPanel, gbc, "Due Date (yyyy-mm-dd):", txtDueDate = new JTextField(12), 4, true);

        addFormField(fineDetailsPanel, gbc, "Return Date (yyyy-mm-dd):", txtReturnDate = new JTextField(12), 5, true);

        addFormField(fineDetailsPanel, gbc, "Days Overdue:", txtDaysOverdue = new JTextField(12), 6, false);

        addFormField(fineDetailsPanel, gbc, "Amount (₱):", txtAmount = new JTextField(12), 7, false);

        JLabel lblDescription = new JLabel("Description:");
        lblDescription.setFont(new Font("Segoe UI", Font.BOLD, 12));
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        fineDetailsPanel.add(lblDescription, gbc);

        txtDescription = new JTextArea(2, 12);
        txtDescription.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        JScrollPane descriptionScrollPane = new JScrollPane(txtDescription);
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        fineDetailsPanel.add(descriptionScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 5, 5, 5);
        btnCalculateFine = createStyledButton(" Calculate Fine", brownCalculateBtn, createUnicodeIcon("\uF02D"), 8, new Font("Segoe UI", Font.BOLD, 14), new Insets(8, 15, 8, 15));
        fineDetailsPanel.add(btnCalculateFine, gbc);

        mainPanel.add(fineDetailsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(primaryBgColor);

        btnSaveFine = createStyledButton(" Save Fine", lightBrownSaveBtn, createUnicodeIcon("\uF0C7"), 8, new Font("Segoe UI", Font.BOLD, 14), new Insets(8, 15, 8, 15));
        btnCancel = createStyledButton(" Cancel", grayCancelBtn, createUnicodeIcon("\uF00D"), 8, new Font("Segoe UI", Font.BOLD, 14), new Insets(8, 15, 8, 15));

        buttonPanel.add(btnSaveFine);
        buttonPanel.add(btnCancel);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField textField, int row, boolean editable) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        textField.setEditable(editable);
        textField.setFont(new Font("Segoe UI", Font.BOLD, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, Color.LIGHT_GRAY),
            new EmptyBorder(4, 6, 4, 6)
        ));
        panel.add(textField, gbc);
    }

    private void addListeners() {
        btnCalculateFine.addActionListener(this::calculateFineAction);
        btnSaveFine.addActionListener(this::saveFineAction);
        btnCancel.addActionListener(e -> dispose());

        KeyAdapter updateListener = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                updateDescription();
            }
        };
        txtDueDate.addKeyListener(updateListener);
        txtReturnDate.addKeyListener(updateListener);
        txtBookTitle.addKeyListener(updateListener);
        txtMemberName.addKeyListener(updateListener);
    }

    private void calculateFineAction(ActionEvent e) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = dateFormat.parse(txtDueDate.getText());
            Date returnDate = dateFormat.parse(txtReturnDate.getText());

            long diff = returnDate.getTime() - dueDate.getTime();
            long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            if (diffDays > 0) {
                txtDaysOverdue.setText(String.valueOf(diffDays));
                double amount = diffDays * FINE_PER_DAY;
                txtAmount.setText(String.format("%.2f", amount));
                updateDescription();
            } else {
                txtDaysOverdue.setText("0");
                txtAmount.setText("0.00");
                txtDescription.setText("No overdue days.");
            }
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Please enter dates in YYYY-MM-DD format.", "Date Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error calculating fine: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void updateDescription() {
        String bookTitle = txtBookTitle.getText().trim();
        String memberName = txtMemberName.getText().trim();
        String dueDateStr = txtDueDate.getText().trim();
        String returnDateStr = txtReturnDate.getText().trim();
        String daysOverdueStr = txtDaysOverdue.getText();
        String amountStr = txtAmount.getText();

        StringBuilder description = new StringBuilder();
        
        if (!daysOverdueStr.isEmpty() && !daysOverdueStr.equals("0")) {
            description.append("Book returned ").append(daysOverdueStr).append(" day(s) late");
            if (!amountStr.isEmpty() && !amountStr.equals("0.00")) {
                description.append(" - ₱").append(FINE_PER_DAY).append(" per day");
            }
        } else {
            description.append("No days overdue.");
        }

        if (!bookTitle.isEmpty()) {
            description.append(" for '").append(bookTitle).append("'");
        }
        if (!memberName.isEmpty()) {
            description.append(" by ").append(memberName);
        }
        if (!dueDateStr.isEmpty()) {
            description.append(" (Due: ").append(dueDateStr).append(")");
        }
        if (!returnDateStr.isEmpty()) {
            description.append(" (Returned: ").append(returnDateStr).append(")");
        }
        
        txtDescription.setText(description.toString());
    }

    private void saveFineAction(ActionEvent e) {
        if (txtFineID.getText().isEmpty() || txtMemberID.getText().isEmpty() || txtMemberName.getText().isEmpty() ||
            txtBookTitle.getText().isEmpty() || txtDueDate.getText().isEmpty() || txtReturnDate.getText().isEmpty() ||
            txtAmount.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields (Fine ID, Member ID, Member Name, Book Title, Due Date, Return Date, Amount).", "Missing Information", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = dateFormat.parse(txtDueDate.getText());
            Date returnDate = dateFormat.parse(txtReturnDate.getText());
            double amount = Double.parseDouble(txtAmount.getText());
            int daysOverdue = Integer.parseInt(txtDaysOverdue.getText());

            Fine fine = new Fine();
            fine.setFineID(txtFineID.getText());
            fine.setMemberID(txtMemberID.getText());
            fine.setMemberName(txtMemberName.getText());
            fine.setBook_title(txtBookTitle.getText());
            fine.setDue_date(dueDate);
            fine.setReturn_date(returnDate);
            fine.setDays_overdue(daysOverdue);
            fine.setAmount(amount);
            fine.setDateIssued(new Date());
            fine.set_status("Outstanding");
            fine.setDescription(txtDescription.getText());
            fine.setStaffID("N/A");
            fine.setReason("Overdue Fine");
            fine.setIsbn("N/A");

            if (fineDAO.addFine(fine)) {
                JOptionPane.showMessageDialog(this, "Fine saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                fineSaved = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to save fine. Please check console for details.", "Save Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Please use YYYY-MM-DD.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount or days overdue format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private JButton createStyledButton(String text, Color bgColor, Icon icon, int radius, Font font, Insets paddingInsets) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setContentAreaFilled(true);

        button.setFont(font);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setIcon(icon);
        button.setIconTextGap(8);
        button.setBorder(new CompoundBorder(
            new RoundedBorder(radius, bgColor),
            new EmptyBorder(paddingInsets.top, paddingInsets.left, paddingInsets.bottom, paddingInsets.right)
        ));
        button.setMargin(new Insets(0,0,0,0));
        return button;
    }

    private static class RoundedBorder implements Border {
        private int radius;
        private Color borderColor;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.borderColor = color;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius/2 + 1, this.radius/2 + 1, this.radius/2 + 2, this.radius/2);
        }

        public boolean isBorderOpaque() {
            return false;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(borderColor);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));

            g2.dispose();
        }
    }

    private ImageIcon createSquareIcon(Color color) {
        BufferedImage image = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, 20, 20);
        g.dispose();
        return new ImageIcon(image);
    }

    private ImageIcon createUnicodeIcon(String unicodeChar) {
        JLabel label = new JLabel(unicodeChar);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(Color.WHITE);
        label.setSize(label.getPreferredSize());
        BufferedImage image = new BufferedImage(label.getWidth(), label.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        label.paint(g);
        g.dispose();
        return new ImageIcon(image);
    }

    public boolean isFineSaved() {
        return fineSaved;
    }

    private void generateFineID() {
        String uuid = UUID.randomUUID().toString().substring(0, 7).toUpperCase();
        txtFineID.setText("F" + uuid);
    }
}
