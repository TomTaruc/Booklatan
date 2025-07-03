/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;
import Model.*;
import Control.*;
import java.time.LocalDate;
import io.github.cdimascio.dotenv.Dotenv;
import Main.*;
//import Views.*;
/**
 *
 * @author Joseph Rey
 */
public class Test {
    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        for(Member member : dao.getMembers()) {
            System.out.println(member.getUserID() + ": " + member.getUserName());
        }
        StaffApplication app = new StaffApplication();
        app.setVisible(true);
    }
}
