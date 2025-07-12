/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import Model.*;
import Control.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 *
 * @author Joseph Rey
 */
public class RegistrationFrame extends JFrame{
    
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    ArrayList<JTextField> fields = new ArrayList<>();
    private DefaultTableModel membersTableModel;
    
    public RegistrationFrame(MembersPanel frame) {
        membersTableModel = frame.getMembersTableModel();
        initComponent();
    };
    
    private void initComponent() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int)((double) screenSize.width * 0.5), (int) ((double) screenSize.height * 0.7));
        this.setSize(frameSize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
        this.setResizable(false);
        
        JPanel panelHeader;
        JPanel textContent;
        JLabel headerTitle;
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.X_AXIS));
        panelHeader.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        panelHeader.setMaximumSize(panelHeader.getPreferredSize());
        panelHeader.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

        textContent = new JPanel();
        textContent.setLayout(new BoxLayout(textContent, BoxLayout.Y_AXIS));
        textContent.setOpaque(false);

        headerTitle = new JLabel("Register Member");
        headerTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
        JLabel headerSubTitle = new JLabel("Please Enter the following formation: ");
        headerSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textContent.add(headerTitle);
        textContent.add(Box.createVerticalStrut(5));
        textContent.add(headerSubTitle);
        
        panelHeader.add(textContent);
        this.add(panelHeader, BorderLayout.NORTH);
        
        
         
        // **** Main Content ****
        JPanel informationPanel = new JPanel();
        String[] labelNames;
        CustomButton registerBtn;
        CustomButton cancelBtn;
        JPanel btnsHolder;
        
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getSize().height - 100));
        informationPanel.setMaximumSize(new Dimension(this.getPreferredSize().width, informationPanel.getPreferredSize().height));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        informationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        labelNames = new String[] {"Name", "Username", "Phone", "Email", "Address", "Password"};
        

        for(String name: labelNames) {
            JLabel label = new JLabel(name + ": ");
            label.setFont(primaryFont.deriveFont(Font.BOLD));
            informationPanel.add(label);
            informationPanel.add(Box.createVerticalStrut(5));
            
            JTextField field = new JTextField();
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
            field.setFont(primaryFont);
            field.setBorder(BorderFactory.createEmptyBorder(5,5, 5, 5));
            informationPanel.add(field);
            informationPanel.add(Box.createVerticalStrut(10));
            fields.add(field);
        };
        
        this.add(informationPanel, BorderLayout.CENTER);
        
        
        btnsHolder = new JPanel();
        btnsHolder.setPreferredSize(new Dimension(informationPanel.getSize().width, 100));
        btnsHolder.setMaximumSize(new Dimension(700, 100));
        btnsHolder.setLayout(new BoxLayout(btnsHolder, BoxLayout.X_AXIS));
        btnsHolder.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnsHolder.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        
        registerBtn = new CustomButton("Register");
        registerBtn.setPrimaryColor(new Color(168, 213, 186));
        registerBtn.setHoverColor(new Color(148, 193, 166));
        registerBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                registerMember();
            }
            
        });
        
        cancelBtn = new CustomButton("Cancel");
        cancelBtn.setPrimaryColor(new Color(247, 161, 161));
        cancelBtn.setHoverColor(new Color(227, 141, 141));
        cancelBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cancelRegistration();
            }
        });
        
        btnsHolder.add(Box.createHorizontalGlue());
        btnsHolder.add(registerBtn);
        btnsHolder.add(Box.createHorizontalStrut(10));
        btnsHolder.add(cancelBtn);
        
        this.add(btnsHolder, BorderLayout.SOUTH);
    };
    
    private void cancelRegistration() {
        this.dispose();
    }
    
    private void registerMember() {
        MemberController memCon = new MemberController(new MemberUserDAO(), new MemberView());
        Member member = new Member();
        
        member.setName(fields.get(0).getText());
        member.setUserName(fields.get(1).getText());
        member.setPhone(fields.get(2).getText());
        member.setEmail(fields.get(3).getText());
        member.setAddress(fields.get(4).getText());
        member.setPassword(fields.get(5).getText());
        member.setDateJoined(LocalDate.now());
        memCon.registerMember(member);
        
        memCon.displayMembers(membersTableModel);
        this.dispose();
    }
}
