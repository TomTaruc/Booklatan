/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/**
 *
 * @author Joseph Rey
 */
public class DashboardPanel extends JPanel{
    private Color primaryColor = new Color(245, 245, 245);
    public DashboardPanel(JFrame frame) {
        this.setPreferredSize(new Dimension(frame.getSize().width - 200, frame.getSize().height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);
        
        
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        header.setMaximumSize(header.getPreferredSize());
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBackground(primaryColor);
        
        JLabel headerTitle = new JLabel("Librarian's Dashboard");
        headerTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
        JLabel headerSubTitle = new JLabel("Overview");
        headerSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        header.add(headerTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(headerSubTitle);
        
        
        JPanel cardHolder = new JPanel();
        cardHolder.setBackground(primaryColor);
        cardHolder.setPreferredSize(new Dimension(this.getPreferredSize().width, 250));
        cardHolder.setMaximumSize(cardHolder.getPreferredSize());
        cardHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        ArrayList<InfoCard> infocards = new ArrayList<>();
        infocards.add(new InfoCard("Active Members", "200", new Color(114, 90, 193)));
        infocards.add(new InfoCard("Books Loaned", "100", new Color(96, 150, 186)));
        infocards.add(new InfoCard("Books Due Today", "3", new Color(229, 56, 59)));
        infocards.add(new InfoCard("Total Unpaid Fines", "30", new Color(107, 144, 128)));
        for(InfoCard card : infocards) {
            cardHolder.add(Box.createVerticalStrut(10));
            cardHolder.add(card);
            cardHolder.add(Box.createVerticalStrut(10));
        }
        
        
        this.add(header);
        this.add(Box.createVerticalStrut(5));
        this.add(cardHolder);
        
    }
}
