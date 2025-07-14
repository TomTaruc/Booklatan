/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;
import View.AdminApplication;

/**
 *
 * @author Dinel
 */
public class AdminAppCon {
    private AdminApplication view;

    public AdminAppCon(AdminApplication view) {
        this.view = view;
        // Add any admin-specific listeners or setup here
    }

    public void openApp() {
        view.setVisible(true);
    }
}
