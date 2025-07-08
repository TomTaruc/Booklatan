/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import Views.UsersPanel;
import Views.Application;
import Views.DashboardPanel;
import Model.*;
import Control.*;
import Views.*;
import Views.CustomScrollPane;
import Views.Sidebar;
import Views.BorderlessTable;
import Views.InfoCard;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
/**
 *
 * @author Joseph Rey
 */
public class LibrarianApplication extends Application{
    private final Color primaryColor = new Color(245, 245, 245);

    public LibrarianApplication() {
        super();
        this.addSideBar();
        this.addMainPanel();
        this.setVisible(true);
    }
    
    
    public void addSideBar() {
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
    
    private void addMainPanel() {
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        
        DashboardPanel panel1 = new DashboardPanel(this);
        BooksPanel panel2 = new BooksPanel(this);
        UsersPanel panel3 = new UsersPanel(this);
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        
        panel4.setBackground(Color.blue);
        panel5.setBackground(Color.green);
        panel6.setBackground(Color.pink);
        
        
        cardPanel.add(panel1, "card1");
        cardPanel.add(panel2, "card2");
        cardPanel.add(panel3, "card3");
        cardPanel.add(panel4, "card4");
        cardPanel.add(panel5, "card5");
        cardPanel.add(panel6, "card6");
        
        
        btns.get(0).addActionListener(e -> cardLayout.show(cardPanel, "card1"));
        btns.get(1).addActionListener(e -> cardLayout.show(cardPanel, "card2"));
        btns.get(2).addActionListener(e -> cardLayout.show(cardPanel, "card3"));
        btns.get(3).addActionListener(e -> cardLayout.show(cardPanel, "card4"));
        btns.get(4).addActionListener(e -> cardLayout.show(cardPanel, "card5"));
        btns.get(5).addActionListener(e -> cardLayout.show(cardPanel, "card6"));
        
        this.add(cardPanel);
    }
}
