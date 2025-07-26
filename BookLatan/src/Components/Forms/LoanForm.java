/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Components.Designs.BorderlessTable;
import Utilities.Design;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import Model.User.UserType;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joseph Rey
 */
public class LoanForm extends JDialog{
    
    private HeaderPanel header;
    private JPanel mainPanel;
    private JPanel formsPanel;
    private JPanel tableHolder;
    private ModernScrollPane scroll;
    
    private JLabel dueDateLabel;
    private JLabel memberNameLabel;
    private JLabel memberIDLabel;
    
    public JSpinner dueDateField;
    public JTextField memberNameField;
    public JTextField memberIDField;
    public JTextField searchBar;
    
    public CustomButton loanBook;
    
    private String[] columnNames = {"BookID", "Title", "Categeory", "Author"};
    public BorderlessTable booksTable;
    public DefaultTableModel booksTableModel;
    private ModernScrollPane scrollBookTable;
    public BorderlessTable addedBooks;
    public DefaultTableModel addedBooksModel;
    private ModernScrollPane scrollAddedBooks;
    
    
    private UserType type;
    
    public LoanForm(UserType type) {
        this.type = type;
        initDialog();
        initComponents();
        initLayout();
        this.setVisible(true);
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width - 200, Design.FRAME_SIZE.height - 200));
        this.setIconImage(Design.appIcon);
        this.setTitle("Loan A Book");
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }
    
    private void initComponents() {
        
        scroll = new ModernScrollPane();
        scroll.setPreferredSize(new Dimension(Design.FRAME_SIZE.width - 200, Design.FRAME_SIZE.height));
        
        mainPanel = new JPanel();
        mainPanel.setPreferredSize(new Dimension(Design.FRAME_SIZE.width - 300, Design.FRAME_SIZE.height/2 + 500));
        mainPanel.setMaximumSize(mainPanel.getPreferredSize());
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        tableHolder = new JPanel();
        tableHolder.setBackground(Design.PRIME_COLOR);
        
        formsPanel = new JPanel();
        formsPanel.setPreferredSize(new Dimension(Design.FRAME_SIZE.width - 200, 300));
        formsPanel.setMaximumSize(formsPanel.getPreferredSize());
        formsPanel.setBackground(Design.PRIME_COLOR);
        
        header = new HeaderPanel(new Dimension(Design.FRAME_SIZE.width - 200, 100));
        header.setTitle("Loan A Book");
        header.setSubtitle("What books do you want to loan?");
        
        
        dueDateLabel = new JLabel("When do you plan to return the book?");
        dueDateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        SpinnerDateModel model = new SpinnerDateModel();
        dueDateField = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(dueDateField, "yyyy-MM-dd");
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        dueDateField.setEditor(editor);
        dueDateField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        dueDateField.setPreferredSize(new Dimension(dueDateField.getPreferredSize().width, 50));
        dueDateField.setBorder(BorderFactory.createEmptyBorder());
        
        memberNameLabel = new JLabel("Member Name");
        memberNameLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        memberIDLabel = new JLabel("Member ID");
        memberIDLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        
        memberNameField = new JTextField();
        memberNameField.setPreferredSize(new Dimension(memberNameField.getPreferredSize().width, 50));
        memberNameField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberNameField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        
        memberIDField = new JTextField();
        memberIDField.setPreferredSize(new Dimension(memberIDField.getPreferredSize().width, 50));
        memberIDField.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberIDField.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        memberIDField.setEditable(false);
        
        if(type == UserType.MEMBER) {
            memberNameField.setEditable(false);
        }
    
        searchBar = new JTextField();
        searchBar.setText("Search book");
        searchBar.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        searchBar.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        searchBar.setForeground(Color.LIGHT_GRAY);
        searchBar.setMinimumSize(new Dimension(100, 60));
        searchBar.setPreferredSize(new Dimension(100, 60));
        searchBar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        Design.addSearchEffect(searchBar, "Search book");
        
        loanBook = new CustomButton("Loan");
        loanBook.setPrimaryColor(Design.BTN2);
        loanBook.setHoverColor(Design.BTN2_HOVER);
        loanBook.setPreferredSize(new Dimension(loanBook.getPreferredSize().width, 50));
        loanBook.setMinimumSize(loanBook.getPreferredSize());
        
        booksTable = new BorderlessTable();
        booksTableModel = new DefaultTableModel(columnNames, 0);
        booksTable.changeModel(booksTableModel);
        scrollBookTable = new ModernScrollPane(booksTable);
        
        addedBooks = new BorderlessTable();
        addedBooksModel = new DefaultTableModel(columnNames, 0);
        addedBooks.changeModel(addedBooksModel);
        scrollAddedBooks = new ModernScrollPane(addedBooks);
        
        
    }
    
    private void initLayout() {
        
        header.add(Box.createHorizontalGlue());
        header.add(loanBook);
        
        formsPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formsPanel.add(memberNameLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridx = 2;
        gbc.gridy = 0;
        formsPanel.add(memberIDLabel, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        formsPanel.add(memberNameField, gbc);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formsPanel.add(memberIDField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        formsPanel.add(dueDateLabel, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        formsPanel.add(dueDateField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        formsPanel.add(searchBar, gbc);        
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 6;
        formsPanel.add(Box.createVerticalGlue(),gbc);
        
        
        tableHolder.setLayout(new BoxLayout(tableHolder, BoxLayout.X_AXIS));
        tableHolder.add(scrollBookTable);
        tableHolder.add(Box.createHorizontalStrut(20));
        tableHolder.add(scrollAddedBooks);
        
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(formsPanel);
        mainPanel.add(tableHolder);
        
        scroll.setViewportView(mainPanel);
        this.add(header, BorderLayout.NORTH);
        this.add(scroll);
    }
    
}
