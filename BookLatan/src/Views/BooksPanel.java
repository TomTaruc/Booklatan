/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

/**
 *
 * @author Joseph Rey
 */
public class BooksPanel extends JPanel {
    Color primaryColor = Color.pink;    
    
    public BooksPanel(JFrame frame) {
        this.setPreferredSize(new Dimension(frame.getSize().width - 200, frame.getSize().height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);
    }
    
}
