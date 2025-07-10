package Model;

import Control.FinesController;
import Views.FinesTab;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                FineDAO fineDAO = new FineDAO();

                fineDAO.checkDetails();

                JFrame mainFrame = new JFrame("Library Management System");
                mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                mainFrame.setSize(1200, 700);
                mainFrame.setLocationRelativeTo(null);

                FinesTab finesTab = new FinesTab();
                
                FinesController controller = new FinesController(fineDAO, finesTab);
                
                finesTab.setController(controller);

                mainFrame.getContentPane().add(finesTab, BorderLayout.CENTER);

                mainFrame.setVisible(true);

            } catch (Exception e) {
                System.err.println("Error starting Fines Tab application: " + e.getMessage());
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "Failed to start the Fines Tab application. Please check console for details.",
                        "Application Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
