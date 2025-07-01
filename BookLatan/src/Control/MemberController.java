/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Model.MemberDAO;
import Model.Member;
/**
 *
 * @author Joseph Rey
 */
public class MemberController {
    
    private MemberDAO model;
    
    public MemberController(MemberDAO model) {
        this.model = model;
    }
    
    public void registerMember(Member member) {
        model.addMember(member);
    }
    
    public void deleteMember(Member member) {
        model.deleteMember(member);
    }
    
    public void udpateMember(Member member) {
        model.updateMember(member);
    }
}
