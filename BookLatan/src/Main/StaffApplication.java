package Main;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import Views.FinesTab;
import Control.FinesController;
import Model.FineDAO;
import Model.DAO;

public class StaffApplication extends JFrame{
    private ArrayList<JButton> btns = new ArrayList<>();
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public StaffApplication() {
        this.setup();
        this.addSideBar();
        this.addMainPanel();
        this.setVisible(true);
    }

    private void setup() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode mode = gd.getDisplayMode();

        this.setTitle("BookLatan");
        this.setName("Staff Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("./src/Images/userimage.png").getImage());
        this.setPreferredSize(new Dimension(mode.getWidth(), mode.getHeight() - 20));
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setResizable(false);
    }

    private void addMainPanel() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        DashboardPanel dashboardPanel = new DashboardPanel(this);

        FineDAO fineDAO = new FineDAO();
        fineDAO.checkDetails();
        FinesTab finesTab = new FinesTab();
        FinesController finesController = new FinesController(fineDAO, finesTab);
        finesTab.setController(finesController);


        cardPanel.add(dashboardPanel, "Dashboard");
        cardPanel.add(finesTab, "Fines");

        btns.get(0).addActionListener(e -> {
            cardLayout.show(cardPanel, "Dashboard");
        });

        btns.get(5).addActionListener(e -> {
            cardLayout.show(cardPanel, "Fines");
            finesTab.refreshFinesTable();
        });

        this.add(cardPanel, BorderLayout.CENTER);

        cardLayout.show(cardPanel, "Dashboard");
    }

    private void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Members", "./src/Images/members.png");
        menuItems.put("Loans", "./src/Images/loanbooks.png");
        menuItems.put("Reservations", "./src/Images/bookreservation.png");
        menuItems.put("Fines", "./src/Images/payments.png");
        Sidebar sb = new Sidebar(this, menuItems);
        this.add(sb, BorderLayout.WEST);
        btns = sb.btns;
    }
}
