/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import View.Components.Application;
import View.Components.Sidebar;
import Model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;

import View.Components.BookManager;
import View.Components.LibDashboard;


/**
 *
 * @author Dinel
 */

public class MemberApplication extends Application {
    private Sidebar sidebar;
    private CardLayout mainPanelLayout;
    private JPanel mainPanel;
    private LibDashboard dashboard;
    private BookManager books;
    private User user;

    public MemberApplication(User user) {
        this.user = user;
        this.addSideBar();
        this.addMainPanel();
        this.attachListeners();
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
        books = new BookManager(panelSize, User.UserType.MEMBER); // read-only for members

        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");

        this.add(mainPanel);
    }

    private void attachListeners() {
        Map<String, JButton> btns = sidebar.getMenuButtons();
        btns.get("Dashboard").addActionListener(e -> mainPanelLayout.show(mainPanel, "dashboard"));
        btns.get("Books").addActionListener(e -> mainPanelLayout.show(mainPanel, "books"));
    }
}

