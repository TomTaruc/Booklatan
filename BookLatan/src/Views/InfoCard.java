/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Joseph Rey
 */
public class InfoCard extends JPanel {
    private int radius = 20;
    Color primaryColor;
    
    public InfoCard(String title, String info, Color color) {
        primaryColor = color;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(350, 150));
        this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel jTitle = new JLabel(title);
        jTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        jTitle.setForeground(Color.WHITE);
        
        
        JLabel jInfo = new JLabel(info);
        jInfo.setFont(new Font("Tahoma", Font.BOLD , 40));
        jInfo.setForeground(Color.WHITE);
        
        this.add(Box.createVerticalGlue());
        this.add(jTitle);
        this.add(Box.createVerticalStrut(5));
        this.add(jInfo);        
        this.add(Box.createVerticalGlue());
        this.setOpaque(false);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Graphics2D g2 = (Graphics2D) g.create();
        
        Color c1 = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 100);
        Color c2 = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 200);
        
        int width = this.getWidth();
        int height = this.getHeight();

        GradientPaint gp = new GradientPaint(0,0,c1, width, 0, c2);
        g2.setPaint(gp);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        
        g2.dispose();
    }
    
    
    
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        Graphics2D g2 = (Graphics2D) g.create();
        
        Color c1 = new Color(163, 206, 241, 50);
        
        g2.setColor(c1);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
        
    }
}
