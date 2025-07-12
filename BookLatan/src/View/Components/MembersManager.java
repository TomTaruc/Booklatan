/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import Model.Member;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.table.*;

/**
 *
 * @author Joseph Rey
 */
public class MembersManager extends JPanel{
    private Color primaryColor = new Color(245, 245, 245);
    private Color textColor = Color.black;
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JTextField> fields = new ArrayList<>();
    private BorderlessTable membersTable;
    private DefaultTableModel membersTableModel;
    private JTextField searchBar;
    private JComboBox<Member.MembershipStatus> filterStatus;
    public CustomButton registerBtn;
    
    public MembersManager(Dimension size) {
        initComponent(size);
    }
    
    private void initComponent(Dimension size) {
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);
        
        //Header
        HeaderPanel header = new HeaderPanel(new Dimension(size.width, 150));
        header.setTitle("Manage Memebers");
        header.setSubtitle("Add, Update, and Delete Members");

        registerBtn = new CustomButton("Register Member");
        registerBtn.setPrimaryColor(new Color(168, 213, 186));
        registerBtn.setHoverColor(new Color(148, 193, 166));
        
        header.add(Box.createHorizontalGlue());
        header.add(registerBtn);
        
        //Reminder: Move this down later - joseph
        this.add(header);

       
        // Search Panel -- Setup
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 150));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(primaryColor);
        
        // Search Panel -- Search Bar
        searchBar = new JTextField("Search member");
        searchBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black), 
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchBar.setFont(primaryFont);
        searchBar.setPreferredSize(new Dimension(size.width-150, 50));
        searchBar.setMaximumSize(searchBar.getPreferredSize());
        searchBar.setForeground(Color.LIGHT_GRAY);
        searchBar.addFocusListener(new SearchEffect());
        
        
        // Search Panel -- Filters
        filterStatus = new JComboBox<>(Member.MembershipStatus.values());
        filterStatus.setFont(primaryFont);
        filterStatus.setPreferredSize(new Dimension(200, 50));
        filterStatus.setMaximumSize(filterStatus.getPreferredSize());
        filterStatus.setBorder(BorderFactory.createLineBorder(Color.black));
        filterStatus.setUI(new BasicComboBoxUI() {
            
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBackground(primaryColor);
                return button;
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = (BasicComboPopup) super.createPopup();
                popup.getList().setBackground(primaryColor); // dropdown background
                popup.getList().setForeground(Color.BLACK); // dropdown text
                return popup;
            }
        });
        
        filterStatus.setRenderer(new DefaultListCellRenderer() {
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
        searchPanel.add(filterStatus);
        
        this.add(searchPanel);
        
        //Manage Membbers -- Setup
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
        
        //Manage Members -- table;
        JPanel tableWrapper;
        ModernScrollPane scrollPane;
        String[] columnNames;
        
        membersTable = new BorderlessTable();
        columnNames = new String[]{"#", "Name", "Status", "Email", "Date Joined"};
        membersTableModel = new DefaultTableModel(null, columnNames);
        membersTable.changeModel(membersTableModel);
        membersTable.getColumnModel().getColumn(0).setMaxWidth(200);
        membersTable.getColumnModel().getColumn(0).setMinWidth(100);
        
        scrollPane = new ModernScrollPane(membersTable);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setBackground(primaryColor);
        scrollPane.getViewport().setBackground(primaryColor);
        scrollPane.setViewportBorder(null);
        scrollPane.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder(10, 20, 30, 20), BorderFactory.createLineBorder(Color.black)));
        scrollPane.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);

        
        tableWrapper = new JPanel();
        tableWrapper.setLayout(new BoxLayout(tableWrapper, BoxLayout.Y_AXIS));
        tableWrapper.setOpaque(false);
        tableWrapper.setAlignmentY(Component.TOP_ALIGNMENT);
        tableWrapper.add(scrollPane);


        // **** Members Details ****
        JPanel memberDetails;
        String[] labelNames;
        CustomButton updateBtn;
        CustomButton deleteBtn;
        
        memberDetails = new JPanel();
        memberDetails.setPreferredSize(new Dimension(700, this.getPreferredSize().height));
        memberDetails.setMaximumSize(new Dimension(1000, this.getPreferredSize().height));
        memberDetails.setLayout(new BoxLayout(memberDetails, BoxLayout.Y_AXIS));
        memberDetails.setAlignmentY(Component.TOP_ALIGNMENT);
        memberDetails.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        memberDetails.setBackground(primaryColor);
        

        labelNames = new String[]{"Name", "Username", "Status", "Email:", "Phone", "Address", "Date Joined"};

        // Creates textfields and labels for the display and update of member details
        for (int i = 0; i < labelNames.length; i++) {
            JLabel label = new JLabel(labelNames[i] + ": ");
            label.setFont(new Font("Tahoma", Font.BOLD, 16));
            label.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to left
            labels.add(label);

            JTextField field = new JTextField();
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Full width
            field.setAlignmentX(Component.LEFT_ALIGNMENT); // Align to left
            field.setFont(new Font("Tahoma", Font.PLAIN, 16));
            field.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
            fields.add(field);

            memberDetails.add(label);
            memberDetails.add(Box.createVerticalStrut(10));
            memberDetails.add(field);
            memberDetails.add(Box.createVerticalStrut(15));
        }
        
        fields.get(6).setEditable(false);
        fields.get(6).setBackground(primaryColor);

        
        JPanel btnsHolder = new JPanel();
        btnsHolder.setPreferredSize(new Dimension(memberDetails.getSize().width, 100));
        btnsHolder.setMaximumSize(new Dimension(700, 100));
        btnsHolder.setLayout(new BoxLayout(btnsHolder, BoxLayout.X_AXIS));
        btnsHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnsHolder.setBackground(primaryColor);
        btnsHolder.setBorder(BorderFactory.createEmptyBorder());
        
        updateBtn = new CustomButton("Update");
        updateBtn.setPrimaryColor(new Color(163, 196, 243));
        updateBtn.setHoverColor(new Color(143, 176, 223));
        

        
        deleteBtn = new CustomButton("Delete");
        deleteBtn.setPrimaryColor(new Color(247, 161, 161));
        deleteBtn.setHoverColor(new Color(227, 141, 141));
        
        
        btnsHolder.add(Box.createHorizontalGlue());
        btnsHolder.add(updateBtn);
        btnsHolder.add(Box.createHorizontalStrut(20));
        btnsHolder.add(deleteBtn);
        memberDetails.add(btnsHolder);
        
        main.add(tableWrapper, BorderLayout.WEST);
        main.add(memberDetails);
        this.add(main);
    };
    
    
    // **** Classess ****
    private class SearchEffect implements FocusListener {
        @Override
        public void focusGained(FocusEvent e) {
            if(searchBar.getText().equals("Search member")) {
                searchBar.setText("");
                searchBar.setForeground(Color.BLACK);   
            }
        }
            
        @Override
        public void focusLost(FocusEvent e) {
            if(searchBar.getText().isEmpty()) {
                searchBar.setText("Search member");
                searchBar.setForeground(Color.LIGHT_GRAY);
            }
        }
    }
}
