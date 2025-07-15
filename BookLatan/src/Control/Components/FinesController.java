package Control.Components;

import Model.Fine;
import Model.FineDAO;
import View.Components.FinesPanel;

import javax.swing.table.DefaultTableModel;
import java.util.List;

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

        List<Fine> fines = model.getFines(searchTerm, statusFilter);

        for (Fine fine : fines) {
            Object[] row = {
                    fine.getFineID(),
                    fine.getStaffID(),
                    fine.getMemberID(),
                    fine.getMemberName(),
                    fine.getAmount(),
                    fine.getReason(),
                    fine.getDateIssued(),
                    fine.get_status(),
                    fine.getBook_title(),
                    fine.getDue_date(),
                    fine.getReturn_date(),
                    fine.getDays_overdue(),
                    fine.getIsbn(),
                    fine.getPaid_date(),
                    fine.getDescription()
            };
            tableModel.addRow(row);
        }
    }

    public void payFine(String fineID) {
        if (model.payFine(fineID)) {
            view.displayMessage("Fine with ID " + fineID + " has been marked as Paid.", "Fine Paid", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        } else {
            view.displayMessage("Failed to mark fine with ID " + fineID + " as Paid.", "Payment Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }
}
