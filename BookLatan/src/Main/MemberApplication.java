/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Views.Application;
import Views.Sidebar;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Joseph Rey
 */
public class MemberApplication extends Application{
    
    public MemberApplication() {
        super();
        this.addSideBar();
        this.setVisible(true);
    }
    
    public void setup() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode mode = gd.getDisplayMode();
        
        this.setTitle("BookLatan");
        this.setName("Member Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("./src/Images/userimage.png").getImage());
        this.setPreferredSize(new Dimension(mode.getWidth(), mode.getHeight() - 20));
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setResizable(false);
    }
    
    public void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Loans", "./src/Images/loanbooks.png");
        menuItems.put("Reservations", "./src/Images/bookreservation.png");
        menuItems.put("Fines", "./src/Images/payments.png");
        Sidebar sb = new Sidebar(this, menuItems);
        this.add(sb, BorderLayout.WEST);
        btns = sb.btns;   
    }
}
