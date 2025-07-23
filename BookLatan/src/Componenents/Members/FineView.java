/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componenents.Members;

import Components.Designs.BorderlessTable;
import Components.Designs.CustomButton;
import Components.Designs.CustomComboBox;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import Model.FineStatus;
import Utilities.Design;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

/** this class and package is for members only. They are simply for viewing.
 *
 * @author Joseph Rey
 */
public class FineView extends JPanel {
    public DefaultTableModel finesTableModel;
    public BorderlessTable finesTable;
    public JFormattedTextField totalUnpaidField;
    public JComboBox<FineStatus> cmbStatusFilter;
    private JLabel totalUnpaidLabel;
    
    private final String[] TABLE_COLUMN_NAMES = {
        "Fine ID", "Amount", "Status", "Description", "Date Issued", "Due Date", "Date Paid"
    };

    public FineView() {
        initFrame();
        initComponents();
    }
    
    private void initFrame() {
        this.setLayout(new BorderLayout(10, 10));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Design.PRIME_COLOR);
    }
    
    private void initComponents() {
        
        HeaderPanel header = new HeaderPanel(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        header.setTitle("Fines");
        header.setSubtitle("View your fines here. Payment is done at the Library's counter.");
        this.add(header);
        
        JPanel searchFilterPanel = new JPanel();
        searchFilterPanel.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        searchFilterPanel.setMaximumSize(searchFilterPanel.getPreferredSize());
        searchFilterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        searchFilterPanel.setLayout(new BoxLayout(searchFilterPanel, BoxLayout.X_AXIS));
        searchFilterPanel.setBackground(Design.PRIME_COLOR);
        
        totalUnpaidLabel = new JLabel("Total Fines (Unpaid):");
        totalUnpaidLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMaximumFractionDigits(2);
        
        NumberFormatter formatter = new NumberFormatter(currencyFormat);
        formatter.setValueClass(Double.class);
        formatter.setAllowsInvalid(false); 
        formatter.setMinimum(0.0); 
        
        totalUnpaidField = new JFormattedTextField(formatter);
        totalUnpaidField.setValue(0.0);
        totalUnpaidField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black), 
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        totalUnpaidField.setFont(Design.PRIME_FONT);
        totalUnpaidField.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width-150, 50));
        totalUnpaidField.setMaximumSize(totalUnpaidField.getPreferredSize());
        totalUnpaidField.setForeground(Color.BLACK);
        totalUnpaidField.setEditable(false);

        cmbStatusFilter = new JComboBox<>(FineStatus.values());
        cmbStatusFilter.setFont(Design.PRIME_FONT);
        cmbStatusFilter.setPreferredSize(new Dimension(200, 50));
        cmbStatusFilter.setMaximumSize(cmbStatusFilter.getPreferredSize());
        cmbStatusFilter.setBorder(BorderFactory.createLineBorder(Color.black));
        cmbStatusFilter.setUI(new CustomComboBox());
        searchFilterPanel.add(totalUnpaidLabel);
        searchFilterPanel.add(Box.createHorizontalStrut(10));
        searchFilterPanel.add(totalUnpaidField);
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
