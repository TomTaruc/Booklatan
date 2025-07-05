/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Map;
/**
 *
 * @author Joseph Rey
 */
public class Sidebar extends JPanel{
    public ArrayList<JButton> btns = new ArrayList<>();
    private Color textColor = new Color(173, 181, 189);
    private Color selectedColor = new Color(108, 117, 125);
    private Color primaryColor = new Color(33, 37, 41);
    
    public Sidebar(JFrame frame, Map<String, String> menuItems) {
        //Panel Setup
        this.setPreferredSize(new Dimension( 200 , frame.getSize().height));
        this.setSize(this.getPreferredSize());
        this.setBackground(primaryColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        //Add basic userinfo
        JPanel userinfo = new JPanel();
        userinfo.setLayout(new BoxLayout(userinfo, BoxLayout.Y_AXIS));
        userinfo.setBackground(this.getBackground());
        userinfo.setPreferredSize(new Dimension(this.getPreferredSize().width, 130));
        userinfo.setMaximumSize(userinfo.getPreferredSize());
        userinfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        ImageIcon imgIcon = new ImageIcon("./src/Images/userimage.png");
        Image scaled = imgIcon.getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH);
        imgIcon = new ImageIcon(scaled);
        JLabel userIcon = new JLabel();
        userIcon.setIcon(imgIcon);
        userIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        
        
        JLabel username = new JLabel("John Doe");
        username.setFont(new Font("Tahoma", Font.BOLD, 18));
        username.setForeground(textColor);
        username.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel accountNo = new JLabel("200001");
        accountNo.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
        accountNo.setForeground(textColor);
        accountNo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userinfo.add(Box.createVerticalStrut(20));
        userinfo.add(userIcon);
        userinfo.add(Box.createHorizontalStrut(10));
        userinfo.add(username);
        userinfo.add(Box.createVerticalStrut(5));
        userinfo.add(accountNo);
        this.add(userinfo);
        this.add(Box.createVerticalStrut(10));
        
        
        //Add buttons
        MouseAdapter hoverEffect = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JButton btn = (JButton) e.getComponent();
                btn.setContentAreaFilled(true);
                btn.setBackground(selectedColor);
            }
            
            
            public void mouseExited(MouseEvent e) {
                JButton btn = (JButton) e.getComponent();
                btn.setContentAreaFilled(false);
                btn.setBackground(primaryColor);
            }
        };
        
        for(Map.Entry<String, String> item : menuItems.entrySet()) {
            ImageIcon img = new ImageIcon(item.getValue());
            JButton btn = new JButton(item.getKey());
            btn.setIcon(img);
            btn.setFont(new Font("Tahoma", Font.BOLD, 15));
            btn.setForeground(textColor);
            btn.setIconTextGap(10);
            btn.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
            btn.setMaximumSize(btn.getPreferredSize());
            btn.setMargin(new Insets(10, 10, 10, 20));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBackground(null);
            btn.setOpaque(true);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addMouseListener(hoverEffect);
            
            btns.add(btn);
            this.add(Box.createVerticalStrut(10));
            this.add(btn);
        }
        
        JButton logoutBtn = new JButton("Logout");
        ImageIcon logoutIcon = new ImageIcon("./src/Images/logout.png");
        logoutBtn.setIcon(logoutIcon);
        logoutBtn.setForeground(textColor);
        logoutBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        logoutBtn.setIconTextGap(10);
        logoutBtn.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        logoutBtn.setMaximumSize(logoutBtn.getPreferredSize());
        logoutBtn.setMargin(new Insets(10, 10, 10, 20));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setBackground(null);
        logoutBtn.setBorderPainted(false);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setHorizontalAlignment(SwingConstants.LEFT);
        logoutBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutBtn.addMouseListener(hoverEffect);
        logoutBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?", "Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                
                if(confirm == JOptionPane.YES_OPTION) {
                    System.out.println("User Logged Out");
                    System.exit(0);
                }
            }
            
        });
        this.add(Box.createVerticalGlue());
        this.add(logoutBtn);
        this.add(Box.createVerticalStrut(30));
    }
    
}
