/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Control.MemberController;
import Model.*;
import Views.BorderlessTable;
import Views.CustomScrollPane;
import Views.MemberView;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.*;
/**
 *
 * @author Joseph Rey
 */

    
public class MembersPanel extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JTextField> fields = new ArrayList<>();
    private BorderlessTable membersTable;
    private DefaultTableModel membersTableModel;
    private MemberController memControl;
    private Member selectedMember;
    
    
    public MembersPanel(JFrame frame) {
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

        panelHeader.add(textContent);
        panelHeader.add(Box.createHorizontalGlue());
        this.add(panelHeader);
        
        
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


        memControl = new MemberController(new MemberDAO(), new MemberView());
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
        JButton updateBtn;
        JButton deleteBtn;
        
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
        
        updateBtn = new JButton("Update");
        updateBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        updateBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                udpateMember(e);
            }
            
        });
        memberDetails.add(updateBtn);
        
        
        deleteBtn = new JButton("Delete");
        deleteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        deleteBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                deleteMember(e);
            }
            
        });
        memberDetails.add(deleteBtn);
        
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
        System.out.println("Worknig");
        memControl.deleteMember(selectedMember);
        memControl.displayMembers(membersTableModel);
        JOptionPane.showMessageDialog(null,  selectedMember.getName() + " account has been deleted sucessfully");
    }
}
    
