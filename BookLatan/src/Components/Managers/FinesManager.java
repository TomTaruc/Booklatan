
package Components.Managers;

import Components.Designs.ModernScrollPane;
import Components.Designs.BorderlessTable;
import Utilities.Design;
import Components.Designs.CustomButton;
import Components.Designs.CustomComboBox;
import Components.Designs.HeaderPanel;
import Model.FineStatus;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * @author Tom
 * @author Joseph
 */
public class FinesManager extends JPanel {

    public DefaultTableModel finesTableModel;
    public BorderlessTable finesTable;
    public JTextField txtSearch;
    public JComboBox<FineStatus> cmbStatusFilter;
    public CustomButton issueBtn;
    public JButton btnMarkAsPaid;
    public JButton btnDeleteFine;
    public JButton btnRefresh;

    private final String[] TABLE_COLUMN_NAMES = {
        "#", "Member Name", "Amount", "Status", "Description"
    };

    public FinesManager() {
        initPanel();
        initComponents();
    }
    
    private void initPanel() {
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Design.PRIME_COLOR);
    }
    
    private void initComponents() {
        
        HeaderPanel header = new HeaderPanel(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        header.setTitle("Fine Manager");
        header.setSubtitle("Issue, Manage, and Delete Fines");
        issueBtn = new CustomButton("Issue A Fine");
        issueBtn.setPrimaryColor(Design.BTN2);
        issueBtn.setHoverColor(Design.BTN2_HOVER);
        header.add(Box.createHorizontalGlue());
        header.add(issueBtn);
        this.add(header);
        
        JPanel searchFilterPanel = new JPanel();
        searchFilterPanel.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        searchFilterPanel.setMaximumSize(searchFilterPanel.getPreferredSize());
        searchFilterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchFilterPanel.setLayout(new BoxLayout(searchFilterPanel, BoxLayout.X_AXIS));
        searchFilterPanel.setBackground(Design.PRIME_COLOR);

        txtSearch = new JTextField();
        txtSearch.setText("Search Member");
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black), 
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        txtSearch.setFont(Design.PRIME_FONT);
        txtSearch.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width-150, 50));
        txtSearch.setMaximumSize(txtSearch.getPreferredSize());
        txtSearch.setForeground(Color.LIGHT_GRAY);
        Design.addSearchEffect(txtSearch, "Search Member");
        

        cmbStatusFilter = new JComboBox<>(FineStatus.values());
        cmbStatusFilter.setFont(Design.PRIME_FONT);
        cmbStatusFilter.setPreferredSize(new Dimension(200, 50));
        cmbStatusFilter.setMaximumSize(cmbStatusFilter.getPreferredSize());
        cmbStatusFilter.setBorder(BorderFactory.createLineBorder(Color.black));
        cmbStatusFilter.setUI(new CustomComboBox());
        searchFilterPanel.add(txtSearch);
        searchFilterPanel.add(Box.createHorizontalStrut(10));
        searchFilterPanel.add(cmbStatusFilter);
        this.add(searchFilterPanel);
        
        finesTable = new BorderlessTable();
        finesTableModel = new DefaultTableModel(new Object[][] {},this.TABLE_COLUMN_NAMES);
        finesTable.changeModel(finesTableModel);
        ModernScrollPane scrollTable = new ModernScrollPane(finesTable);
        this.add(scrollTable);        
    }
}