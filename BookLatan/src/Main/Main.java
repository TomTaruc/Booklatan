/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Views.LoginFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class Main {
    public static void main(String[] args) {
        //TODO: Replace this code with login window and use login controller. FOR: DINEL, FROM JOSEPH
         try {
             LoginFrame app = new LoginFrame();
        }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                System.exit(0);
        }
    }
}
