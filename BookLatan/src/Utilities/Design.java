/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * This class is used to store all shared styles <br>
 * (e.g. colors, fonts, etc).
 * @author Joseph Rey
 */
public class Design {
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension FRAME_SIZE = new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height - 20);
    public static final Dimension MAIN_PANEL_SIZE = new Dimension(FRAME_SIZE.width - 200, FRAME_SIZE.height);
    public static final Font PRIME_FONT = new Font("Tahoma", Font.PLAIN, 16);
    public static final Color PRIME_COLOR = new Color(245, 243, 244);
    public static final Image appIcon = new ImageIcon("./src/Images/userimage.png").getImage();
    
    //Joseph Colors
    public static final Color CLR1 = new Color(33, 37, 41);
    public static final Color CLR2 = new Color(108, 117, 125);
    public static final Color BTN1 = new Color(168, 213, 186);
    public static final Color BTN1_HOVER = new Color(148, 193, 166);
    public static final Color BTN2 = new Color(163, 196, 243);
    public static final Color BTN2_HOVER = new Color(143, 176, 223);
    public static final Color BTN3 = new Color(247, 161, 161);
    public static final Color BTN3_HOVER = new Color(227, 141, 141);
    //Dinel Colors
    public static final Color HOVER_BTN_DINEL = CLR2;
    public static final Color BTN_COLOR_DINEL = CLR1;
    public static final Color ACCENT_COLOR_DINEL = new Color(54, 84, 150);
    public static final Color TEXT_COLOR_DINEL = new Color(100, 100, 100);
        
    //Toms Colors
    
    //Albert Colors

    //Khryzan Colors
    
    
    //Effects
    public static void addHoverEffect(JButton btn, Color defaultColor, Color hoverColor) {
        btn.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(defaultColor);
            }            
        });    
    }
    
    public static void addSearchEffect(JTextField field, String defaultText) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if(field.getText().isEmpty()) {
                    field.setText(defaultText);
                    field.setForeground(Color.LIGHT_GRAY);
                }
            }

            @Override
            public void focusGained(FocusEvent e) {
                if( field.getText().equalsIgnoreCase(defaultText)) {
                    field.setText("");
                    field.setForeground(TEXT_COLOR_DINEL);
                }
            }
        });
    }
}
