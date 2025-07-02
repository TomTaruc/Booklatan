/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Test;
import Model.*;
import Control.*;
import java.time.LocalDate;
import io.github.cdimascio.dotenv.Dotenv;
//import Views.*;
/**
 *
 * @author Joseph Rey
 */
public class Test {
    public static void main(String[] args) {
        MemberDAO dao = new MemberDAO();
        
        Member member = new Member();
        member.setName("Dinel Robles");
        member.setAddress("Taguig, Phlippines");
        member.setEmail("dcpr@gmail.com");
        member.setDateJoined(LocalDate.now());
        member.setPhone("123-123-123");
        member.setMemberID(4);
        member.setUserID(4);
        member.setStatus(Member.MembershipStatus.ACTIVE);
        
        
        dao.addMember(member);
        
    }
}
