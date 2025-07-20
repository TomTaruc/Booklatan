package View.Components;

import Control.Components.FinesController;
import Model.UserMemberDAO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class FinesPanel extends JPanel {

    private FinesController controller;
    private DefaultTableModel finesTableModel;
    private JTable finesTable;
    private JTextField txtSearch;
    private JComboBox<String> cmbStatusFilter;
    private JButton btnIssueFine;
    private JButton btnMarkAsPaid;
    private JButton btnDeleteFine;
    private JButton btnRefresh;
    private JButton btnSearch;

    private Color primaryBgColor = new Color(240, 240, 240);
    private Color headerTextColor = Color.BLACK;

    private Color issueFineBtnColor = new Color(59, 130, 246);
    private Color markAsPaidBtnColor = new Color(34, 197, 94);
    private Color deleteFineBtnColor = new Color(239, 68, 68);
    private Color searchRefreshBtnColor = new Color(59, 130, 246);

    private static final String[] TABLE_COLUMN_NAMES = {
        "Fine ID", "Member Name", "Book Title", "Due Date", "Return Date",
        "Days Overdue", "Amount", "Status"
    };

    public FinesPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(primaryBgColor);

        initComponents();
        setupFinesTable();
        addListeners();
    }

    public void setController(FinesController controller) {
        this.controller = controller;
        refreshFinesTable();
    }

    private void initComponents() {
        JPanel mainContentWrapper = new JPanel(new BorderLayout());
        mainContentWrapper.setBackground(primaryBgColor);
        mainContentWrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(primaryBgColor);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        actionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Actions", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY));
        actionsPanel.setBackground(primaryBgColor);
        actionsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnIssueFine = createStyledButton(" Issue Fine", issueFineBtnColor, createUnicodeIcon("\uF02B"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));
        btnMarkAsPaid = createStyledButton(" Mark as Paid", markAsPaidBtnColor, createUnicodeIcon("\uF00C"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));
        btnDeleteFine = createStyledButton(" Delete Fine", deleteFineBtnColor, createUnicodeIcon("\uF014"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));

        actionsPanel.add(btnIssueFine);
        actionsPanel.add(btnMarkAsPaid);
        actionsPanel.add(btnDeleteFine);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(actionsPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalStrut(10));

        JPanel searchFilterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchFilterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Search & Filter", TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14), Color.DARK_GRAY));
        searchFilterPanel.setBackground(primaryBgColor);
        searchFilterPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        searchFilterPanel.add(searchLabel);

        txtSearch = new JTextField(35);
        txtSearch.putClientProperty("JTextField.placeholderText", "Search by Fine ID, Member Name, Book Title, Member ID, or Description");
        txtSearch.setBorder(new EmptyBorder(5, 8, 5, 8));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchFilterPanel.add(txtSearch);

        btnSearch = createStyledButton(" Search", searchRefreshBtnColor, createUnicodeIcon("\uF002"), new Font("Segoe UI", Font.BOLD, 14), new Insets(10, 20, 10, 20));
        searchFilterPanel.add(btnSearch);

        searchFilterPanel.add(new JLabel("Filter by Status:"));
        cmbStatusFilter = new JComboBox<>(new String[]{"All Status", "Pending", "Paid"});
        cmbStatusFilter.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        searchFilterPanel.add(cmbStatusFilter);

        btnRefresh = createStyledButton(" Refresh", searchRefreshBtnColor, createUnicodeIcon("\uF021"), new Font("Segoe UI", Font.BOLD, 12), new Insets(8, 15, 8, 15));
        searchFilterPanel.add(btnRefresh);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(searchFilterPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalStrut(10));

        finesTableModel = new DefaultTableModel(TABLE_COLUMN_NAMES, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        finesTable = new JTable(finesTableModel);
        finesTable.setFillsViewportHeight(true);
        finesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        finesTable.setBackground(primaryBgColor);
        finesTable.setRowHeight(35);
        finesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(finesTable);
        scrollPane.getViewport().setBackground(primaryBgColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        mainContentWrapper.add(contentPanel, BorderLayout.CENTER);
        this.add(mainContentWrapper, BorderLayout.CENTER);
    }

    private void setupFinesTable() {
        JTableHeader tHeader = finesTable.getTableHeader();
        tHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tHeader.setBackground(new Color(220, 220, 220));
        tHeader.setForeground(headerTextColor);
        tHeader.setOpaque(true);
        tHeader.setReorderingAllowed(false);
        tHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setOpaque(true);
                label.setBackground(new Color(220, 220, 220));
                label.setForeground(headerTextColor);
                label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                label.setHorizontalAlignment(CENTER);
                return label;
            }
        });

        finesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? primaryBgColor : new Color(230, 230, 230)));
                c.setForeground(Color.BLACK);
                ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < finesTable.getColumnCount(); i++) {
            if (finesTable.getColumnModel().getColumn(i).getHeaderValue().toString().toLowerCase().contains("date")) {
                finesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof Date) {
                            value = dateFormat.format(value);
                        }
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? primaryBgColor : new Color(230, 230, 230)));
                        c.setForeground(Color.BLACK);
                        ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                        return c;
                    }
                });
            }
        }
        
        finesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        finesTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        finesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        finesTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        finesTable.getColumnModel().getColumn(7).setPreferredWidth(100);
    }


    private JButton createStyledButton(String text, Color bgColor, Icon icon, Font font, Insets paddingInsets) {
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
        button.setBorder(new EmptyBorder(paddingInsets.top, paddingInsets.left, paddingInsets.bottom, paddingInsets.right));
        button.setMargin(new Insets(0,0,0,0));
        return button;
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

    private void addListeners() {
        btnRefresh.addActionListener(this::btnRefreshActionPerformed);
        btnMarkAsPaid.addActionListener(this::btnMarkAsPaidActionPerformed);
        btnSearch.addActionListener(this::btnSearchActionPerformed);

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                refreshFinesTable();
            }
        });

        cmbStatusFilter.addActionListener(this::cmbStatusFilterActionPerformed);

        btnIssueFine.addActionListener(e -> {
            if (controller != null && controller.getModel() != null) {
                Window ownerWindow = SwingUtilities.getWindowAncestor(this);
                UserMemberDAO userMemberDAO = new UserMemberDAO();
                IssueFineDialog issueFineDialog = new IssueFineDialog(ownerWindow, controller.getModel(), userMemberDAO);
                issueFineDialog.setVisible(true);
                if (issueFineDialog.isFineSaved()) {
                    refreshFinesTable();
                }
            } else {
                displayMessage("Controller or FineDAO is not initialized. Cannot issue fine.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btnDeleteFine.addActionListener(e -> {
            int selectedRow = finesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String fineIDStr = finesTableModel.getValueAt(selectedRow, finesTable.getColumnModel().getColumnIndex("Fine ID")).toString();
                int fineID = -1;
                try {
                    fineID = Integer.parseInt(fineIDStr);
                } catch (NumberFormatException ex) {
                    displayMessage("Invalid Fine ID format in table.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to delete fine ID " + fineID + "? This action cannot be undone.",
                        "Confirm Deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.deleteFine(fineID);
                    refreshFinesTable();
                }
            } else {
                displayMessage("Please select a fine from the table to delete.", "No Fine Selected", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void btnRefreshActionPerformed(ActionEvent evt) {
        refreshFinesTable();
    }

    private void btnSearchActionPerformed(ActionEvent evt) {
        refreshFinesTable();
    }

    private void btnMarkAsPaidActionPerformed(ActionEvent evt) {
        int selectedRow = finesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String fineIDStr = finesTableModel.getValueAt(selectedRow, finesTable.getColumnModel().getColumnIndex("Fine ID")).toString();

            int fineID = -1;
            try {
                fineID = Integer.parseInt(fineIDStr);
            } catch (NumberFormatException ex) {
                displayMessage("Invalid Fine ID format in table.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to mark fine ID " + fineID + " as paid?",
                    "Confirm Payment", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.payFine(fineID);
                refreshFinesTable();
            }
        } else {
            displayMessage("Please select a fine from the table to mark as paid.", "No Fine Selected", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void cmbStatusFilterActionPerformed(ActionEvent evt) {
        refreshFinesTable();
    }

    public void refreshFinesTable() {
        if (controller != null) {
            String searchTerm = txtSearch.getText();
            String statusFilter = (String) cmbStatusFilter.getSelectedItem();
            
            if ("All Status".equals(statusFilter)) {
                statusFilter = "All";
            }
            
            controller.displayFines(finesTableModel, searchTerm, statusFilter);
        }
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
    
    public void loadFines() {
        refreshFinesTable();
    }
}