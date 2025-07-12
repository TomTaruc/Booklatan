/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author Joseph Rey
 */
public abstract class Application extends JFrame {
    protected Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private String title = "BookLatan";
    private Image appIcon = new ImageIcon("./src/Images/userimage.png").getImage();
    protected Color primaryColor = new Color(245,245,245);
    
    public Application() {
        initComponent();
    }
    
    protected void initComponent() {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(appIcon);
        setPreferredSize(new Dimension(screenSize.width, screenSize.height - 20));
        setSize(getPreferredSize());
        setLocationRelativeTo(null);
        getContentPane().setBackground(primaryColor);
        setResizable(false);
        setVisible(true);
    }
    
    abstract protected void addSideBar();
}
