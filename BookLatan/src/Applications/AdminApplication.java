/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Components.Dashboards.LibDashboard;
import Model.User;
import Components.Managers.MembersManager;
import View.Components.Sidebar;
import Components.Managers.StaffManager;
import Components.Managers.BookManager;
import Utilities.Design;
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

    public AdminApplication() {
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
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }

    private void addMainPanel() {
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        dashboard = new LibDashboard();
        BookManager books = new BookManager(Design.MAIN_PANEL_SIZE, User.UserType.ADMIN);
        members = new MembersManager(Design.MAIN_PANEL_SIZE, true);
        staff = new StaffManager(Design.MAIN_PANEL_SIZE);

        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(staff, "staff");

        this.add(mainPanel);
    }
}