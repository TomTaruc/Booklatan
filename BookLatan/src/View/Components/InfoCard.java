/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.*;

/**
 *
 * @author Joseph Rey
 */
public class InfoCard extends JPanel{
    private int radius = 20;
    private Color primaryColor = new Color(199, 199, 199);
    private Color textColor = Color.WHITE;
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private Dimension size = new Dimension(350, 150);
    private int topPadding = 10;
    private int bottomPadding = 10;
    private int leftPadding = 20;
    private int rightPaddign = 20;
    private String title;
    private String info;
    
    public InfoCard(String title, String info) {
        this.title = title;
        this.info = info;
        initComponent();
    }
    
    public void initComponent() {
        JLabel jTitle;
        JLabel jInfo;
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(size);
        this.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setOpaque(false);
        
        jTitle = new JLabel(title);
        jTitle.setFont(primaryFont.deriveFont(Font.BOLD, 30));
        jTitle.setForeground(textColor);
        
        
        jInfo = new JLabel(info);
        jInfo.setFont(primaryFont.deriveFont(Font.BOLD, 40));
        jInfo.setForeground(textColor);
        
        this.add(Box.createVerticalGlue());
        this.add(jTitle);
        this.add(Box.createVerticalStrut(5));
        this.add(jInfo);        
        this.add(Box.createVerticalGlue());
    }

    // **** Setters ****
    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }
    
    
    // **** Paint Overrides/ Custom Design ****
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
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
        super.paintChildren(g);
        
        Graphics2D g2 = (Graphics2D) g.create();
        Color c1 = new Color(163, 206, 241, 50);
        
        g2.setColor(c1);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }
}
