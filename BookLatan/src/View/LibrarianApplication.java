/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;
import View.Components.*;
import javax.swing.*;
import java.awt.*;
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
    public Sidebar sidebar;
    public CardLayout mainPanelLayout;
    public JPanel mainPanel;
    
    public LibrarianApplication() {
        addSideBar();
        addMainPanel();
    }
    
    
    
    @Override
    protected void addSideBar() {
        Map<String, String> menuItems = new LinkedHashMap<>();
        menuItems.put("Dashboard", "./src/Images/dashboard2.png");
        menuItems.put("Books", "./src/Images/bookcataglo2.png");
        menuItems.put("Members", "./src/Images/members.png");
        menuItems.put("Loans", "./src/Images/loanbooks.png");
        menuItems.put("Reservations", "./src/Images/bookreservation.png");
        menuItems.put("Fines", "./src/Images/payments.png");
        sidebar = new Sidebar(this.getSize());
        // TASK: REPLACE THE CODE UNDER THIS SO THAT
        // EVERYTIME A USER LOGIN THIER USER NAME
        // AND USER NO (MEMBERID/STAFFID) IS DISPLAYED.
        // ON THE SIDEBAR. For: Dinel From: Joseph
        sidebar.addUserInfo("John", 1);
        sidebar.addMenuItems(menuItems);
        this.add(sidebar, BorderLayout.WEST);
        this.revalidate();
        this.repaint();
    }
    
    private void addMainPanel() {
        mainPanelLayout = new CardLayout();
        mainPanel = new JPanel(mainPanelLayout);
        JPanel dashboard = new LibDashboard(new Dimension(this.getWidth() - 200, this.getHeight()));
        JPanel books = new JPanel();
        JPanel members = new MembersManager(new Dimension(this.getWidth() - 200, this.getHeight()));
        JPanel loans = new JPanel();
        JPanel reservations = new JPanel();
        JPanel fines = new FinesPanel();
        
        books.setBackground(Color.black);
        loans.setBackground(Color.blue);
        reservations.setBackground(Color.green);
        fines.setBackground(Color.pink);
        
        
        mainPanel.add(dashboard, "dashboard");
        mainPanel.add(books, "books");
        mainPanel.add(members, "members");
        mainPanel.add(loans, "loans");
        mainPanel.add(reservations, "reservations");
        mainPanel.add(fines, "fines");
        
        this.add(mainPanel);
    }
}
