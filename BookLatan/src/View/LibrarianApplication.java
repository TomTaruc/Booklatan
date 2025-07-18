/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import View.Components.*;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Dinel
 */
public class LibrarianApplication extends Application {
    public Sidebar sidebar;
    public CardLayout mainPanelLayout;
    public JPanel mainPanel;
    public LibDashboard dashboard;
    public MembersManager members;
    private User user;

    public LibrarianApplication(User user) {
        this.user = user;
        addSideBar();
        addMainPanel();
    }

    @Override
    protected void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Members", "./src/Images/members.png");
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
        BookManager books = new BookManager(panelSize, User.UserType.LIBRARIAN);
        members = new MembersManager(panelSize, false);
        JPanel loans = new JPanel();
        JPanel reservations = new JPanel();
        FinesPanel fines = new FinesPanel();

        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(loans, "loans");
        mainPanel.add(reservations, "reservations");
        mainPanel.add(fines, "fines");

        this.add(mainPanel);
    }
}
    