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
import Components.Designs.CustomComboBox;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import Utilities.Design;

public class BookManager extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private Color textColor = Color.black;
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    public ArrayList<JTextField> fields = new ArrayList<>();
    public BorderlessTable booksTable;
    public DefaultTableModel booksTableModel;
    public JTextField searchBar;
    public CustomButton addBtn, updateBtn, deleteBtn;
    private boolean isEditable = true;
    private User.UserType userType;

    // Filters for all roles
    public JComboBox<String> statusFilter, categoryFilter;

    public BookManager(Dimension size, User.UserType userType) {
        this.userType = userType;
        this.isEditable = (userType == User.UserType.ADMIN || userType == User.UserType.LIBRARIAN);
        initComponent(size);
    }

    public BookManager(Dimension size) {
        this(size, User.UserType.ADMIN); // default to admin for backward compatibility
    }

    private void initComponent(Dimension size) {
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);
        
        //Header
        HeaderPanel header = new HeaderPanel(new Dimension(size.width, 150));
        header.setTitle("Manage Books");
        header.setSubtitle("Add, Update, and Delete Books");

        addBtn = new CustomButton("Add Book");
        addBtn.setPrimaryColor(Design.BTN1);
        addBtn.setHoverColor(Design.BTN1_HOVER);
        addBtn.setVisible(isEditable);
        
        header.add(Box.createHorizontalGlue());
        header.add(addBtn);
        
        this.add(header);

        // Search Panel -- Setup
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 150));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(primaryColor);
        
        // Search Panel -- Search Bar
        searchBar = new JTextField("Search books");
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black), 
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchBar.setFont(primaryFont);
        searchBar.setPreferredSize(new Dimension(size.width-150, 50));
        searchBar.setMaximumSize(searchBar.getPreferredSize());
        searchBar.setForeground(Color.LIGHT_GRAY);
        Design.addSearchEffect(searchBar, "Search books");
        
        // Search Panel -- Filters
        statusFilter = new JComboBox<>(new String[]{"Status", "Available", "Reserved", "Not Available", "Loaned"});
        categoryFilter = new JComboBox<>(new String[]{"Category", "Fiction", "Non-Fiction", "Poetry", "Drama", "Mythology", "Educational"});
        statusFilter.setFont(primaryFont);
        categoryFilter.setFont(primaryFont);
        statusFilter.setPreferredSize(new Dimension(200, 50));
        categoryFilter.setPreferredSize(new Dimension(200, 50));
        statusFilter.setMaximumSize(statusFilter.getPreferredSize());
        categoryFilter.setMaximumSize(categoryFilter.getPreferredSize());
        statusFilter.setBorder(BorderFactory.createLineBorder(Color.black));
        categoryFilter.setBorder(BorderFactory.createLineBorder(Color.black));
        statusFilter.setUI(new CustomComboBox());
        categoryFilter.setUI(new CustomComboBox());
        
        statusFilter.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if(!isSelected) {
                    label.setBackground(primaryColor);
                    label.setForeground(Color.BLACK);
                }
                return label;   
        }});
        
        categoryFilter.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                if(!isSelected) {
                    label.setBackground(primaryColor);
                    label.setForeground(Color.BLACK);
                }
                return label;   
        }});
        
        searchPanel.add(searchBar);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(statusFilter);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(categoryFilter);
        
        this.add(searchPanel);
        
        //Manage Books -- Setup
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());
        main.setBackground(primaryColor);
        
        //Manage Books -- table (Bigger table on the left)
        JPanel tableWrapper;
        ModernScrollPane scrollPane;
        String[] columnNames;
        
        booksTable = new BorderlessTable();
        columnNames = new String[]{"#", "Title", "Authors", "Publisher", "Category", "Status"};
        booksTableModel = new DefaultTableModel(null, columnNames);
        booksTable.changeModel(booksTableModel);
        
        // Set column widths for better visibility of all book information
        booksTable.getColumnModel().getColumn(0).setMaxWidth(80);   // #
        booksTable.getColumnModel().getColumn(0).setMinWidth(60);
        booksTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Title
        booksTable.getColumnModel().getColumn(2).setPreferredWidth(150); // Authors
        booksTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Publisher
        booksTable.getColumnModel().getColumn(4).setPreferredWidth(100); // Category
        booksTable.getColumnModel().getColumn(5).setPreferredWidth(100); // Status
        
        // Apply status renderer to the Status column (column 5)
        booksTable.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());

 
        scrollPane = new ModernScrollPane(booksTable);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBackground(primaryColor);
        scrollPane.getViewport().setBackground(primaryColor);
        scrollPane.setViewportBorder(null);
        scrollPane.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(15, 25, 35, 25), BorderFactory.createLineBorder(Color.black)));
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);

        tableWrapper = new JPanel();
        tableWrapper.setLayout(new BorderLayout());
        tableWrapper.setOpaque(false);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        // **** Book Details **** (Smaller details panel on the right)
        JPanel bookDetails;
        String[] labelNames;
        
        bookDetails = new JPanel();
        bookDetails.setPreferredSize(new Dimension(400, this.getPreferredSize().height));
        bookDetails.setMaximumSize(new Dimension(450, this.getPreferredSize().height));
        bookDetails.setLayout(new BoxLayout(bookDetails, BoxLayout.Y_AXIS));
        bookDetails.setAlignmentY(Component.TOP_ALIGNMENT);
        bookDetails.setBorder(BorderFactory.createEmptyBorder(30, 20, 10, 20));
        bookDetails.setBackground(primaryColor);
        
        labelNames = new String[]{"Title", "Authors", "Publisher", "Category", "Status", "Shelf Location", "Publisher ID", "Library ID"};

        // Creates textfields and labels for the display and update of book details
        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i] + ": ");
            label.setFont(primaryFont.deriveFont(Font.BOLD));
            label.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to left
            labels.add(label);

            JTextField field = new JTextField();
            field.setName(labelNames[i]);
            field.setMaximumSize(new Dimension(350, 40)); // Constrained width for smaller panel
            field.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to left
            field.setFont(primaryFont);
            field.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
            fields.add(field);

            bookDetails.add(label);
            bookDetails.add(Box.createVerticalStrut(8));
            bookDetails.add(field);
            bookDetails.add(Box.createVerticalStrut(12));
        }
        
        JPanel btnsHolder = new JPanel();
        btnsHolder.setPreferredSize(new Dimension(350, 80));
        btnsHolder.setMaximumSize(new Dimension(350, 80));
        btnsHolder.setLayout(new BoxLayout(btnsHolder, BoxLayout.X_AXIS));
        btnsHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnsHolder.setBackground(primaryColor);
        btnsHolder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        updateBtn = new CustomButton("Update");
        updateBtn.setPrimaryColor(Design.BTN2);
        updateBtn.setHoverColor(Design.BTN2_HOVER);
        updateBtn.setVisible(isEditable);
        
        deleteBtn = new CustomButton("Delete");
        deleteBtn.setPrimaryColor(Design.BTN3);
        deleteBtn.setHoverColor(Design.BTN3_HOVER);
        deleteBtn.setVisible(isEditable);
        
        btnsHolder.add(Box.createHorizontalGlue());
        btnsHolder.add(updateBtn);
        btnsHolder.add(Box.createHorizontalStrut(15));
        btnsHolder.add(deleteBtn);
        bookDetails.add(btnsHolder);
        
        // Add panels to main with BorderLayout for better space distribution
        main.add(tableWrapper, BorderLayout.CENTER);
        main.add(bookDetails, BorderLayout.EAST);
        this.add(main);
    }

    // Custom renderer for status badges (for all roles)
    static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                      boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setOpaque(true);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(new Font("Tahoma", Font.BOLD, 13));
            switch (label.getText()) {
                case "Available":
                    label.setBackground(new Color(212, 237, 218));
                    label.setForeground(new Color(40, 167, 69));
                    break;
                case "Reserved":
                    label.setBackground(new Color(255, 243, 205));
                    label.setForeground(new Color(255, 193, 7));
                    break;
                case "Not Available":
                    label.setBackground(new Color(248, 215, 218));
                    label.setForeground(new Color(220, 53, 69));
                    break;
                case "Loaned":
                    label.setBackground(new Color(209, 236, 241));
                    label.setForeground(new Color(23, 162, 184));
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