
package Components.Managers;

import Model.UserMemberDAO;
import Utilities.Design;
import View.Components.CustomButton;
import View.Components.CustomComboBox;
import View.Components.HeaderPanel;
import Control.Forms.FineForm;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import View.Components.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * @author Tom
 * @author Joseph
 */
public class FinesManager extends JPanel {

    private FinesManagerController controller;
    private DefaultTableModel finesTableModel;
    private BorderlessTable finesTable;
    private JTextField txtSearch;
    private JComboBox<String> cmbStatusFilter;
    public CustomButton issueBtn;
    private JButton btnMarkAsPaid;
    private JButton btnDeleteFine;
    private JButton btnRefresh;
    private JButton btnSearch;

    private Color headerTextColor = Color.BLACK;

    private Color issueFineBtnColor = new Color(59, 130, 246);
    private Color markAsPaidBtnColor = new Color(34, 197, 94);
    private Color deleteFineBtnColor = new Color(239, 68, 68);
    private Color searchRefreshBtnColor = new Color(59, 130, 246);

    private final String[] TABLE_COLUMN_NAMES = {
        "Fine ID", "Member Name", "Book Title", "Due Date", "Return Date",
        "Days Overdue", "Amount", "Status"
    };

    public FinesManager() {
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
        

        cmbStatusFilter = new JComboBox<>(new String[]{"All Status", "Pending", "Paid"});
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

  private void setupFinesTable() {
        JTableHeader tHeader = finesTable.getTableHeader();
        tHeader.setFont(new Font("Segoe UI", Font.BOLD, 15));
        tHeader.setBackground(new Color(220, 220, 220));
        tHeader.setForeground(headerTextColor);
        tHeader.setOpaque(true);
        tHeader.setReorderingAllowed(false);
        tHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Segoe UI", Font.BOLD, 15));
                label.setOpaque(true);
                label.setBackground(new Color(220, 220, 220));
                label.setForeground(headerTextColor);
                label.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                label.setHorizontalAlignment(CENTER);
                return label;
            }
        });

        finesTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? Design.PRIME_COLOR : new Color(230, 230, 230)));
                c.setForeground(Color.BLACK);
                ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                return c;
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < finesTable.getColumnCount(); i++) {
            if (finesTable.getColumnModel().getColumn(i).getHeaderValue().toString().toLowerCase().contains("date")) {
                finesTable.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
                    @Override
                    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                        if (value instanceof Date) {
                            value = dateFormat.format(value);
                        }
                        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                        c.setBackground(isSelected ? table.getSelectionBackground() : (row % 2 == 0 ? Design.PRIME_COLOR : new Color(230, 230, 230)));
                        c.setForeground(Color.BLACK);
                        ((JLabel)c).setBorder(new EmptyBorder(5, 10, 5, 10));
                        return c;
                    }
                });
            }
        }
        
        finesTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        finesTable.getColumnModel().getColumn(1).setPreferredWidth(180);
        finesTable.getColumnModel().getColumn(2).setPreferredWidth(250);
        finesTable.getColumnModel().getColumn(3).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(4).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(5).setPreferredWidth(120);
        finesTable.getColumnModel().getColumn(6).setPreferredWidth(100);
        finesTable.getColumnModel().getColumn(7).setPreferredWidth(100);
    }
}