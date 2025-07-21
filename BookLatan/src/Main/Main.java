/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Components.Login.LoginController;
import Applications.LibAppCon;
import Applications.LibrarianApplication;
import javax.swing.JOptionPane;

/**
 *
 * @author Joseph Rey
 */
public class Main {
    public static void main(String[] args) {
         try {
             LoginController login = new LoginController();
        }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                System.exit(0);
        }
    }
}
