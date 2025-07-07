/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
/**
 *
 * @author Joseph Rey
 */
public class CustomScrollPane extends JScrollPane {
    public CustomScrollPane(Component view) {
        super(view);
        initComponent();
    }
    
    public void initComponent() {
        this.setViewportBorder(null);
        this.setBorder(BorderFactory.createEmptyBorder());
        this.getVerticalScrollBar().setUI(new CustomScrollBar());
        this.getHorizontalScrollBar().setUI(new CustomScrollBar());
        
    }
    
    public class CustomScrollBar extends BasicScrollBarUI {
        protected void configureScrollBarColors() {
                thumbColor = new Color(150, 150, 150, 180);  // Semi-transparent gray
                trackColor = new Color(240, 240, 240);
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(trackColor);
                g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
                g2.dispose();
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Remove arrow buttons
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Remove arrow buttons
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
    }
}
