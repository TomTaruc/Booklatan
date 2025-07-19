package View;

import View.Components.*;
import Model.User;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import Control.Components.MemberManagerController;
import Control.Components.FinesController; // Import FinesController
import Model.FineDAO; // Import FineDAO
import Model.UserMemberDAO; 

/**
 *
 * @author Joseph Rey
 */
public class LibrarianApplication extends Application {
    public Sidebar sidebar;
    public CardLayout mainPanelLayout;
    public JPanel mainPanel;
    public LibDashboard dashboard;
    public MembersManager members;
    public FinesPanel fines; // Declare FinesPanel
    private User user;

    // Controllers
    private MemberManagerController memCon;
    private FinesController finesController; // Declare FinesController

    public LibrarianApplication(User user) {
        this.user = user;
        addSideBar();
        addMainPanel();
        initializeControllers(); 
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
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);

        Dimension panelSize = new Dimension(this.getWidth() - sidebar.getPreferredSize().width, this.getHeight());

        dashboard = new LibDashboard(panelSize);
        JPanel books = new JPanel(); 
        members = new MembersManager(panelSize, false); 
        JPanel loans = new JPanel(); // Placeholder for Loans Panel
        JPanel reservations = new JPanel(); // Placeholder for Reservations Panel
        fines = new FinesPanel(); // Instantiate FinesPanel BEFORE its controller needs it

        // Set background colors for placeholder panels
        books.setBackground(Color.black);
        loans.setBackground(Color.blue);
        reservations.setBackground(Color.green);

        // Add panels to the mainPanel with their respective card names
        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(loans, "loans");
        mainPanel.add(reservations, "reservations");
        mainPanel.add(fines, "fines");

        this.add(mainPanel, BorderLayout.CENTER); 
    }

    private void initializeControllers() {

        // Initialize FinesController
        FineDAO fineDAO = new FineDAO(); // Create an instance of FineDAO
        finesController = new FinesController(fineDAO, fines);
        fines.setController(finesController); 
    }
}