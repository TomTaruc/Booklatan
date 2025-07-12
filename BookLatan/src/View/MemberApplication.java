/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import View.Components.Application;
import View.Components.Sidebar;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Joseph Rey
 */
public class MemberApplication extends Application{
    private Sidebar sidebar;
    private CardLayout menus;
    
    
    public MemberApplication() {
        this.addSideBar();
    }
    
    @Override
    protected void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Loans", "./src/Images/loanbooks.png");
        menuItems.put("Reservations", "./src/Images/bookreservation.png");
        menuItems.put("Fines", "./src/Images/payments.png");
        sidebar = new Sidebar(this.getSize());
        // TASK: REPLACE THE CODE UNDER THIS SO THAT
        // EVERYTIME A USER LOGIN THIER USER NAME
        // AND USER NO (MEMBERID/STAFFID) IS DISPLAYED.
        // ON THE SIDEBAR. For: Dinel From: Joseph
        sidebar.addUserInfo("Alice", 2);
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }
}
