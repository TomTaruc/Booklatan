/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
/**
 *
 * @author Joseph Rey
 */
public class BorderlessTable extends JTable {
    private Color tableColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private int rowHeight = 50;
    
    
    public BorderlessTable() {
        //Default data
        super(new DefaultTableModel( new Object[][] {{"Data 1", "Data 2", "Data 3", "Data 4", "Data 5"}}, new String[] {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"}));
        this.initComponent();
        
        
    }
    
    public void initComponent() {
        JTableHeader header;
        this.setFont(primaryFont);
        this.setRowHeight(rowHeight);
        this.setShowGrid(false);
        this.setIntercellSpacing(new Dimension(0, 0));
        
        //Headaear SetUp;
        header = this.getTableHeader();
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setDefaultRenderer(new HeaderDesign ());
        
        
        // Content column setup
        this.getColumnModel().setColumnMargin(0);
        for(int i = 0; i < this.getColumnCount(); i++) {
            this.getColumnModel().getColumn(i).setCellRenderer(new ColumDesign());
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
    
    public void changeModel(DefaultTableModel model) {
        this.setModel(model);
        this.initComponent();
    }
    
    // **** Setters *****
    public void setTableColor(Color tableColor) {
        this.tableColor = tableColor;
        this.initComponent();
    }
    
    @Override
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    
    // **** Getters ****
    @Override
    public int getRowHeight() {
        return rowHeight;
    }
    
    // **** Classess ****
    private class HeaderDesign extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setFont(primaryFont);
            label.setOpaque((true));
            label.setBackground(new Color(230, 230, 230));
            label.setForeground(Color.BLACK);
            label.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            return label;
        }
    }
    
    private class ColumDesign extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {                    
            JLabel label = new JLabel(value != null ? value.toString() : "");
            label.setFont(primaryFont);
            label.setOpaque(true);
            label.setBackground(isSelected ? table.getSelectionBackground() : tableColor);
            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
            return label;
        }
    }
}
