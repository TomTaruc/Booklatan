/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Model.User;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.Dimension;

/**
 * Example class showing how to use BookManager with database connectivity
 * @author Dinel
 */
public class BookManagerExample {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Book Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(new Dimension(1200, 800));
            frame.setLocationRelativeTo(null);
            
            // Create BookManager with ADMIN privileges (can edit books)
            BookManager bookManager = new BookManager(new Dimension(1200, 800), User.UserType.ADMIN);
            
            // Create and connect the controller to handle database operations
            BookManagerController controller = new BookManagerController(bookManager);
            
            // Add the BookManager to the frame
            frame.add(bookManager);
            
            // Display the frame
            frame.setVisible(true);
        });
    }
} 