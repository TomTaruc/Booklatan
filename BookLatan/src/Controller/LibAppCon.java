/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import View.*;
import Model.*;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;


/**
 *
 * @author Joseph Rey
 */
public class LibAppCon {
    private LibrarianApplication view;
    
    public LibAppCon(LibrarianApplication view) {
        this.view = view;
        
        Map<String, JButton> btns = view.sidebar.getMenuButtons();
        btns.get("Dashboard").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "dashboard"));
        btns.get("Books").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "books"));
        btns.get("Members").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "members"));
        btns.get("Loans").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "loans"));
        btns.get("Reservations").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "reservations"));
        btns.get("Fines").addActionListener(e -> view.mainPanelLayout.show(view.mainPanel, "fines"));
    }
    
    public void openApp() {
        view.setVisible(true);
    }
    
    
    
}
