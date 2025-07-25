/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Dashboards;

import Model.FineDAO;
import Model.UserMemberDAO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joseph Rey
 */
public class LibDashCon {
    private LibDashboard view;
    private UserMemberDAO memDAO;
    private FineDAO fineDAO;
    
    public LibDashCon(LibDashboard view) {
        this.view = view;
        this.memDAO = new UserMemberDAO();
        this.fineDAO = new FineDAO();
        
        String[] columnNames = {"#", "Name", "Book Title", "Date Loaned", "Date Due"};
        Object[][] data = {
            {"200001", "Kaiser Lycan", "How to kill a mocking bird", "12/08/2024", "12/09/2024"},
            {"200002", "Dinel Robles", "Legion Lover", "12/08/2024", "12/09/2024", "12/09, 2025"},
            {"200003", "Khryzna Advincula", "When a women kills", "12/08/2024", "12/09/2024"}
        };
        
        for(Object[] xdata : data) {
            view.recentlyLoanedModel.addRow(xdata);
        }
        
        while(view.recentlyLoanedModel.getRowCount() < 15) {
            view.recentlyLoanedModel.addRow(new Object[] {"-", "-", "-", "-", "-"});
        }
        
        view.infocards.get(0).updateContent(String.valueOf(memDAO.countActiveMembers()));
        view.infocards.get(3).updateContent(String.valueOf(fineDAO.countUnpaidFine()));
        
               
       
    }
}
