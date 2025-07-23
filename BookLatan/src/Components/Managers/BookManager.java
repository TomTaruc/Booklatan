/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Components.Managers;

/*
 * @Author Dinel
 */

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import Model.User;
import Components.Designs.BorderlessTable;
import Components.Designs.CustomButton;

public class BookManager extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Segoe UI", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    public ArrayList<JTextField> fields = new ArrayList<>();
    public BorderlessTable booksTable;
    public DefaultTableModel booksTableModel;
    public JTextField searchBar;
    public CustomButton addBtn, updateBtn, deleteBtn;
    private boolean isEditable = true;
    private User.UserType userType;

    // Filters for all roles
    private JComboBox<String> statusFilter, categoryFilter;

    public BookManager(Dimension size, User.UserType userType) {
        this.userType = userType;
        this.isEditable = (userType == User.UserType.ADMIN || userType == User.UserType.LIBRARIAN);
        initComponent(size);
    }

    public BookManager(Dimension size) {
        this(size, User.UserType.ADMIN); // default to admin for backward compatibility
    }

    private void initComponent(Dimension size) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Header Panel (Search & Filters) ---
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);

        JLabel title = new JLabel("Search & Filter Books");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        searchBar = new JTextField();
        searchBar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        searchBar.setPreferredSize(new Dimension(400, 40));
        searchBar.setText("");

        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.X_AXIS));
        filterPanel.setOpaque(false);

        statusFilter = new JComboBox<>(new String[]{"All Statuses", "Available", "Reserved", "Checked Out"});
        categoryFilter = new JComboBox<>(new String[]{"All Categories", "Fiction", "Technology", "Engineering"});
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        categoryFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statusFilter.setMaximumSize(new Dimension(200, 35));
        categoryFilter.setMaximumSize(new Dimension(200, 35));
        filterPanel.add(statusFilter);
        filterPanel.add(Box.createHorizontalStrut(10));
        filterPanel.add(categoryFilter);
        filterPanel.add(Box.createHorizontalStrut(10));
        // Add Publisher ID filter
        JLabel pubIdLabel = new JLabel("Publisher ID:");
        pubIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField pubIdField = new JTextField();
        pubIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pubIdField.setMaximumSize(new Dimension(120, 35));
        pubIdField.setPreferredSize(new Dimension(120, 35));
        filterPanel.add(pubIdLabel);
        filterPanel.add(Box.createHorizontalStrut(5));
        filterPanel.add(pubIdField);
        filterPanel.add(Box.createHorizontalStrut(10));
        // Add Library ID filter
        JLabel libIdLabel = new JLabel("Library ID:");
        libIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JTextField libIdField = new JTextField();
        libIdField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        libIdField.setMaximumSize(new Dimension(120, 35));
        libIdField.setPreferredSize(new Dimension(120, 35));
        filterPanel.add(libIdLabel);
        filterPanel.add(Box.createHorizontalStrut(5));
        filterPanel.add(libIdField);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(searchBar);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(filterPanel);

        add(headerPanel, BorderLayout.NORTH);

        // --- Main Content: Table (left) + Details (right) ---
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setOpaque(false);
        mainPanel.setBackground(primaryColor);

        // --- Table Panel (left) ---
        JPanel tableWrapper = new JPanel();
        tableWrapper.setLayout(new BoxLayout(tableWrapper, BoxLayout.Y_AXIS));
        tableWrapper.setOpaque(false);
        tableWrapper.setAlignmentY(Component.TOP_ALIGNMENT);
        tableWrapper.setPreferredSize(new Dimension((int)(size.width * 0.55), size.height));
        tableWrapper.setMaximumSize(new Dimension((int)(size.width * 0.65), size.height));

        String[] columnNames = {"#", "Title", "Authors", "Publisher", "Category", "Status", "Shelf Location"};
        Object[][] data = {
            {"BK010", "1984", "George Orwell", "Secker & Warburg", "Fiction", "Reserved", "A-FIC-019"},
            {"BK004", "Clean Code", "Robert C. Martin", "Prentice Hall", "Technology", "Reserved", "B-TECH-052"},
            {"BK007", "Design Patterns", "Erich Gamma, Richard Helm, Ralph Johnson, John Vlissides", "Addison-Wesley", "Technology", "Available", "B-TECH-067"},
            {"BK003", "Java: The Complete Reference", "Herbert Schildt", "McGraw-Hill Education", "Technology", "Available", "B-TECH-045"},
            {"BK008", "Pride and Prejudice", "Jane Austen", "T. Egerton", "Fiction", "Checked Out", "A-FIC-015"}
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(model);
        table.setRowHeight(36);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.setFillsViewportHeight(true);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(Color.BLACK);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 30, 20),
            BorderFactory.createLineBorder(Color.black)
        ));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBackground(primaryColor);
        scrollPane.getViewport().setBackground(primaryColor);
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
        detailsPanel.setBackground(primaryColor);

        String[] labelNames = {"Title", "Authors", "Publisher", "Category", "Status", "Shelf Location", "Publisher ID", "Library ID"};
        for (String labelName : labelNames) {
            JLabel label = new JLabel(labelName + ": ");
            label.setFont(primaryFont.deriveFont(Font.BOLD));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            labels.add(label);

            JTextField field = new JTextField();
            field.setName(labelName);
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        buttonPanel.setBackground(primaryColor);
        addBtn = new CustomButton("Add Book");
        updateBtn = new CustomButton("Update Book");
        deleteBtn = new CustomButton("Delete Book");
        addBtn.setPrimaryColor(new Color(168, 213, 186));
        addBtn.setHoverColor(new Color(148, 193, 166));
        updateBtn.setPrimaryColor(new Color(168, 213, 186));
        updateBtn.setHoverColor(new Color(148, 193, 166));
        deleteBtn.setPrimaryColor(new Color(168, 213, 186));
        deleteBtn.setHoverColor(new Color(148, 193, 166));
        addBtn.setVisible(isEditable);
        updateBtn.setVisible(isEditable);
        deleteBtn.setVisible(isEditable);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(updateBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteBtn);
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
                case "Available":
                    label.setBackground(new Color(212, 237, 218));
                    label.setForeground(new Color(40, 167, 69));
                    break;
                case "Reserved":
                    label.setBackground(new Color(255, 243, 205));
                    label.setForeground(new Color(255, 193, 7));
                    break;
                case "Checked Out":
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