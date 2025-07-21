/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;


/**
 *
 * @author Dinel
 */
public class MemAppCon {
    private MemberApplication view;

    public MemAppCon(MemberApplication view) {
        this.view = view;
        // Add member-specific listeners if needed
    }

    public void openApp() {
        view.setVisible(true);
    }
}