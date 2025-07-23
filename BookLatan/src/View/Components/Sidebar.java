/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Joseph Rey
 */
public class Sidebar extends JPanel{
    private Map<String, JButton> btns = new LinkedHashMap<>();
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private Color selectedColor = new Color(108, 117, 125);
    private Color primaryColor = new Color(33, 37, 41);
    private Color textColor = new Color(173, 181, 189);
    private int preferredWidth = 300;
    private JButton selectedBtn = null;
    
    public Sidebar(Dimension frameSize) {
        initComponent(frameSize);
        
    }
    
    public void initComponent(Dimension frameSize) {
        this.setMaximumSize(new Dimension(preferredWidth, frameSize.height));
        this.setPreferredSize(new Dimension(preferredWidth, frameSize.height));
        this.setBackground(primaryColor);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    /**
     * Display the booklatan icon and username and identification.
     * 
     * @param userno is not actually the userNo but either the staffNo or memberNo. 
     * @param username is the username of the user.
     */
    
    public void addUserInfo(String username, int userno) {
        JPanel userinfo = new JPanel();
        JLabel usernameLabel = new JLabel(username);
        JLabel accountNo = new JLabel(String.format("%06d", userno));
        JLabel appIcon = new JLabel();
        int panelHeight = 150;
        Image booklantanIcon = new ImageIcon("./src/Images/userimage.png").getImage().getScaledInstance(60, 50, Image.SCALE_SMOOTH);
        
        userinfo.setLayout(new BoxLayout(userinfo, BoxLayout.Y_AXIS));
        userinfo.setBackground(this.getBackground());
        userinfo.setPreferredSize(new Dimension(this.getPreferredSize().width, panelHeight));
        userinfo.setMaximumSize(userinfo.getPreferredSize());
        userinfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        appIcon.setIcon(new ImageIcon(booklantanIcon));
        appIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        usernameLabel.setFont(primaryFont.deriveFont(Font.BOLD, 25));
        usernameLabel.setForeground(textColor);
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        accountNo.setFont(primaryFont.deriveFont(Font.BOLD | Font.ITALIC, 15));
        accountNo.setForeground(textColor);
        accountNo.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        userinfo.add(Box.createVerticalStrut(20));
        userinfo.add(appIcon);
        userinfo.add(Box.createVerticalStrut(15));
        userinfo.add(usernameLabel);
        userinfo.add(Box.createVerticalStrut(10));
        userinfo.add(accountNo);
        userinfo.add(Box.createVerticalStrut(20));
        this.add(userinfo, 0);
    }
    
    
    /**
     * Adds menu items on the side bar.
     * @param menuItems is a Map<String, String> where first param is button name and second is icon path.
     */
    public void addMenuItems(Map<String, String> menuItems) {
        ImageIcon img;
        JButton btn;
        int btnHeight = 50;
        Insets paddingSize = new Insets(10, 20, 10, 20);
        int margin2x = 10; //top and bottom margin
        int iconTextGap = 10;
                
        for(Map.Entry<String, String> item : menuItems.entrySet()) {
            img = new ImageIcon(item.getValue());
            btn = new JButton(item.getKey());
            btn.setIcon(img);
            btn.setFont(primaryFont.deriveFont(Font.BOLD, 15));
            btn.setForeground(textColor);
            btn.setIconTextGap(iconTextGap);
            btn.setPreferredSize(new Dimension(this.getPreferredSize().width, btnHeight));
            btn.setMaximumSize(btn.getPreferredSize());
            btn.setMargin(paddingSize);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.setBackground(null);
            btn.setOpaque(true);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);
            btn.setHorizontalAlignment(SwingConstants.LEFT);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.addMouseListener(new HoverEffect());
            btn.addActionListener(new ButtonClickHandler());
            this.add(Box.createVerticalStrut(margin2x));
            this.add(btn);
            this.add(Box.createVerticalStrut(margin2x));
            btns.put(item.getKey(), btn);
        }
        
        img = new ImageIcon("./src/Images/logout.png");
        btn = new JButton("Logout");
        btn.setIcon(img);
        btn.setFont(primaryFont.deriveFont(Font.BOLD, 15));
        btn.setForeground(textColor);
        btn.setIconTextGap(iconTextGap);
        btn.setPreferredSize(new Dimension(this.getPreferredSize().width, btnHeight));
        btn.setMaximumSize(btn.getPreferredSize());
        btn.setMargin(paddingSize);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBackground(null);
        btn.setOpaque(true);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.addMouseListener(new HoverEffect());
        btn.addActionListener(new ButtonClickHandler());
        this.add(Box.createVerticalGlue());
        this.add(btn);
        this.add(Box.createVerticalStrut(20));
        btns.put("Logout", btn);
    }
    
    private void updateSelectedButton(JButton clickedButton) {
        if (selectedBtn != null) {
            selectedBtn.setContentAreaFilled(false);
            selectedBtn.setBackground(primaryColor);
        }

        clickedButton.setContentAreaFilled(true);
        clickedButton.setBackground(selectedColor);
        selectedBtn = clickedButton;
    } 
    
    
    // **** Nested class ****
    private class ButtonClickHandler implements ActionListener {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            JButton clickedButton = (JButton) e.getSource();
            updateSelectedButton(clickedButton);
        }
    }
    
    private class HoverEffect extends MouseAdapter {
        boolean isClicked = false;
        @Override
        public void mouseEntered(MouseEvent e) {
            if(isClicked)
                return;
            JButton btn = (JButton) e.getComponent();
            btn.setContentAreaFilled(true);
            btn.setBackground(selectedColor);
        }
            
        @Override
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton) e.getComponent();
            if(btn != selectedBtn) {
                btn.setContentAreaFilled(false);
                btn.setBackground(primaryColor);
            }
        }
    }
    
    
    // **** Getters *****
    public Map<String, JButton> getMenuButtons() {
        return btns;
    }
    
}
