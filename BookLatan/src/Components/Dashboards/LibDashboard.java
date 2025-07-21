/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Dashboards;
import Utilities.Design;
import View.Components.BorderlessTable;
import View.Components.HeaderPanel;
import View.Components.InfoCard;
import View.Components.ModernScrollPane;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Joseph Rey
 */
public class LibDashboard extends ModernScrollPane {
    
    private JPanel contentPanel;
    public BorderlessTable recentlyLoanedTable;
    public DefaultTableModel recentlyLoanedModel;
    
    public LibDashboard () {
        contentPanel = new JPanel();
        designPanel();
        this.getViewport().setView(contentPanel);
    }
    
    private void designPanel() {
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setPreferredSize(new Dimension(200, Design.MAIN_PANEL_SIZE.height + 300));
        contentPanel.setMaximumSize(new Dimension(Design.MAIN_PANEL_SIZE.width, contentPanel.getPreferredSize().height));
        contentPanel.setBackground(Design.PRIME_COLOR);
        
        HeaderPanel header = new HeaderPanel(new Dimension(Design.MAIN_PANEL_SIZE.width, 150));
        header.setTitle("Dashboard");
        header.setSubtitle("Overiew");
        
        //Info cards -- Holder
        JPanel cardHolder = new JPanel();
        ArrayList<InfoCard> infocards = new ArrayList<>();

        cardHolder.setBackground(Design.PRIME_COLOR);
        cardHolder.setLayout(new BoxLayout(cardHolder, BoxLayout.X_AXIS));
        cardHolder.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 200));
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
        header2.setPreferredSize(new Dimension(Design.MAIN_PANEL_SIZE.width, 50));
        header2.setMaximumSize(header2.getPreferredSize());
        header2.setOpaque(false);

        title.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 25));
        header2.add(title);

        // Recently loaned books
        // TODO: MOVE THIS CODE TO LibAppCon: FOR JOSEPH/DEAN, FROM JOSEPH
            String[] columnNames = {"#", "Name", "Book Title", "Date Loaned", "Date Due"};
            Object[][] data = {};
            
        recentlyLoanedTable = new BorderlessTable();
        recentlyLoanedModel = new DefaultTableModel(data, columnNames);
        recentlyLoanedTable.changeModel(recentlyLoanedModel);
        TableColumn column1 = recentlyLoanedTable.getColumnModel().getColumn(0);
        column1.setMaxWidth(200);
        column1.setMinWidth(100);

        

        //Scrollable Container
        ModernScrollPane scrollTable = new ModernScrollPane(recentlyLoanedTable);
        scrollTable.getViewport().setBackground(Design.PRIME_COLOR);
        scrollTable.setBackground(Design.PRIME_COLOR);
        scrollTable.setViewportBorder(null);
        scrollTable.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10), BorderFactory.createLineBorder(Color.black)));
        scrollTable.setCorner(JScrollPane.UPPER_RIGHT_CORNER, null);
        
        contentPanel.add(header);
        contentPanel.add(cardHolder);
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(header2);
        contentPanel.add(scrollTable);
        
    }
}
