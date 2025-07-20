package Controller;

import Control.Components.FinesController; // Tom: Import FinesController
import Control.Components.MemberManagerController;
import Control.Components.StaffManagerController;
import Model.FineDAO; // Import FineDAO
import View.AdminApplication;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import View.Components.FinesPanel; // Tom: Import FinesPanel

import View.Components.BookManager; // dinel
/**
 *
 * @author Dinel
 * @author Joseph
 */
public class AdminAppCon implements AppController {
    public AdminApplication view;
    private MemberManagerController memCon;
    private StaffManagerController staffCon;
    private FinesController finesCon; // Tom: Declare FinesController
    private FineDAO fineDAO; 

    public AdminAppCon(AdminApplication view) {
        this.view = view;
        this.memCon = new MemberManagerController(view.members, true);
        this.staffCon = new StaffManagerController(view.staff);

        // --- ADDITIONS FOR FINES PANEL ---
        this.fineDAO = new FineDAO();
        this.finesCon = new FinesController(this.fineDAO, view.fines); 
        view.fines.setController(this.finesCon); 

        attachListeners();
    }

    public void openApp() {
        view.setVisible(true);
    }

    private void attachListeners() {
        Map<String, JButton> btns = view.sidebar.getMenuButtons();
        btns.get("Dashboard").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "dashboard"));
        btns.get("Books").addActionListener(e ->    view.mainPanelLayout.show(view.mainPanel, "books"));
        btns.get("Members").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "members"));
        btns.get("Staff").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "staff"));
        btns.get("Fines").addActionListener(e -> {
            view.mainPanelLayout.show(view.mainPanel, "fines"); 
            view.fines.refreshFinesTable();
        });
        // --- END ADDITION ---

        btns.get("Logout").addActionListener(e -> {
            int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if(answer == JOptionPane.YES_OPTION) {
                view.dispose();
            }
        });
    }
}