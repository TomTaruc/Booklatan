/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author motar
 */
import javax.swing.JFrame;
import javax.swing.SwingUtilities; 

public class Main extends JFrame {

    private FinesPanel finesPanel;

    public Main() {
        setTitle("BookLatan Fines Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); 
        setLocationRelativeTo(null); 

        finesPanel = new FinesPanel(); 
        add(finesPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}

