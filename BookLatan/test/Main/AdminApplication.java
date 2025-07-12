/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Views.Application;
import Views.BooksPanel;
import Views.DashboardPanel;
import Views.MembersPanel;
import Views.Sidebar2;
import Views.StaffPanel;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Joseph Rey
 */
public class AdminApplication extends Application{        
    
    public AdminApplication() {
        setup();
        addSideBar();
        addMainPanel();
        this.setVisible(true);
    }
    
    private void addMainPanel() {
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        
        DashboardPanel panel1 = new DashboardPanel(this);
        BooksPanel panel2 = new BooksPanel(this);
        MembersPanel panel3 = new MembersPanel(this);
        StaffPanel panel4 = new StaffPanel(this);
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        
        panel5.setBackground(Color.green);
        panel6.setBackground(Color.pink);
        
        cardPanel.add(panel1, "card1");
        cardPanel.add(panel2, "card2");
        cardPanel.add(panel3, "card3");
        cardPanel.add(panel4, "card4");
        cardPanel.add(panel5, "card5");
        cardPanel.add(panel6, "card6");
        
        
        btns.get(0).addActionListener(e -> cardLayout.show(cardPanel, "card1"));
        btns.get(1).addActionListener(e -> cardLayout.show(cardPanel, "card2"));
        btns.get(2).addActionListener(e -> cardLayout.show(cardPanel, "card3"));
        btns.get(3).addActionListener(e -> cardLayout.show(cardPanel, "card4"));
        btns.get(4).addActionListener(e -> cardLayout.show(cardPanel, "card5"));
        btns.get(5).addActionListener(e -> cardLayout.show(cardPanel, "card6"));
        
        this.add(cardPanel);
    }
    
    public void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Members", "./src/Images/members.png");
        menuItems.put("Staff", "./src/Images/members.png");
        menuItems.put("Loans", "./src/Images/loanbooks.png");
        menuItems.put("Reservations", "./src/Images/bookreservation.png");
        Sidebar2 sb = new Sidebar2(this, menuItems);
        this.add(sb, BorderLayout.WEST);
        btns = sb.btns;
    }

    public void setup() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode mode = gd.getDisplayMode();
        
        this.setTitle("BookLatan");
        this.setName("Admin Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("./src/Images/userimage.png").getImage());
        this.setPreferredSize(new Dimension(mode.getWidth(), mode.getHeight() - 20));
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setResizable(false);
    }
    
}
