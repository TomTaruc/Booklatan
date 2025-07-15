/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Model.User;
import View.Components.Application;
import View.Components.LibDashboard;
import View.Components.MembersManager;
import View.Components.Sidebar;
import View.Components.StaffManager;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Joseph Rey
 */
public class AdminApplication extends Application {
    public Sidebar sidebar;
    public JPanel mainPanel;
    public CardLayout mainPanelLayout;
    public LibDashboard dashboard;
    public MembersManager members;
    public StaffManager staff;
    private User user;
    
    public AdminApplication(User user) {
        this.user = user;
        System.out.println("AdminApplication opened for: " + user.getUsername() + " (ID: " + user.getUserId() + ")");
        this.addSideBar();
        this.addMainPanel();
    }

    @Override
    protected void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Members", "./src/Images/members.png");
        menuItems.put("Staff", "./src/Images/members.png");

        sidebar = new Sidebar(this.getSize());
        sidebar.addUserInfo(user.getUsername(), user.getUserId());
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }
    
    private void addMainPanel() {
        Dimension panelSize = new Dimension(this.getWidth() - 200, this.getHeight());
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        dashboard = new LibDashboard(panelSize);
        JPanel books = new JPanel();
        members = new MembersManager(panelSize, true);
        staff = new StaffManager(panelSize);
        
        books.setBackground(Color.black);
        staff.setBackground(Color.blue);
        
        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(staff, "staff");
        
        this.add(mainPanel);
    }
    
}
