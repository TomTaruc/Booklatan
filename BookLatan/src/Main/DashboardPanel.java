/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
/**
 *
 * @author Joseph Rey
 */
public class DashboardPanel extends JPanel{
    private Color primaryColor = new Color(245, 245, 245);
    public DashboardPanel(JFrame frame) {
        this.setPreferredSize(new Dimension(frame.getSize().width - 200, frame.getSize().height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);
        
        //Header 
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        header.setMaximumSize(header.getPreferredSize());
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBackground(primaryColor);
        
        JLabel headerTitle = new JLabel("Librarian's Dashboard");
        headerTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
        JLabel headerSubTitle = new JLabel("Overview");
        headerSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        header.add(headerTitle);
        header.add(Box.createVerticalStrut(5));
        header.add(headerSubTitle);
        
        //Overview
        JPanel cardHolder = new JPanel();
        cardHolder.setBackground(primaryColor);
        cardHolder.setPreferredSize(new Dimension(this.getPreferredSize().width, 200));
        cardHolder.setMaximumSize(cardHolder.getPreferredSize());
        cardHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        ArrayList<InfoCard> infocards = new ArrayList<>();
        infocards.add(new InfoCard("Active Members", "200", new Color(114, 90, 193)));
        infocards.add(new InfoCard("Books Loaned", "100", new Color(96, 150, 186)));
        infocards.add(new InfoCard("Books Due Today", "3", new Color(229, 56, 59)));
        infocards.add(new InfoCard("Total Unpaid Fines", "30", new Color(107, 144, 128)));
        for(InfoCard card : infocards) {
            cardHolder.add(Box.createVerticalStrut(10));
            cardHolder.add(card);
            cardHolder.add(Box.createVerticalStrut(10));
        }
        
        //Recent activities
        String[] columnNames = {"Activity", "Member Name", "Book Title", "Date"};
        
        Object[][] data = {
            {"Loaned", "Kaiser Lycan", "How to kill a mocking bird", "12/08/2024"},
            {"Reserved", "Dinel Robles", "Legion Lover", "12/08/2024"},
            {"Returned", "Khryzna Advincula", "When a women kills", "12/08/2024"}
        };
        
        JTable table = new JTable(new DefaultTableModel(data, columnNames)) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        
        
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0,0));
        table.setRowHeight(50);
        table.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
        JTableHeader tHeader = table.getTableHeader();
        tHeader.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tHeader.setBackground(new Color(230, 230, 230));
        tHeader.setForeground(Color.BLACK);
        tHeader.setOpaque(true);
        
        table.getTableHeader().setFont(new Font("Tahoma", Font.PLAIN, 16));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Tahoma", Font.BOLD, 16));
                label.setOpaque(true);
                label.setBackground(new Color(230, 230, 230));
                label.setForeground(Color.BLACK);
                label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                label.setHorizontalAlignment(LEFT);
                return label;
            }
            
        });
                
        for(int i = 0; i < columnNames.length; i++) {
             table.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel(new BorderLayout());
                panel.setBackground( isSelected ? table.getSelectionBackground() : primaryColor);
                panel.setBorder(new EmptyBorder(5, 10, 5, 10));
                
                JLabel label = new JLabel(value.toString());
                label.setFont(new Font("Tahoma", Font.PLAIN, 16));
                label.setOpaque(false);
                label.setForeground(Color.black);
                
                panel.add(label, BorderLayout.WEST);
                return panel;
            } 
            });
        }
        
        //Scrollable Container
        JScrollPane scrollTable = new JScrollPane(table); 
        scrollTable.setPreferredSize(new Dimension(this.getPreferredSize().width - 50, 500));
        scrollTable.setMaximumSize(scrollTable.getPreferredSize());
        scrollTable.getViewport().setBackground(primaryColor);
        scrollTable.setViewportBorder(null);
        scrollTable.setBorder(BorderFactory.createEmptyBorder());
        scrollTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);
        
        this.add(header);
        this.add(Box.createVerticalStrut(5));
        this.add(cardHolder);
        this.add(scrollTable);
    }
}
