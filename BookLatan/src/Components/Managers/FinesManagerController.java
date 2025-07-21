package Components.Managers;

import Model.Fine;
import Model.FineDAO;
import Components.Managers.FinesManager;
import Control.Forms.FineForm;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Date;

public class FinesManagerController {

    private FineDAO model;
    private FinesManager view;

    public FinesManagerController(FinesManager view) {
        this.model = new FineDAO();
        this.view = view;
        
        view.issueBtn.addActionListener(e -> {
            FineForm form = new FineForm();
            form.setVisible(true);
        });
    }

    
}