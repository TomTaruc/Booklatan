/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Designs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.table.*;

/**
 *
 * @author Joseph Rey
 */
public class BorderlessTable extends JTable {
    private Color tableColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    private int rowHeight = 50;
    private int hoveredRow = -1;  // FIXED: default should be -1

    public BorderlessTable() {
        // Default data
        super(new DefaultTableModel(
            new Object[][] {{"Data 1", "Data 2", "Data 3", "Data 4", "Data 5"}},
            new String[] {"Column 1", "Column 2", "Column 3", "Column 4", "Column 5"}
        ));
        this.initComponent();
    }

    public void initComponent() {
        JTableHeader header;
        this.setFont(primaryFont);
        this.setRowHeight(rowHeight);
        this.setShowGrid(false);
        this.setIntercellSpacing(new Dimension(0, 0));
        this.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Header setup
        header = this.getTableHeader();
        header.setOpaque(true);
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
        header.setDefaultRenderer(new HeaderDesign());

        // Content column setup
        this.getColumnModel().setColumnMargin(0);
        for (int i = 0; i < this.getColumnCount(); i++) {
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

    // ***** Setters *****
    public void setTableColor(Color tableColor) {
        this.tableColor = tableColor;
        this.repaint();
    }

    @Override
    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
        super.setRowHeight(rowHeight); // ensure it actually applies
    }

    // ***** Getters *****
    @Override
    public int getRowHeight() {
        return rowHeight;
    }

    // ***** Hover Detection (Fix) *****
    @Override
    protected void processMouseMotionEvent(MouseEvent e) {
        super.processMouseMotionEvent(e);
        int row = rowAtPoint(e.getPoint());
        if (row != hoveredRow) {
            hoveredRow = row;
            repaint();
        }
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_EXITED) {
            hoveredRow = -1;
            repaint();
        }
    }

    // ***** Classes *****
    private class HeaderDesign extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value.toString());
            label.setFont(primaryFont);
            label.setOpaque(true);
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

            // HIGHLIGHT LOGIC
            if (isSelected) {
                label.setBackground(table.getSelectionBackground());
            } else if (row == hoveredRow) {
                label.setBackground(new Color(220, 240, 255)); // Hover color
            } else {
                label.setBackground(tableColor);
            }

            label.setForeground(Color.BLACK);
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

            return label;
        }
    }
}
