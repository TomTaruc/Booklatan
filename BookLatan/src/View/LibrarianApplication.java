package View;

import Control.Components.FinesController;
import Control.Components.MemberManagerController;
import Model.FineDAO;
import Model.User;
import View.Components.*;
import java.awt.*;
import java.util.LinkedHashMap; //dinel
import java.util.Map;
<<<<<<< HEAD
import javax.swing.*; // Import FinesController
=======
>>>>>>> parent of c91736e (Merge pull request #15 from CodeGizmo18/main)
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
    public FinesPanel fines; // Declare FinesPanel
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
<<<<<<< HEAD
        dashboard = new LibDashboard(panelSize);
        BookManager books = new BookManager(panelSize, User.UserType.LIBRARIAN);
        members = new MembersManager(panelSize, false);
=======
        dashboard = new LibDashboard(new Dimension(this.getWidth() - 200, this.getHeight()));
        JPanel books = new JPanel();
        members = new MembersManager(new Dimension(this.getWidth() - 200, this.getHeight()), false);
>>>>>>> parent of c91736e (Merge pull request #15 from CodeGizmo18/main)
        JPanel loans = new JPanel();
        JPanel reservations = new JPanel();
        FinesPanel fines = new FinesPanel();
        
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


        this.add(mainPanel);
        this.add(mainPanel, BorderLayout.CENTER); 
    }
}
