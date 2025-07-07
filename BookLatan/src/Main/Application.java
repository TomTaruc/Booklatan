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
public abstract class Application extends JFrame {
    protected ArrayList<JButton> btns = new ArrayList<>();
    
    public Application() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.setTitle("BookLatan");
        this.setName("Staff Application");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("./src/Images/userimage.png").getImage());
        this.setPreferredSize(new Dimension(screenSize.width, screenSize.height - 20));
        this.setSize(this.getPreferredSize());
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new Color(245, 245, 245));
        this.setResizable(false); 
    }
    
    abstract public void addSideBar();
}
