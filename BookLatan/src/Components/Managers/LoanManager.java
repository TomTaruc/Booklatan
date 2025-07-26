/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Components.Designs.BorderlessTable;
import Components.Designs.CustomButton;
import Components.Designs.CustomComboBox;
import Components.Designs.HeaderPanel;
import Components.Designs.ModernScrollPane;
import Model.LoanStatus;
import Utilities.Design;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Joseph Rey
 */
public class LoanManager extends JPanel{
    
    private HeaderPanel header;
    private ModernScrollPane scroll;
    private JPanel searchPanel;
    public BorderlessTable table;
    public DefaultTableModel model;
    public JTextField searchbar;
    public JComboBox<LoanStatus> statusSelection;
    public CustomButton createLoanBtn;
    public final String SEARCH_PLACEHORDER = "Search member";
    private final String[] columnNames = {"#", "Member","Issue Date", "Due Date", "Return Date", "Status"};
    
    public LoanManager() {
        initPanel();
        initComponents();
        initLayout();
    }
    
    public void initPanel() {
        this.setPreferredSize(Design.MAIN_PANEL_SIZE);
        this.setBackground(Design.PRIME_COLOR);
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void initComponents() {
        header = new HeaderPanel(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        header.setTitle("Manage Loan");
        header.setSubtitle("Issue, Delete, and Update Loans");
        
        createLoanBtn = new CustomButton("Issue A Loan");
        createLoanBtn.setPrimaryColor(Design.BTN2);
        createLoanBtn.setHoverColor(Design.BTN2_HOVER);
        
        searchPanel = new JPanel();
        searchPanel.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 100));
        searchPanel.setMaximumSize(searchPanel.getPreferredSize());
        searchPanel.setBackground(Design.PRIME_COLOR);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        searchbar = new JTextField();
        searchbar.setText(SEARCH_PLACEHORDER);
        searchbar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.black), 
                BorderFactory.createEmptyBorder(0, 10, 0, 10)
        ));
        searchbar.setFont(Design.PRIME_FONT);
        searchbar.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width-150, 50));
        searchbar.setMaximumSize(searchbar.getPreferredSize());
        searchbar.setForeground(Color.LIGHT_GRAY);
        Design.addSearchEffect(searchbar, SEARCH_PLACEHORDER);
        
        statusSelection = new JComboBox<>(LoanStatus.values());
        statusSelection.setFont(Design.PRIME_FONT);
        statusSelection.setPreferredSize(new Dimension(200, 50));
        statusSelection.setMaximumSize(statusSelection.getPreferredSize());
        statusSelection.setBorder(BorderFactory.createLineBorder(Color.black));
        statusSelection.setUI(new CustomComboBox());
        
        table = new BorderlessTable();
        model = new DefaultTableModel(new Object[][] {},columnNames);
        table.changeModel(model);
        scroll = new ModernScrollPane(table);
        
    }
    
    public void initLayout() {
        
        header.add(Box.createHorizontalGlue());
        header.add(createLoanBtn);
        
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.add(searchbar, 0);
        searchPanel.add(Box.createHorizontalStrut(10), 1);
        searchPanel.add(statusSelection, 2);
        
        this.add(header, 0);
        this.add(searchPanel, 1);
        this.add(scroll); 
    }
}   
