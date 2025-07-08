/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Views;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import Model.Member;
/**
 *
 * @author Joseph Rey
 */
public class MemberView {
    public void showMembers(DefaultTableModel model, ArrayList<Member> members) {
        model.setRowCount(0);
        for(Member member : members) {
            model.addRow(new Object[] {String.format("%06d", member.getMemberID()), member.getName(), member.getStatus().toString().toLowerCase(), member.getEmail(), member.getDateJoined().toString()});
        }
        
        while(model.getRowCount() < 20) {
            model.addRow(new Object[] {"-", "-", "-", "-", "-"});
        }
    }
    
    public void filterMembers(DefaultTableModel model, ArrayList<Member> members) {
        model.setRowCount(0);
        for(Member member : members) {
            model.addRow(new Object[] {String.format("%06d", member.getMemberID()), member.getName(), member.getStatus().toString().toLowerCase(), member.getEmail(), member.getDateJoined().toString()});
        }
    }
}
