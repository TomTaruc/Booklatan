/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Componenents.Members.FineView;
import Components.Designs.Sidebar;
import Model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JPanel;
import java.awt.Dimension;

import Components.Managers.BookManager;
import Components.Managers.BookManagerController;
import Components.Dashboards.LibDashboard;
import Components.Managers.ReservationsManager;
import Utilities.Design;
import java.awt.Color;


/**
 *
 * @author Dinel
 */

public class MemberApplication extends Application {
    Sidebar sidebar;
    public CardLayout mainPanelLayout;
    public JPanel mainPanel;
    LibDashboard dashboard;
    BookManager books;
    JPanel loans;
    ReservationsManager reservations;
    FineView fines;

    public MemberApplication() {
        this.addSideBar();
        this.addMainPanel();
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
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }

    private void addMainPanel() {
        Dimension panelSize = new Dimension(this.getWidth() - 200, this.getHeight());
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        
        dashboard = new LibDashboard();
        books = new BookManager(panelSize, User.UserType.MEMBER); // read-only for members
        // Connect BookManager to database through controller
        BookManagerController bookController = new BookManagerController(books);
        loans = new JPanel();
        reservations = new ReservationsManager(Design.MAIN_PANEL_SIZE, User.UserType.MEMBER);
        fines = new FineView();
        
        loans.setBackground(Color.pink);

        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(loans, "loans");
        mainPanel.add(reservations, "reservations");
        mainPanel.add(fines, "fines");

        this.add(mainPanel);
    }

}

