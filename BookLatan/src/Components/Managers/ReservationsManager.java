/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Components.Managers;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import Model.User;
import Components.Designs.BorderlessTable;
import Components.Designs.CustomButton;
import Components.Designs.CustomComboBox;
import Utilities.Design;

public class ReservationsManager extends JPanel {
    private Font primaryFont = new Font("Segoe UI", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    public ArrayList<JTextField> fields = new ArrayList<>();
    public BorderlessTable reservationsTable;
    public DefaultTableModel reservationsTableModel;
    public JTextField searchBar;
    public CustomButton addBtn, claimBtn, cancelBtn;
    public Color btnPrimaryColor = new Color(168, 213, 186);
    public Color btnSecondaryColor = new Color(168, 213, 186);
    public Color disabledColor = Color.GRAY;
    private boolean isEditable = true;

    // Filters for all roles
    public JComboBox<String> statusFilter;

    public ReservationsManager(Dimension size, User.UserType userType) {
        this.isEditable = (userType == User.UserType.LIBRARIAN);
        initComponent(size, userType);
    }

    public ReservationsManager(Dimension size) {
        this(size, User.UserType.LIBRARIAN); 
    }

    private void initComponent(Dimension size, User.UserType userType) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Header Panel (Search & Filters) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(Design.PRIME_COLOR);

        JLabel title = new JLabel("Search & Filter Reservations");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchBar = new JTextField();
        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchBar.setPreferredSize(new Dimension(400, 40));
        searchBar.setText("");

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setBackground(Design.PRIME_COLOR);

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Pending", "Claimed", "Cancelled"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 50));
        statusFilter.setMaximumSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 50));
        statusFilter.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusFilter.setUI(new CustomComboBox());
        filterPanel.add(statusFilter);
        filterPanel.add(Box.createVerticalStrut(10));

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(searchBar);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(filterPanel);

        add(headerPanel, BorderLayout.NORTH);

        // --- Main Content: Table (left) + Details (right) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setBackground(Design.PRIME_COLOR);

        // --- Table Panel (left) ---
        JPanel tableWrapper = new JPanel();
        tableWrapper.setLayout(new BoxLayout(tableWrapper, BoxLayout.Y_AXIS));
        tableWrapper.setBackground(Design.PRIME_COLOR);
        tableWrapper.setAlignmentY(Component.TOP_ALIGNMENT);
        tableWrapper.setPreferredSize(new Dimension((int)(size.width * 0.55), size.height));
        tableWrapper.setMaximumSize(new Dimension((int)(size.width * 0.65), size.height));

        String[] columnNames = {"Reservation ID", "Member Name", "Book Title", "Date Reserved", "Status"};
        Object[][] data = {
            {"RES001", "Kaiser Lycan", "1984", "2024-07-20", "Pending"},
            {"RES002", "Alice Love", "Clean Code", "2024-07-21", "Claimed"},
            {"RES003", "Dinel Christian P. Robles", "Design Patterns", "2024-07-22", "Pending"},
            {"RES004", "Khryzna Advincula", "Java: The Complete Reference", "2024-07-23", "Canceled"},
            {"RES005", "Kaiser Lycan", "Pride and Prejudice", "2024-07-24", "Pending"}
        };
        reservationsTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        reservationsTable = new BorderlessTable();
        reservationsTable.changeModel(reservationsTableModel);
        reservationsTable.setRowHeight(36);
        reservationsTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        reservationsTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        reservationsTable.setFillsViewportHeight(true);
        reservationsTable.setShowGrid(false);
        reservationsTable.setIntercellSpacing(new Dimension(0, 0));
        reservationsTable.setSelectionBackground(new Color(232, 240, 254));
        reservationsTable.setSelectionForeground(Color.BLACK);
        reservationsTable.getTableHeader().setReorderingAllowed(false);
        reservationsTable.getTableHeader().setResizingAllowed(false);
        reservationsTable.getColumnModel().getColumn(2).setCellRenderer(new StatusBadgeRenderer());

        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 30, 20),
            BorderFactory.createLineBorder(Color.black)
        ));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBackground(Design.PRIME_COLOR);
        scrollPane.getViewport().setBackground(Design.PRIME_COLOR);
        tableWrapper.add(scrollPane);

        mainPanel.add(tableWrapper);
        mainPanel.add(Box.createHorizontalStrut(30));

        // --- Details Panel (right) ---
        JPanel detailsPanel = new JPanel();
        detailsPanel.setPreferredSize(new Dimension(500, size.height));
        detailsPanel.setMaximumSize(new Dimension(700, size.height));
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        detailsPanel.setBackground(Design.PRIME_COLOR);

        String[] labelNames = {"Reservation ID", "Date Reserved", "Status", "Member Name", "Member ID", "Book Title"};
        for (String labelName : labelNames) {
            JLabel label = new JLabel(labelName + ": ");
            label.setFont(primaryFont.deriveFont(Font.BOLD));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            labels.add(label);

            JTextField field = new JTextField();
            field.setName(labelName);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            field.setAlignmentX(Component.CENTER_ALIGNMENT);
            field.setFont(primaryFont);
            field.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
            fields.add(field);

            detailsPanel.add(label);
            detailsPanel.add(Box.createVerticalStrut(10));
            detailsPanel.add(field);
            detailsPanel.add(Box.createVerticalStrut(15));
        }

        // --- Button Panel (only visible for admin/librarian) ---
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Design.PRIME_COLOR);
        addBtn = new CustomButton("Add Reservation");
        cancelBtn = new CustomButton("Cancel Reservation");
        claimBtn = new CustomButton("Mark as Claimed");
        
        addBtn.setPrimaryColor(btnPrimaryColor);
        addBtn.setHoverColor(btnSecondaryColor);
        claimBtn.setPrimaryColor(btnPrimaryColor);
        claimBtn.setHoverColor(btnSecondaryColor);
        cancelBtn.setPrimaryColor(btnPrimaryColor);
        cancelBtn.setHoverColor(btnSecondaryColor);
        
        // Configure button visibility based on user type
        if (userType == User.UserType.MEMBER) {
            addBtn.setVisible(true);
            addBtn.setText("New Reservation");
            claimBtn.setVisible(false); // Members can't claim reservations
            cancelBtn.setVisible(true);
            cancelBtn.setText("Cancel Reservation");
        } else {
            addBtn.setVisible(isEditable);
            claimBtn.setVisible(isEditable);
            cancelBtn.setVisible(isEditable);
        }
        
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addBtn);
        if (claimBtn.isVisible()) {
            buttonPanel.add(Box.createHorizontalStrut(10));
            buttonPanel.add(claimBtn);
        }
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelBtn);
        buttonPanel.add(Box.createHorizontalGlue());
        detailsPanel.add(Box.createVerticalStrut(20));
        detailsPanel.add(buttonPanel);

        mainPanel.add(detailsPanel);
        add(mainPanel, BorderLayout.CENTER);
    }

    // Custom renderer for status badges (for all roles)
    static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                      boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Segoe UI", Font.BOLD, 13));
            switch (label.getText()) {
                case "Pending":
                    label.setBackground(new Color(255, 243, 205));
                    label.setForeground(new Color(255, 193, 7));
                    break;
                case "Claimed":
                    label.setBackground(new Color(212, 237, 218));
                    label.setForeground(new Color(40, 167, 69));
                    break;
                case "Cancelled":
                    label.setBackground(new Color(248, 215, 218));
                    label.setForeground(new Color(220, 53, 69));
                    break;
                default:
                    label.setBackground(Color.LIGHT_GRAY);
                    label.setForeground(Color.DARK_GRAY);
            }
            label.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
            return label;
        }
    }
}
