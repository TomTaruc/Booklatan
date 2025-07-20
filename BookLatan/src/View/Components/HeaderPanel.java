/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Joseph Rey
 */
public class HeaderPanel extends JPanel {
    private Color primaryColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private int paddingTop =  10;
    private int paddingBottom = 10;
    private int paddingLeft = 20;
    private int paddingRight = 20;
    private String title = "Title";
    private String subtitle = "Subtitle";
    private Dimension size;
    private JPanel textContent;
    
    public HeaderPanel(Dimension size) {
        this.size = size;
        initComponent();
    }
    
    public void initComponent() {
        
        JLabel headerTitle;
        JLabel headerSubTitle;
        
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setPreferredSize(size);
        this.setMaximumSize(this.getPreferredSize());
        this.setBorder(BorderFactory.createEmptyBorder(paddingTop, paddingLeft, paddingBottom, paddingRight));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setBackground(primaryColor);

        textContent = new JPanel();
        textContent.setLayout(new BoxLayout(textContent, BoxLayout.Y_AXIS));
        textContent.setOpaque(false);

        headerTitle = new JLabel(title);
        headerTitle.setFont(primaryFont.deriveFont(Font.BOLD, 30));
        headerSubTitle = new JLabel(subtitle);
        headerSubTitle.setFont(primaryFont.deriveFont(Font.BOLD, 25));
        textContent.add(headerTitle);
        textContent.add(headerSubTitle);

        this.add(textContent);
    }
    
    // **** Setters ****

    public void setTitle(String title) {
        this.title = title;
        this.remove(textContent);
        initComponent();
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        this.remove(textContent);
        initComponent();
    }
    
}
