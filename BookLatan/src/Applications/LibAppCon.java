/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Components.Dashboards.LibDashCon;
import Components.Login.LoginController;
import Components.Managers.FinesManagerController;
import Components.Managers.LoanManagerController;
import Components.Managers.MemberManagerController;
import Components.Managers.ReservationsManagerController;
import Model.*;
import java.util.Map;
import javax.swing.*;


/**
 *
 * @author Joseph Rey
 */
public class LibAppCon {
    public LibrarianApplication view;
    private MemberManagerController memCon;
    private LibDashCon dashCon;
    private UserStaffDAO userDAO;
    private FinesManagerController fineCon;
    private ReservationsManagerController reservationsCon;
    private LoanManagerController loanCon;
    
    public LibAppCon(LibrarianApplication view, User user) {
        userDAO = new UserStaffDAO();
        this.view = view;
        view.sidebar.addUserInfo(user.getUsername(), userDAO.getStaffIDByUserID(user.getUserId()));
        this.memCon = new MemberManagerController(view.members, false);
        this.dashCon = new LibDashCon(view.dashboard);
        this.fineCon = new FinesManagerController(view.fines, userDAO.getStaffByUserID(user.getUserId()));
        this.reservationsCon = new ReservationsManagerController(view.reservations, false, userDAO.getStaffByUserID(user.getUserId()));
        this.loanCon = new LoanManagerController (view.loans);
        attachListeners();
        
    }
    
    public void openApp() {
        view.setVisible(true);
    }
    
    private void attachListeners() {
        Map<String, JButton> btns = view.sidebar.getMenuButtons();
        btns.get("Dashboard").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "dashboard"));
        btns.get("Books").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "books"));
        btns.get("Members").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "members"));
        btns.get("Loans").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "loans"));
        btns.get("Reservations").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "reservations"));
        btns.get("Fines").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "fines"));   
        btns.get("Logout").addActionListener(e -> {
           int answer = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
           if(answer == JOptionPane.YES_OPTION) {
               new LoginController();
               view.dispose();
           }
        });
    }
}
