package fines_taruc;

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
