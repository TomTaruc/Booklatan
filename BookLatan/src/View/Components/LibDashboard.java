/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Joseph Rey
 */
public class LibDashboard extends JPanel {    
    private Color primaryColor = new Color(245, 245, 245);
    private Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
    BorderlessTable table;
    DefaultTableModel tableModel;

    public LibDashboard (Dimension size) {
        initComponent(size);
    }

    public void initComponent(Dimension size) {
        this.setPreferredSize(size);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(primaryColor);

        //Header
        HeaderPanel header = new HeaderPanel(new Dimension(size.width, 150));
        header.setTitle("Dashboard");
        header.setSubtitle("Overiew");

        //Info cards -- Holder
        JPanel cardHolder = new JPanel();
        ArrayList<InfoCard> infocards = new ArrayList<>();

        cardHolder.setBackground(primaryColor);
        cardHolder.setLayout(new BoxLayout(cardHolder, BoxLayout.X_AXIS));
        cardHolder.setPreferredSize(new Dimension(size.width, 200));
        cardHolder.setMaximumSize(cardHolder.getPreferredSize());
        cardHolder.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardHolder.setOpaque(false);
        //Info cards -- Content

        infocards.add(new InfoCard("Active Members", "1"));
        infocards.add(new InfoCard("Books Loaned", "100"));
        infocards.add(new InfoCard("Books Due Today", "3"));
        infocards.add(new InfoCard("Total Unpaid Fines", "30"));

        infocards.get(0).setPrimaryColor(new Color(114, 90, 193));
        infocards.get(1).setPrimaryColor(new Color(96, 150, 186));
        infocards.get(2).setPrimaryColor(new Color(229, 56, 59));
        infocards.get(3).setPrimaryColor(new Color(107, 144, 128));

        for(InfoCard card : infocards) {
            cardHolder.add(Box.createVerticalStrut(10));
            cardHolder.add(card);
            cardHolder.add(Box.createVerticalStrut(10));
        }
        
        // Recently loaned books -- Header 2
        JPanel header2 = new JPanel();
        JLabel title = new JLabel("Recently Borrowed Books");
        
        header2.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        header2.setLayout(new BoxLayout(header2, BoxLayout.X_AXIS));
        header2.setPreferredSize(new Dimension(this.getPreferredSize().width, 50));
        header2.setMaximumSize(header2.getPreferredSize());
        header2.setOpaque(false);

        title.setFont(primaryFont.deriveFont(Font.BOLD, 25));
        header2.add(title);

        // Recently loaned books
        // TODO: MOVE THIS CODE TO LibAppCon: FOR JOSEPH/DEAN, FROM JOSEPH
            String[] columnNames = {"#", "Name", "Book Title", "Date Loaned", "Date Due"};
            Object[][] data = {
                {"200001", "Kaiser Lycan", "How to kill a mocking bird", "12/08/2024", "12/09/2024"},
                {"200002", "Dinel Robles", "Legion Lover", "12/08/2024", "12/09/2024", "12/09, 2025"},
                {"200003", "Khryzna Advincula", "When a women kills", "12/08/2024", "12/09/2024"}
            };
            
        table = new BorderlessTable();
        tableModel = new DefaultTableModel(data, columnNames);
        table.changeModel(tableModel);
        TableColumn column1 = table.getColumnModel().getColumn(0);
        column1.setMaxWidth(200);
        column1.setMinWidth(100);

        
        // TODO: MOVE THIS CODE TO LibAppCon: FOR JOSEPH/DEAN, FROM JOSEPH
            DefaultTableModel xtable = (DefaultTableModel) table.getModel();
               while(xtable.getRowCount() < 15) {
                   xtable.addRow(new Object[] {"-", "-", "-", "-", "-"});
               }
       

        //Scrollable Container
        ModernScrollPane scrollTable = new ModernScrollPane(table);
        scrollTable.setPreferredSize(new Dimension(size.width - 50, 580));
        scrollTable.setMaximumSize(scrollTable.getPreferredSize());
        scrollTable.getViewport().setBackground(primaryColor);
        scrollTable.setBackground(primaryColor);
        scrollTable.setViewportBorder(null);
        scrollTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10), BorderFactory.createLineBorder(Color.black)));
        scrollTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);

        this.add(header);
        this.add(cardHolder);
        this.add(Box.createVerticalStrut(30));
        this.add(header2);
        this.add(scrollTable);
    }
}
