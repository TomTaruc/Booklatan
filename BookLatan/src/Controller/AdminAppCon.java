/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Control.Components.MemberManagerController;
import Control.Components.StaffManagerController;
import View.AdminApplication;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import View.Components.BookManager; // dinel
/**
 *
 * @author Dinel
 * @author Joseph
 */
public class AdminAppCon implements AppController{
    public AdminApplication view;
    private MemberManagerController memCon;
    private StaffManagerController staffCon;
    
    public AdminAppCon(AdminApplication view) {
        this.view = view;
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
               view.dispose();
           }
        });
    }
}
