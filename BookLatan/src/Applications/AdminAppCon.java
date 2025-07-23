/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Components.Login.LoginController;
import Components.Managers.MemberManagerController;
import Components.Managers.StaffManagerController;
import Model.User;
import Model.UserStaffDAO;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 *
 * @author Dinel
 * @author Joseph
 */
public class AdminAppCon{
    public AdminApplication view;
    private MemberManagerController memCon;
    private StaffManagerController staffCon;
    private UserStaffDAO userDAO;
    
    public AdminAppCon(AdminApplication view, User user) {
        this.view = view;
        this.userDAO = new UserStaffDAO();
        this.view.sidebar.addUserInfo(user.getUsername(), userDAO.getStaffIDByUserID(user.getUserId()));
        this.memCon = new MemberManagerController(view.members, true);
        this.staffCon = new StaffManagerController(view.staff);
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
        btns.get("Logout").addActionListener(e -> {
           int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
           if(answer == JOptionPane.YES_OPTION) {
               new LoginController();
               view.dispose();
           }
        });
    }
}
