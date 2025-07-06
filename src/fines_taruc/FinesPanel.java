/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fines_taruc;

/**
 *
 * @author motar
 */
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FinesPanel extends javax.swing.JPanel {
    
    private FineDAO fineDAO;
    private DefaultTableModel tableModel;
    
    public FinesPanel() {
        initComponents();
        fineDAO = new FineDAO();
        setupTable();
        loadFines();
    }
    
    private void setupTable() {
        String[] columns = {"Fine ID", "Member ID", "Member Name", "Book Title", 
                           "Due Date", "Return Date", "Days Overdue", "Amount", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        finesTable.setModel(tableModel);
        
        // Set column widths
        finesTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // Fine ID
        finesTable.getColumnModel().getColumn(1).setPreferredWidth(80);  // Member ID
        finesTable.getColumnModel().getColumn(2).setPreferredWidth(120); // Member Name
        finesTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Book Title
        finesTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Due Date
        finesTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Return Date
        finesTable.getColumnModel().getColumn(6).setPreferredWidth(100); // Days Overdue
        finesTable.getColumnModel().getColumn(7).setPreferredWidth(80);  // Amount
        finesTable.getColumnModel().getColumn(8).setPreferredWidth(80);  // Status
    }
    
    private void loadFines() {
        tableModel.setRowCount(0);
        List<Fine> fines = fineDAO.getAllFines();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Fine fine : fines) {
            Object[] row = {
                fine.getFineId(),
                fine.getMemberId(),
                fine.getMemberName(),
                fine.getBookTitle(),
                dateFormat.format(fine.getDueDate()),
                fine.getReturnDate() != null ? dateFormat.format(fine.getReturnDate()) : "Not Returned",
                fine.getDaysOverdue(),
                "₱" + fine.getAmount(),
                fine.getStatus().toUpperCase()
            };
            tableModel.addRow(row);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnIssueFine = new javax.swing.JButton();
        btnMarkPaid = new javax.swing.JButton();
        btnDeleteFine = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cmbStatusFilter = new javax.swing.JComboBox<>();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        finesTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(245, 245, 245));

        jPanel1.setBackground(new java.awt.Color(59, 130, 246));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("📚 Library Fines Management");

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
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Actions"));

        btnIssueFine.setBackground(new java.awt.Color(59, 130, 246));
        btnIssueFine.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnIssueFine.setForeground(new java.awt.Color(255, 255, 255));
        btnIssueFine.setText("📝 Issue Fine");
        btnIssueFine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIssueFineActionPerformed(evt);
            }
        });

        btnMarkPaid.setBackground(new java.awt.Color(34, 197, 94));
        btnMarkPaid.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnMarkPaid.setForeground(new java.awt.Color(255, 255, 255));
        btnMarkPaid.setText("✅ Mark as Paid");
        btnMarkPaid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarkPaidActionPerformed(evt);
            }
        });

        btnDeleteFine.setBackground(new java.awt.Color(239, 68, 68));
        btnDeleteFine.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnDeleteFine.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteFine.setText("🗑️ Delete Fine");
        btnDeleteFine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFineActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(btnIssueFine, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnMarkPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(btnDeleteFine, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIssueFine, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMarkPaid, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteFine, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Search & Filter"));

        jLabel2.setText("Search:");

        btnSearch.setText("🔍 Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        jLabel3.setText("Filter by Status:");

        cmbStatusFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All Status", "pending", "paid", "overdue" }));
        cmbStatusFilter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbStatusFilterActionPerformed(evt);
            }
        });

        btnRefresh.setText("🔄 Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSearch)
                .addGap(20, 20, 20)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btnRefresh)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSearch)
                    .addComponent(jLabel3)
                    .addComponent(cmbStatusFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        finesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"Fine ID", "Member ID", "Member Name", "Book Title", "Due Date", "Return Date", "Days Overdue", "Amount", "Status"}
        ));
        finesTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        finesTable.setRowHeight(25);
        jScrollPane1.setViewportView(finesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
    }// </editor-fold>                        
    
    private void btnIssueFineActionPerformed(java.awt.event.ActionEvent evt) {                                             
        FineDialog dialog = new FineDialog((JFrame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setVisible(true);
        if (dialog.isConfirmed()) {
            loadFines();
        }
    }                                            

    private void btnMarkPaidActionPerformed(java.awt.event.ActionEvent evt) {                                            
        int selectedRow = finesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a fine to mark as paid.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String fineId = (String) tableModel.getValueAt(selectedRow, 0);
        String status = (String) tableModel.getValueAt(selectedRow, 8);
        
        if ("PAID".equals(status)) {
            JOptionPane.showMessageDialog(this, "This fine is already paid.", "Already Paid", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to mark this fine as paid?", 
            "Confirm Payment", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (fineDAO.markAsPaid(fineId)) {
                JOptionPane.showMessageDialog(this, "Fine marked as paid successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadFines();
            } else {
                JOptionPane.showMessageDialog(this, "Error marking fine as paid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                           

    private void btnDeleteFineActionPerformed(java.awt.event.ActionEvent evt) {                                              
        int selectedRow = finesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a fine to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String fineId = (String) tableModel.getValueAt(selectedRow, 0);
        String memberName = (String) tableModel.getValueAt(selectedRow, 2);
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete fine " + fineId + " for " + memberName + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            if (fineDAO.deleteFine(fineId)) {
                JOptionPane.showMessageDialog(this, "Fine deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                loadFines();
            } else {
                JOptionPane.showMessageDialog(this, "Error deleting fine.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }                                             

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {                                          
        String searchTerm = txtSearch.getText().trim();
        if (searchTerm.isEmpty()) {
            loadFines();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Fine> fines = fineDAO.searchFines(searchTerm);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Fine fine : fines) {
            Object[] row = {
                fine.getFineId(),
                fine.getMemberId(),
                fine.getMemberName(),
                fine.getBookTitle(),
                dateFormat.format(fine.getDueDate()),
                fine.getReturnDate() != null ? dateFormat.format(fine.getReturnDate()) : "Not Returned",
                fine.getDaysOverdue(),
                "₱" + fine.getAmount(),
                fine.getStatus().toUpperCase()
            };
            tableModel.addRow(row);
        }
        
        if (fines.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No fines found matching: " + searchTerm, "Search Results", JOptionPane.INFORMATION_MESSAGE);
        }
    }                                         

    private void cmbStatusFilterActionPerformed(java.awt.event.ActionEvent evt) {                                                
        String selectedStatus = (String) cmbStatusFilter.getSelectedItem();
        
        if ("All Status".equals(selectedStatus)) {
            loadFines();
            return;
        }
        
        tableModel.setRowCount(0);
        List<Fine> fines = fineDAO.getFinesByStatus(selectedStatus.toLowerCase());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        for (Fine fine : fines) {
            Object[] row = {
                fine.getFineId(),
                fine.getMemberId(),
                fine.getMemberName(),
                fine.getBookTitle(),
                dateFormat.format(fine.getDueDate()),
                fine.getReturnDate() != null ? dateFormat.format(fine.getReturnDate()) : "Not Returned",
                fine.getDaysOverdue(),
                "₱" + fine.getAmount(),
                fine.getStatus().toUpperCase()
            };
            tableModel.addRow(row);
        }
    }                                               

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {                                           
        txtSearch.setText("");
        cmbStatusFilter.setSelectedIndex(0);
        loadFines();
        JOptionPane.showMessageDialog(this, "Fines list refreshed!", "Refresh", JOptionPane.INFORMATION_MESSAGE);
    }                                          

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnDeleteFine;
    private javax.swing.JButton btnIssueFine;
    private javax.swing.JButton btnMarkPaid;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbStatusFilter;
    private javax.swing.JTable finesTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration                   
}

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
