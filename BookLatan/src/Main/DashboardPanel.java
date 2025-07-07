/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Views.BorderlessTable;
import Views.CustomScrollPane;
import Views.InfoCard;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.*;


/**
 *
 * @author Joseph Rey
 */
  
    public class DashboardPanel extends JPanel{
        Color primaryColor = new Color(245, 245, 245);
        public DashboardPanel(JFrame frame) {
        this.setPreferredSize(new Dimension(frame.getSize().width - 200, frame.getSize().height));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);

        //Header 
        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.X_AXIS));
        header.setPreferredSize(new Dimension(this.getPreferredSize().width, 100));
        header.setMaximumSize(header.getPreferredSize());
        header.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        header.setAlignmentX(Component.CENTER_ALIGNMENT);
        header.setBackground(primaryColor);

        JPanel textContent = new JPanel();
        textContent.setLayout(new BoxLayout(textContent, BoxLayout.Y_AXIS));
        textContent.setOpaque(false);

        JLabel headerTitle = new JLabel("Librarian's Dashboard");
        headerTitle.setFont(new Font("Tahoma", Font.BOLD, 25));
        JLabel headerSubTitle = new JLabel("Overview");
        headerSubTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textContent.add(headerTitle);
        textContent.add(Box.createVerticalStrut(5));
        textContent.add(headerSubTitle);

        header.add(textContent);
        header.add(Box.createHorizontalGlue());
        //Overview
        JPanel cardHolder = new JPanel();
        cardHolder.setBackground(primaryColor);
        cardHolder.setLayout(new BoxLayout(cardHolder, BoxLayout.X_AXIS));
        cardHolder.setPreferredSize(new Dimension(this.getPreferredSize().width, 200));
        cardHolder.setMaximumSize(cardHolder.getPreferredSize());
        cardHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardHolder.setOpaque(false);
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

        // Main content Hdear
        JPanel tableHeader = new JPanel();
        tableHeader.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        tableHeader.setLayout(new BoxLayout(tableHeader, BoxLayout.X_AXIS));
        tableHeader.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        tableHeader.setMaximumSize(tableHeader.getPreferredSize());
        tableHeader.setOpaque(false);

        JLabel tableTitle = new JLabel("Recently Borrowed Books");
        tableTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
        tableHeader.add(tableTitle);

        //Table for recently laoned books
        String[] columnNames = {"#", "Name", "Book Title", "Date Loaned", "Date Due"};

        Object[][] data = {
            {"200001", "Kaiser Lycan", "How to kill a mocking bird", "12/08/2024", "12/09/2024"},
            {"200002", "Dinel Robles", "Legion Lover", "12/08/2024", "12/09/2024", "12/09, 2025"},
            {"200003", "Khryzna Advincula", "When a women kills", "12/08/2024", "12/09/2024"}
        };

        BorderlessTable table = new BorderlessTable();
        table.setTableColor(primaryColor);
        table.changeModel(new DefaultTableModel(data, columnNames));
        table.getTableHeader().setResizingAllowed(false);
        TableColumn column1 = table.getColumnModel().getColumn(0);
        column1.setMaxWidth(200);
        column1.setMinWidth(100);

        DefaultTableModel xtable = (DefaultTableModel) table.getModel();
        while(xtable.getRowCount() < 15) {
            xtable.addRow(new Object[] {"-", "-", "-", "-", "-"});
        }

        //Scrollable Container
        CustomScrollPane scrollTable = new CustomScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(this.getPreferredSize().width - 50, 600));
        scrollTable.getViewport().setBackground(primaryColor);
        scrollTable.setViewportBorder(null);
        scrollTable.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);

        this.add(header);
        this.add(Box.createVerticalStrut(5));
        this.add(cardHolder);
        this.add(tableHeader);
        this.add(scrollTable);
    }
}