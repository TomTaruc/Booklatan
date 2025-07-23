/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Applications;

import Model.User;
import Model.UserMemberDAO;


/**
 *
 * @author Dinel
 */
public class MemAppCon {
    private MemberApplication view;
    private User user;
    private UserMemberDAO userDAO;

    public MemAppCon(MemberApplication view, User user) {
        this.view = view;
        this.user = user;
        this.userDAO = new UserMemberDAO();
        this.view.sidebar.addUserInfo(this.user.getUsername(), userDAO.getMemberIDByUSerID(user.getUserId()));
        // Add member-specific listeners if needed
    }

    public void openApp() {
        view.setVisible(true);
    }
}