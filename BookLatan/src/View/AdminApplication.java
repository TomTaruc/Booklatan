/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import View.Components.Application;
import View.Components.Sidebar;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import Model.User;

/**
 *
 * @author Joseph Rey
 */
public class AdminApplication extends Application {
    private Sidebar sidebar;
    private CardLayout menus;
    private User user;

    public AdminApplication(User user) {
        this.user = user;
        System.out.println("AdminApplication opened for: " + user.getUsername() + " (ID: " + user.getUserId() + ")");
        this.addSideBar();
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
}
