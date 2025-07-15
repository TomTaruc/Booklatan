/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Control.Components.MemberManagerController;
import Control.Components.RegisterMemberCon;
import View.*;
import Model.*;
import View.Components.RegistrationFrame;
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
    
    public LibAppCon() {
        this.view = new LibrarianApplication();
        this.memCon = new MemberManagerController(view.members, false);
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
               view.dispose();
           }
        });
    }
}
