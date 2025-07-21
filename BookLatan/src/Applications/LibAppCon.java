/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Components.Dashboards.LibDashCon;
import Components.Login.LoginController;
import Components.Managers.FinesManagerController;
import Components.Managers.MemberManagerController;
import Control.Forms.RegisterMemberCon;
import Model.*;
import Control.Forms.RegistrationForm;
import java.awt.CardLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Joseph Rey
 */
public class LibAppCon implements AppController {
    public LibrarianApplication view;
    private MemberManagerController memCon;
    private LibDashCon dashCon;
    private FinesManagerController fineCon;
    
    public LibAppCon(LibrarianApplication view) {
        this.view = view;
        this.memCon = new MemberManagerController(view.members, false);
        this.dashCon = new LibDashCon(view.dashboard);
        this.fineCon = new FinesManagerController(view.fines);
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
