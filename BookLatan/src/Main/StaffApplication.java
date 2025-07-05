/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Joseph Rey
 */
public class StaffApplication extends JFrame{
    private ArrayList<JButton> btns = new ArrayList<>();
    
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
        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);
        
        DashboardPanel panel1 = new DashboardPanel(this);
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.red);
        
        
        cardPanel.add(panel1, "card1");
        cardPanel.add(panel2, "card2");
        
        btns.get(0).addActionListener(e -> cardLayout.show(cardPanel, "card1"));
        btns.get(1).addActionListener(e -> cardLayout.show(cardPanel, "card2"));
        
        this.add(cardPanel);
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
