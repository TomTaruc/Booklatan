package View.Components;

import Control.Components.FinesController;
import Model.FineDAO;
import Views.IssueFineDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    private Color primaryBgColor = new Color(253, 245, 230);
    private Color headerTextColor = Color.WHITE;

    public FinesPanel() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setBackground(primaryBgColor);

        initComponents();
        initTable();
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

        btnIssueFine = createStyledButton(" Issue Fine", new Color(160, 82, 45), createUnicodeIcon("\uF02B"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));
        btnMarkAsPaid = createStyledButton(" Mark as Paid", new Color(160, 82, 45), createUnicodeIcon("\uF00C"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));
        btnDeleteFine = createStyledButton(" Delete Fine", new Color(160, 82, 45), createUnicodeIcon("\uF014"), new Font("Segoe UI", Font.BOLD, 18), new Insets(12, 25, 12, 25));

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
        txtSearch.putClientProperty("JTextField.placeholderText", "Search by Member/Book/ISBN");
        txtSearch.setBorder(new EmptyBorder(5, 8, 5, 8));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchFilterPanel.add(txtSearch);

        btnSearch = createStyledButton(" Search", new Color(205, 133, 63), createUnicodeIcon("\uF002"), new Font("Segoe UI", Font.BOLD, 14), new Insets(10, 20, 10, 20));
        searchFilterPanel.add(btnSearch);

        searchFilterPanel.add(new JLabel("Filter by Status:"));
        cmbStatusFilter = new JComboBox<>(new String[]{"All Status", "Pending", "Paid", "Overdue"});
        cmbStatusFilter.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        searchFilterPanel.add(cmbStatusFilter);

        btnRefresh = createStyledButton(" Refresh", new Color(205, 133, 63), createUnicodeIcon("\uF021"), new Font("Segoe UI", Font.BOLD, 12), new Insets(8, 15, 8, 15));
        searchFilterPanel.add(btnRefresh);
        
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(searchFilterPanel);
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalStrut(10));

        finesTableModel = new DefaultTableModel(new Object[]{
                "Fine ID", "Staff ID", "Member ID", "Member Name", "Amount", "Reason",
                "Date Issued", "Status", "Book Title", "Due Date", "Return Date",
                "Days Overdue", "ISBN", "Paid Date", "Description"
        }, 0) {
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

        JTableHeader tHeader = finesTable.getTableHeader();
        tHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tHeader.setBackground(new Color(230, 230, 230));
        tHeader.setForeground(Color.BLACK);
        tHeader.setOpaque(true);
        tHeader.setReorderingAllowed(false);
        tHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setOpaque(true);
                label.setBackground(new Color(230, 230, 230));
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                label.setHorizontalAlignment(CENTER);
                return label;
            }
        });

        finesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? primaryBgColor : new Color(240, 240, 240)));
                c.setForeground(Color.BLACK);
                ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < finesTable.getColumnCount(); i++) {
            if (finesTableModel.getColumnClass(i) == Date.class || finesTableModel.getColumnName(i).toLowerCase().contains("date")) {
                 finesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof Date) {
                            value = dateFormat.format(value);
                        }
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? primaryBgColor : new Color(240, 240, 240)));
                        c.setForeground(Color.BLACK);
                        ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                        return c;
                    }
                });
            }
        }

        JScrollPane scrollPane = new JScrollPane(finesTable);
        scrollPane.getViewport().setBackground(primaryBgColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        contentPanel.add(scrollPane);

        mainContentWrapper.add(contentPanel, BorderLayout.CENTER);
        this.add(mainContentWrapper, BorderLayout.CENTER);
    }

    private void initTable() {
        String[] desiredVisibleColumnNames = {
            "Fine ID", "Member Name", "Book Title", "Due Date", "Return Date",
            "Days Overdue", "Amount", "Status"
        };

        Map<String, Integer> originalColumnIndices = new HashMap<>();
        for (int i = 0; i < finesTableModel.getColumnCount(); i++) {
            originalColumnIndices.put(finesTableModel.getColumnName(i), i);
        }

        TableColumnModel columnModel = finesTable.getColumnModel();

        java.util.List<javax.swing.table.TableColumn> allColumns = new java.util.ArrayList<>();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            allColumns.add(columnModel.getColumn(i));
        }

        while (columnModel.getColumnCount() > 0) {
            columnModel.removeColumn(columnModel.getColumn(0));
        }

        for (String colName : desiredVisibleColumnNames) {
            Integer originalIndex = originalColumnIndices.get(colName);
            if (originalIndex != null) {
                javax.swing.table.TableColumn columnToAdd = null;
                for(javax.swing.table.TableColumn col : allColumns) {
                    if (col.getModelIndex() == originalIndex) {
                        columnToAdd = col;
                        break;
                    }
                }
                if (columnToAdd != null) {
                    columnModel.addColumn(columnToAdd);
                }
            }
        }

        finesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        finesTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        finesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        finesTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(6).setPreferredWidth(90);
        finesTable.getColumnModel().getColumn(7).setPreferredWidth(100);

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
                        c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? primaryBgColor : new Color(240, 240, 240)));
                        c.setForeground(Color.BLACK);
                        ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                        return c;
                    }
                });
            }
        }
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
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    refreshFinesTable();
                }
            }
        });

        cmbStatusFilter.addActionListener(this::cmbStatusFilterActionPerformed);

        btnIssueFine.addActionListener(e -> {
            if (controller != null && controller.getModel() != null) {
                IssueFineDialog issueFineDialog = new IssueFineDialog(SwingUtilities.getWindowAncestor(this), controller.getModel());
                issueFineDialog.setVisible(true);
                if (issueFineDialog.isFineSaved()) {
                    refreshFinesTable();
                }
            } else {
                displayMessage("Controller or FineDAO is not initialized. Cannot issue fine.", "Initialization Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDeleteFine.addActionListener(e -> displayMessage("Delete Fine functionality not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE));
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
            String fineID = (String) finesTableModel.getValueAt(selectedRow, finesTable.getColumnModel().getColumnIndex("Fine ID"));
            String currentStatus = (String) finesTableModel.getValueAt(selectedRow, finesTable.getColumnModel().getColumnIndex("Status"));

            if ("Paid".equalsIgnoreCase(currentStatus)) {
                displayMessage("This fine is already marked as Paid.", "Information", JOptionPane.INFORMATION_MESSAGE);
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
            controller.displayFines(finesTableModel, searchTerm, statusFilter);
        }
    }

    public void displayMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
