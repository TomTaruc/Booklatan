/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import Model.MemberUserDAO;
import Model.Member;
import Views.MemberView;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Joseph Rey
 */
public class MemberController {
    
    private MemberUserDAO model;
    private MemberView view;
    
    public MemberController(MemberUserDAO model, MemberView view) {
        this.model = model;
        this.view = view;
    }
    
    public void displayMembers(DefaultTableModel tblModel) {
        view.showMembers(tblModel, model.getMembers());
    };
    
    public void filterMembers(DefaultTableModel tblModel, String name, Member.MembershipStatus status) {
        view.showMembers(tblModel, model.getMembers(name, status));
    };
    
    public void filterMembers(DefaultTableModel tblModel, Member.MembershipStatus status) {
        view.showMembers(tblModel, model.getMembers(status));
    };
    
    public Member getMemberDetail(int memberNo) {
        return model.getMemberByID(memberNo);
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
    
    public int getTotalActiveMembers() {
        return model.countActiveMembers();
    }
}
