/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;

import Control.MemberController;
import Model.*;
import Views.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.*;
/**
 *
 * @author Joseph Rey
 */

    
public class UsersPanel extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JTextField> fields = new ArrayList<>();
    private BorderlessTable membersTable;
    private DefaultTableModel membersTableModel;
    private MemberController memControl;
    private Member selectedMember;
    private JTextField searchBar;
    private JComboBox<Member.MembershipStatus> filterStatus;
    
    
    public UsersPanel(JFrame frame) {
        initComponent(frame);
    }
    
    private void initComponent(JFrame frame) {
        this.setPreferredSize(new Dimension(frame.getSize().width - 200, frame.getSize().height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Panel Header 
        JPanel panelHeader;
        JPanel textContent;
        JLabel headerTitle;
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        panelHeader.setMaximumSize(panelHeader.getPreferredSize());
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelHeader.setBackground(primaryColor);

        textContent = new JPanel();
        textContent.setLayout(new BoxLayout(textContent, BoxLayout.Y_AXIS));
        textContent.setOpaque(false);

        headerTitle = new JLabel("Manage Members");
        headerTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
        JLabel headerSubTitle = new JLabel("Add, Update, and Delete Members");
        headerSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textContent.add(headerTitle);
        textContent.add(Box.createVerticalStrut(5));
        textContent.add(headerSubTitle);

        CustomButton registerBtn = new CustomButton("Register Member");
        registerBtn.setPrimaryColor(new Color(168, 213, 186));
        registerBtn.setHoverColor(new Color(148, 193, 166));
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addMember(e);
            }
            
        });
        
        panelHeader.add(textContent);
        panelHeader.add(Box.createHorizontalGlue());
        panelHeader.add(registerBtn);
        this.add(panelHeader);
        
        // **** Search Panel ****
        JPanel searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, 150));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        
        searchBar = new JTextField("Search member");
        searchBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        searchBar.setFont(new Font("Tahoma", Font.PLAIN, 16));
        searchBar.setForeground(Color.LIGHT_GRAY);
        searchBar.addFocusListener(new FocusListener() {

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
                    memControl.displayMembers(membersTableModel);
                }
            }
        });
        
        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterSearch();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e) {
                filterSearch();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                filterSearch();
            }

        });
        
        searchPanel.add(searchBar);
        searchPanel.add(Box.createHorizontalStrut(10));
        
        
        filterStatus = new JComboBox<>(Member.MembershipStatus.values());
        filterStatus.setFont(primaryFont);
        filterStatus.setPreferredSize(new Dimension(150, 50));
        filterStatus.setMaximumSize(filterStatus.getPreferredSize());
        
        searchPanel.add(filterStatus);
        
        this.add(searchPanel);
        
        //Main Content 
        JPanel mainContentContainer = new JPanel();
        mainContentContainer.setLayout(new BoxLayout(mainContentContainer, BoxLayout.X_AXIS));
        
        // **** Members Tables ****
        JPanel tableWrapper;
        CustomScrollPane scrollPane;
        String[] columnNames;
        
        membersTable = new BorderlessTable();
        columnNames = new String[]{"#", "Name", "Status", "Email", "Date Joined"};
        membersTableModel = new DefaultTableModel(null, columnNames);
        membersTable.changeModel(membersTableModel);


        memControl = new MemberController(new MemberUserDAO(), new MemberView());
        memControl.displayMembers(membersTableModel);

        membersTable.getColumnModel().getColumn(0).setMaxWidth(200);
        membersTable.getColumnModel().getColumn(0).setMinWidth(100);
        
        scrollPane = new CustomScrollPane(membersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        
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
        membersTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tableMouseClicked(e);
            }
            
        });

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

        
        JPanel btnsHolder = new JPanel();
        btnsHolder.setPreferredSize(new Dimension(memberDetails.getSize().width, 100));
        btnsHolder.setMaximumSize(new Dimension(700, 100));
        btnsHolder.setLayout(new BoxLayout(btnsHolder, BoxLayout.X_AXIS));
        btnsHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnsHolder.setBorder(BorderFactory.createEmptyBorder());
        
        updateBtn = new CustomButton("Update");
        updateBtn.setPrimaryColor(new Color(163, 196, 243));
        updateBtn.setHoverColor(new Color(143, 176, 223));
        updateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                udpateMember(e);
            }
            
        });

        
        deleteBtn = new CustomButton("Delete");
        deleteBtn.setPrimaryColor(new Color(247, 161, 161));
        deleteBtn.setHoverColor(new Color(227, 141, 141));
        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteMember(e);
            }
            
        });
        
        btnsHolder.add(Box.createHorizontalGlue());
        btnsHolder.add(updateBtn);
        btnsHolder.add(Box.createHorizontalStrut(20));
        btnsHolder.add(deleteBtn);
        memberDetails.add(btnsHolder);
        
        mainContentContainer.add(tableWrapper, BorderLayout.WEST);
        mainContentContainer.add(memberDetails);
        this.add(mainContentContainer);
    };
    
    private void tableMouseClicked(MouseEvent e) {
        int selectedRowIndex = membersTable.getSelectedRow();
        int memberID;
        
        try {
            memberID = Integer.parseInt(membersTableModel.getValueAt(selectedRowIndex, 0).toString());
            
        }
        catch(NumberFormatException ex) {
            return;
        }
        
        selectedMember = memControl.getMemberDetail(memberID);
        fields.get(0).setText(selectedMember.getName());
        fields.get(1).setText(selectedMember.getUserName());
        fields.get(2).setText(selectedMember.getStatus().toString().toLowerCase());
        fields.get(3).setText(selectedMember.getEmail());
        fields.get(4).setText(selectedMember.getPhone());
        fields.get(5).setText(selectedMember.getAddress());
        //Date
        fields.get(6).setText(selectedMember.getDateJoined().toString());   
    }
    
    private void udpateMember(MouseEvent e) {
        selectedMember.setName(fields.get(0).getText());
        selectedMember.setUserName(fields.get(1).getText());
        selectedMember.setStatus(Member.MembershipStatus.valueOf(fields.get(2).getText().strip().toUpperCase()));
        selectedMember.setEmail(fields.get(3).getText());
        selectedMember.setPhone(fields.get(4).getText());
        selectedMember.setAddress(fields.get(5).getText());
        selectedMember.setDateJoined(LocalDate.parse(fields.get(6).getText()));
        
        memControl.udpateMember(selectedMember);
        memControl.displayMembers(membersTableModel);
        
        JOptionPane.showMessageDialog(null,  selectedMember.getName() + "'s details has been updated sucessfully");
    }
    
    private void deleteMember(MouseEvent e) {
        memControl.deleteMember(selectedMember);
        memControl.displayMembers(membersTableModel);
        for(JTextField field : fields) {
            field.setText("");
        }
        JOptionPane.showMessageDialog(null,  selectedMember.getName() + " account has been deleted sucessfully");
    }
    
    private void filterSearch() {
        memControl.filterMembers(membersTableModel, searchBar.getText(), (Member.MembershipStatus) filterStatus.getSelectedItem());
    }
    
    private void addMember(MouseEvent e) {
        RegistrationFrame frame = new RegistrationFrame(this);
        frame.setVisible(true);
    }

    public BorderlessTable getMembersTable() {
        return membersTable;
    }

    public void setMembersTable(BorderlessTable membersTable) {
        this.membersTable = membersTable;
    }

    public DefaultTableModel getMembersTableModel() {
        return membersTableModel;
    }

    public void setMembersTableModel(DefaultTableModel membersTableModel) {
        this.membersTableModel = membersTableModel;
    }
    
    
    
}
    
