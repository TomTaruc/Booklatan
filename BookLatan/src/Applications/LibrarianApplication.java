/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Components.Designs.Sidebar;
import Components.Managers.FinesManager;
import Components.Dashboards.LibDashboard;
import Components.Managers.BookManager;
import Components.Managers.BookManagerController;
import Components.Managers.LoanManager;
import Components.Managers.MembersManager;
import Components.Managers.ReservationsManager;
import Model.User;
import Utilities.Design;
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
    public ReservationsManager reservations;
    public FinesManager fines;
    public LoanManager loans;
    
    public LibrarianApplication() {
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

        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }

    private void addMainPanel() {
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        dashboard = new LibDashboard();
        BookManager books = new BookManager(Design.MAIN_PANEL_SIZE, User.UserType.LIBRARIAN);
        // Connect BookManager to database through controller
        BookManagerController bookController = new BookManagerController(books);
        members = new MembersManager(Design.MAIN_PANEL_SIZE, false);
        loans = new LoanManager();
        reservations = new ReservationsManager(Design.MAIN_PANEL_SIZE, User.UserType.LIBRARIAN);
        fines = new FinesManager();

        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(loans, "loans");
        mainPanel.add(reservations, "reservations");
        mainPanel.add(fines, "fines");

        this.add(mainPanel);
    }
}
    