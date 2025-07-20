package Control.Components;

import Model.Fine;
import Model.FineDAO;
import View.Components.FinesPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;

public class FinesController {

    private FineDAO model;
    private FinesPanel view;

    public FinesController(FineDAO model, FinesPanel view) {
        this.model = model;
        this.view = view;
    }

    public FineDAO getModel() {
        return model;
    }

    public void displayFines(DefaultTableModel tableModel, String searchTerm, String statusFilter) {
        tableModel.setRowCount(0);

        if (searchTerm != null && searchTerm.trim().isEmpty()) {
            searchTerm = null;
        }
        
        if ("All".equals(statusFilter) || "All Status".equals(statusFilter)) {
            statusFilter = null;
        }

        List<Fine> fines = model.getFines(searchTerm, statusFilter);

        for (Fine fine : fines) {
            Object[] row = {
                fine.getFineID(),
                fine.getMemberName(),
                fine.getBook_title(),
                fine.getDue_date(),
                fine.getReturn_date(),
                fine.getDays_overdue(),
                "₱" + String.format("%.2f", fine.getAmount()),
                fine.get_status()
            };
            tableModel.addRow(row);
        }
    }

    public void payFine(int fineID) {
        if (model.payFine(fineID)) {
            view.displayMessage("Fine with ID " + fineID + " has been marked as Paid.", "Fine Paid", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.displayMessage("Failed to mark fine with ID " + fineID + " as Paid.", "Payment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteFine(int fineID) {
        if (model.deleteFine(fineID)) {
            view.displayMessage("Fine with ID " + fineID + " has been successfully deleted.", "Fine Deleted", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.displayMessage("Failed to delete fine with ID " + fineID + ".", "Deletion Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}