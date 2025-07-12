/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author motar
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

public class FineDialog extends javax.swing.JDialog {
    
    private FineDAO fineDAO;
    private boolean confirmed = false;
    
    public FineDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        try {
            fineDAO = new FineDAO();
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
        setLocationRelativeTo(parent);
        generateFineId();
    }
    
    private void generateFineId() {
        String fineId = "F" + String.format("%03d", (int)(Math.random() * 1000));
        txtFineId.setText(fineId);
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtFineId = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMemberId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtMemberName = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtBookTitle = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDueDate = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtReturnDate = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDaysOverdue = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtAmount = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        btnCalculate = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Issue New Fine");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(59, 130, 246));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("📝 Issue New Fine");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Fine Details"));

        jLabel2.setText("Fine ID:");
        txtFineId.setEditable(false);
        txtFineId.setBackground(new java.awt.Color(240, 240, 240));

        jLabel3.setText("Member ID:");
        jLabel4.setText("Member Name:");
        jLabel5.setText("Book Title:");
        jLabel6.setText("Due Date (yyyy-mm-dd):");
        jLabel7.setText("Return Date (yyyy-mm-dd):");
        jLabel8.setText("Days Overdue:");
        txtDaysOverdue.setEditable(false);
        txtDaysOverdue.setBackground(new java.awt.Color(240, 240, 240));

        jLabel9.setText("Amount (₱):");
        jLabel10.setText("Description:");

        btnCalculate.setBackground(new java.awt.Color(34, 197, 94));
        btnCalculate.setForeground(new java.awt.Color(255, 255, 255));
        btnCalculate.setText("🧮 Calculate Fine");
        btnCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFineId)
                    .addComponent(txtMemberId)
                    .addComponent(txtMemberName)
                    .addComponent(txtBookTitle)
                    .addComponent(txtDueDate)
                    .addComponent(txtReturnDate)
                    .addComponent(txtDaysOverdue)
                    .addComponent(txtAmount)
                    .addComponent(txtDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(btnCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtFineId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMemberId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMemberName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtBookTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDueDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtReturnDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtDaysOverdue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(btnCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(248, 249, 250));

        btnSave.setBackground(new java.awt.Color(59, 130, 246));
        btnSave.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("💾 Save Fine");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(107, 114, 128));
        btnCancel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("❌ Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>                        

    private void btnCalculateActionPerformed(java.awt.event.ActionEvent evt) {                                             
        try {
            String dueDateStr = txtDueDate.getText().trim();
            String returnDateStr = txtReturnDate.getText().trim();
            
            if (dueDateStr.isEmpty() || returnDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both due date and return date.", "Missing Dates", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dueDate = sdf.parse(dueDateStr);
            Date returnDate = sdf.parse(returnDateStr);
            
            long diffInMillies = returnDate.getTime() - dueDate.getTime();
            int daysOverdue = (int) (diffInMillies / (1000 * 60 * 60 * 24));
            
            if (daysOverdue > 0) {
                double amount = daysOverdue * 100; 
                txtDaysOverdue.setText(String.valueOf(daysOverdue));
                txtAmount.setText(String.valueOf(amount));
                txtDescription.setText("Book returned " + daysOverdue + " day(s) late - ₱100 per day");
                JOptionPane.showMessageDialog(this, "Fine calculated: ₱" + amount + " for " + daysOverdue + " day(s) overdue", "Calculation Complete", JOptionPane.INFORMATION_MESSAGE);
            } else {
                txtDaysOverdue.setText("0");
                txtAmount.setText("0");
                txtDescription.setText("Book returned on time - No fine");
                JOptionPane.showMessageDialog(this, "Book was returned on time. No fine required.", "No Fine", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid dates in yyyy-mm-dd format.\nExample: 2024-01-15", "Invalid Date Format", JOptionPane.ERROR_MESSAGE);
        }
    }                                            

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {                                        
        try {
            if (txtMemberId.getText().trim().isEmpty() || 
                txtMemberName.getText().trim().isEmpty() || 
                txtBookTitle.getText().trim().isEmpty() || 
                txtDueDate.getText().trim().isEmpty() || 
                txtReturnDate.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Missing Information", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            Fine fine = new Fine();
            fine.setFineId(txtFineId.getText());
            fine.setMemberId(txtMemberId.getText().trim());
            fine.setMemberName(txtMemberName.getText().trim());
            fine.setBookTitle(txtBookTitle.getText().trim());
            fine.setIsbn(""); 
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            fine.setDueDate(sdf.parse(txtDueDate.getText().trim()));
            fine.setReturnDate(sdf.parse(txtReturnDate.getText().trim()));
            fine.setDaysOverdue(Integer.parseInt(txtDaysOverdue.getText().isEmpty() ? "0" : txtDaysOverdue.getText()));
            fine.setAmount(Double.parseDouble(txtAmount.getText().isEmpty() ? "0" : txtAmount.getText()));
            
            int daysOverdue = fine.getDaysOverdue();
            if (daysOverdue == 0) {
                fine.setStatus("paid"); 
            } else if (daysOverdue > 14) {
                fine.setStatus("overdue");
            } else {
                fine.setStatus("pending");
            }
            
            fine.setIssuedDate(new Date());
            fine.setDescription(txtDescription.getText().trim());
            
            if (fineDAO.addFine(fine)) {
                JOptionPane.showMessageDialog(this, "Fine issued successfully!\n\nFine ID: " + fine.getFineId() + "\nMember: " + fine.getMemberName() + "\nAmount: ₱" + fine.getAmount(), "Success", JOptionPane.INFORMATION_MESSAGE);
                confirmed = true;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error issuing fine. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please check all fields and ensure dates are in yyyy-mm-dd format.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }                                       

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {                                          
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel? All entered data will be lost.", 
            "Confirm Cancel", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
        }
    }                                         

    private javax.swing.JButton btnCalculate;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField txtAmount;
    private javax.swing.JTextField txtBookTitle;
    private javax.swing.JTextField txtDaysOverdue;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDueDate;
    private javax.swing.JTextField txtFineId;
    private javax.swing.JTextField txtMemberId;
    private javax.swing.JTextField txtMemberName;
    private javax.swing.JTextField txtReturnDate;
}


