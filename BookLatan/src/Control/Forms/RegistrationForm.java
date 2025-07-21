/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control.Forms;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import Model.*;
import View.Components.CustomButton;
import View.Components.HeaderPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 *
 * @author Joseph Rey
 */
public class RegistrationForm extends JFrame{
    public CustomButton registerBtn;
    public CustomButton cancelBtn;
    public ArrayList<JTextField> fields = new ArrayList<>();
    
    private boolean isStaff = false;
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private Color primaryColor = new Color(245, 245, 245);
    
    public RegistrationForm(boolean isStaff) {
        this.isStaff = isStaff;
        initComponent();
    }
    
    private void initComponent() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int)((double) screenSize.width * 0.5), (int) ((double) screenSize.height * 0.7));
        this.setSize(frameSize);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);        
        this.setResizable(false);
        
        
        HeaderPanel header = new HeaderPanel(new Dimension(this.getWidth(), 100));
        header.setTitle("Register User");
        header.setSubtitle("Please Enter the following information");
        this.add(header, BorderLayout.NORTH);
        
        
         
        // **** Main Content ****
        JPanel informationPanel = new JPanel();
        String[] labelNames;
        JPanel btnsHolder;
        
        informationPanel.setLayout(new BoxLayout(informationPanel, BoxLayout.Y_AXIS));
        informationPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getSize().height - 100));
        informationPanel.setMaximumSize(new Dimension(this.getPreferredSize().width, informationPanel.getPreferredSize().height));
        informationPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        informationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        informationPanel.setBackground(primaryColor);
        
        labelNames = new String[] {"Name", "Username", "Phone", "Email", "Address", "Password", "Role", "Date Hired"};
        

        for(String name: labelNames) {
            
            if(!isStaff && (name.equalsIgnoreCase("role") || name.equalsIgnoreCase("date hired")))
                continue;
            
            
            
            JLabel label = new JLabel(name + ": ");
            label.setFont(primaryFont.deriveFont(Font.BOLD));
            
            informationPanel.add(label);
            informationPanel.add(Box.createVerticalStrut(5));
            
            JTextField field = new JTextField();
            if(name.equalsIgnoreCase("date hired")) {
                field.setText(LocalDate.now().toString());
            }
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
        btnsHolder.setBackground(primaryColor);
        btnsHolder.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        
        registerBtn = new CustomButton("Register");
        registerBtn.setPrimaryColor(new Color(168, 213, 186));
        registerBtn.setHoverColor(new Color(148, 193, 166));
        
        
        cancelBtn = new CustomButton("Cancel");
        cancelBtn.setPrimaryColor(new Color(247, 161, 161));
        cancelBtn.setHoverColor(new Color(227, 141, 141));
        
        btnsHolder.add(Box.createHorizontalGlue());
        btnsHolder.add(registerBtn);
        btnsHolder.add(Box.createHorizontalStrut(10));
        btnsHolder.add(cancelBtn);
        
        this.add(btnsHolder, BorderLayout.SOUTH);
    };
    
    
}
