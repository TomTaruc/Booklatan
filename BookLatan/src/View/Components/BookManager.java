package View.Components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class BookManager extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    public ArrayList<JTextField> fields = new ArrayList<>();
    public BorderlessTable booksTable;
    public DefaultTableModel booksTableModel;
    public JTextField searchBar;
    public CustomButton addBtn, updateBtn, deleteBtn;

    public BookManager(Dimension size) {
        initComponent(size);
    }

    private void initComponent(Dimension size) {
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);

        // Header
        HeaderPanel header = new HeaderPanel(new Dimension(size.width, 150));
        header.setTitle("Manage Books");
        header.setSubtitle("Add, Update, and Delete Books");
        addBtn = new CustomButton("Add Book");
        addBtn.setPrimaryColor(new Color(168, 213, 186));
        addBtn.setHoverColor(new Color(148, 193, 166));
        header.add(Box.createHorizontalGlue());
        header.add(addBtn);
        this.add(header);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 80));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(primaryColor);

        searchBar = new JTextField("Search book");
        searchBar.setFont(primaryFont);
        searchBar.setPreferredSize(new Dimension(size.width-150, 40));
        searchBar.setMaximumSize(searchBar.getPreferredSize());
        searchPanel.add(searchBar);
        searchPanel.add(Box.createHorizontalStrut(10));
        // Add more filters if needed
        this.add(searchPanel);

        // Table
        booksTable = new BorderlessTable();
        String[] columnNames = {"#", "Title", "Authors", "Publisher", "Category", "Status", "Shelf Location"};
        booksTableModel = new DefaultTableModel(null, columnNames);
        booksTable.changeModel(booksTableModel);
        booksTable.getColumnModel().getColumn(0).setMaxWidth(60);
        booksTable.getColumnModel().getColumn(0).setMinWidth(40);

        // Hard-coded data
        booksTableModel.addRow(new Object[]{1, "Java Programming", "John Doe", "Pearson", "Programming", "Available", "A1"});
        booksTableModel.addRow(new Object[]{2, "Database Systems", "Jane Smith", "McGraw-Hill", "Databases", "Borrowed", "B2"});
        booksTableModel.addRow(new Object[]{3, "Software Engineering", "Alice Brown", "O'Reilly", "Engineering", "Reserved", "C3"});

        ModernScrollPane scrollPane = new ModernScrollPane(booksTable);
        scrollPane.setBackground(primaryColor);
        scrollPane.getViewport().setBackground(primaryColor);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 20, 30, 20),
            BorderFactory.createLineBorder(Color.black)
        ));

        JPanel tableWrapper = new JPanel();
        tableWrapper.setLayout(new BoxLayout(tableWrapper, BoxLayout.Y_AXIS));
        tableWrapper.setOpaque(false);
        tableWrapper.add(scrollPane);
        this.add(tableWrapper);

        // Details panel for book info
        JPanel bookDetails = new JPanel();
        bookDetails.setPreferredSize(new Dimension(700, 300));
        bookDetails.setMaximumSize(new Dimension(1000, 300));
        bookDetails.setLayout(new BoxLayout(bookDetails, BoxLayout.Y_AXIS));
        bookDetails.setAlignmentY(Component.TOP_ALIGNMENT);
        bookDetails.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        bookDetails.setBackground(primaryColor);

        String[] labelNames = {"Title", "Authors", "Publisher", "Category", "Status", "Shelf Location"};
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

            bookDetails.add(label);
            bookDetails.add(Box.createVerticalStrut(10));
            bookDetails.add(field);
            bookDetails.add(Box.createVerticalStrut(15));
        }
        this.add(bookDetails);

        // Add, Update, Delete buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(primaryColor);
        updateBtn = new CustomButton("Update Book");
        updateBtn.setPrimaryColor(new Color(168, 213, 186));
        updateBtn.setHoverColor(new Color(148, 193, 166));
        deleteBtn = new CustomButton("Delete Book");
        deleteBtn.setPrimaryColor(new Color(168, 213, 186));
        deleteBtn.setHoverColor(new Color(148, 193, 166));
        buttonPanel.add(updateBtn);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(deleteBtn);
        this.add(buttonPanel);
    }
} 