/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 *
 * @author Joseph Rey
 */
public class CustomButton extends JButton {
    
    Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    Color primaryColor = new Color(224, 224, 224);
    Color clickedColor = new Color(primaryColor.getRed(), primaryColor.getGreen(), primaryColor.getBlue(), 190);
    Color hoverColor = new Color(220, 220, 220);
    int borderRadius = 50;
    
    public CustomButton(String text) {
        super(text);
        initComponent();
    }
    
    private void initComponent() {
        //Color
        this.setOpaque(true);
        this.setForeground(Color.black);
        this.setContentAreaFilled(false);
        //Size
        this.setPreferredSize(new Dimension(150, 60));
        this.setMaximumSize(this.getPreferredSize());
        //Padding and Margins

        //Layout
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Text
        
        //Others        
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if(!this.getModel().isArmed()) {
            g2.setColor((this.getModel().isRollover() ? hoverColor : primaryColor));
        }
        else {
            g2.setColor(clickedColor);
        }
        g2.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, borderRadius, borderRadius);
        g2.setColor(this.getForeground());
        g2.setFont(primaryFont);
        FontMetrics fm = g2.getFontMetrics(primaryFont);
        float x = (this.getWidth() - fm.stringWidth(this.getText())) / 2;
        float y = (this.getHeight() + fm.getAscent() - fm.getDescent()) / 2;
        g2.drawString(this.getText(), x, y);
        g2.dispose();
    }
    
 

    @Override
    public boolean contains(int x, int y) {
        int arc = 30;
        Shape round = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, arc, arc);
        return round.contains(x, y);
    }

    public Font getPrimaryFont() {
        return primaryFont;
    }

    public void setPrimaryFont(Font primaryFont) {
        this.primaryFont = primaryFont;
    }

    public Color getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(Color primaryColor) {
        this.primaryColor = primaryColor;
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    public Color getClickedColor() {
        return clickedColor;
    }

    public void setClickedColor(Color clickedColor) {
        this.clickedColor = clickedColor;
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }
    
    
    
    
    
    
    
}
