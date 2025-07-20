package View;

import View.Components.Application;
import View.Components.Sidebar;
import View.Components.MemberFinesPanel; // Tom: Import from new MemberFinesPanel
import Model.User;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Dimension;

import View.Components.BookManager;
import View.Components.LibDashboard;
import javax.swing.JPanel; 
import javax.swing.JLabel; 
import java.awt.Font;
import javax.swing.SwingConstants; 
import javax.swing.JButton; 
import java.awt.GridBagLayout; 



/**
 * The main application frame for a Member user.
 * It sets up the sidebar and a main panel with a CardLayout to switch between different views.
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

    //Tom: Declare finesPanel as a class field
    public MemberFinesPanel finesPanel;

    private JPanel dashboardPlaceholderPanel;
    private JPanel booksPlaceholderPanel;
    private JPanel loansPlaceholderPanel;
    private JPanel reservationsPlaceholderPanel;

    public MemberApplication(User user) {
        this.user = user;
        super.initComponent();

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
        menuItems.put("Fines", "./src/Images/payments.png"); // Fines tab

        sidebar = new Sidebar(this.getSize());
        sidebar.addUserInfo(user.getUsername(), user.getUserId());
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();

        Map<String, JButton> sidebarButtons = sidebar.getMenuButtons();

        if (sidebarButtons.containsKey("Dashboard")) {
            sidebarButtons.get("Dashboard").addActionListener(e -> mainPanelLayout.show(mainPanel, "dashboard"));
        }
        if (sidebarButtons.containsKey("Books")) {
            sidebarButtons.get("Books").addActionListener(e -> mainPanelLayout.show(mainPanel, "books"));
        }
        if (sidebarButtons.containsKey("Loans")) {
            sidebarButtons.get("Loans").addActionListener(e -> mainPanelLayout.show(mainPanel, "loans"));
        }
        if (sidebarButtons.containsKey("Reservations")) {
            sidebarButtons.get("Reservations").addActionListener(e -> mainPanelLayout.show(mainPanel, "reservations"));
        }
        if (sidebarButtons.containsKey("Fines")) {
            sidebarButtons.get("Fines").addActionListener(e -> {
                mainPanelLayout.show(mainPanel, "fines");
                // Refresh fines data when the tab is shown to ensure it's up-to-date
                if (finesPanel != null) {
                    finesPanel.refreshFines();
                }
            });
        }
    }


    private void addMainPanel() {
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        int sidebarWidth = sidebar.getPreferredSize().width;
        Dimension panelSize = new Dimension(this.getWidth() - sidebarWidth, this.getHeight());

        // Initialize placeholder panels for other tabs.
        // These are simple JPanels without specific content, serving only for navigation.
        dashboardPlaceholderPanel = new JPanel();
        dashboardPlaceholderPanel.setPreferredSize(panelSize);
        dashboardPlaceholderPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for centering
        JLabel dashboardLabel = new JLabel("Member Dashboard View", SwingConstants.CENTER);
        dashboardLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        dashboardPlaceholderPanel.add(dashboardLabel);


        booksPlaceholderPanel = new JPanel();
        booksPlaceholderPanel.setPreferredSize(panelSize);
        booksPlaceholderPanel.setLayout(new GridBagLayout());
        JLabel booksLabel = new JLabel("Member Books View", SwingConstants.CENTER);
        booksLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        booksPlaceholderPanel.add(booksLabel);

        loansPlaceholderPanel = new JPanel();
        loansPlaceholderPanel.setPreferredSize(panelSize);
        loansPlaceholderPanel.setLayout(new GridBagLayout());
        JLabel loansLabel = new JLabel("Member Loans View", SwingConstants.CENTER);
        loansLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loansPlaceholderPanel.add(loansLabel);

        reservationsPlaceholderPanel = new JPanel();
        reservationsPlaceholderPanel.setPreferredSize(panelSize);
        reservationsPlaceholderPanel.setLayout(new GridBagLayout());
        JLabel reservationsLabel = new JLabel("Member Reservations View", SwingConstants.CENTER);
        reservationsLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        reservationsPlaceholderPanel.add(reservationsLabel);
        finesPanel = new MemberFinesPanel(user);
        finesPanel.setPreferredSize(panelSize); // Tom

        // Tom: Added 
        mainPanel.add(dashboardPlaceholderPanel, "dashboard");
        mainPanel.add(booksPlaceholderPanel, "books");
        mainPanel.add(loansPlaceholderPanel, "loans");
        mainPanel.add(reservationsPlaceholderPanel, "reservations");
        mainPanel.add(finesPanel, "fines"); // Add the fines panel

        this.add(mainPanel, BorderLayout.CENTER);
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

