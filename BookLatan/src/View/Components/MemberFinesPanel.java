package View.Components;

import Model.Fine;
import Model.FineDAO;
import Model.User;
import java.awt.*;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class MemberFinesPanel extends JPanel {

    private User currentUser;
    private FineDAO fineDAO;

    private JTable finesTable;
    private DefaultTableModel tableModel;
    private JLabel noFinesLabel;
    private JLabel memberNameLabel;
    private JLabel totalOutstandingFinesLabel;
    private JLabel paymentReminderLabel;

    private JPanel contentPanel;
    private JPanel bottomPanel;
    private CardLayout cardLayout;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public MemberFinesPanel(User user) {
        this.currentUser = user;
        this.fineDAO = new FineDAO();
        initComponents();
        loadFinesData();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        memberNameLabel = new JLabel("Member: " + currentUser.getUsername() + " (ID: " + currentUser.getUserId() + ")");
        memberNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        memberNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        totalOutstandingFinesLabel = new JLabel("Total Outstanding Fines: ₱0.00");
        totalOutstandingFinesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        totalOutstandingFinesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(memberNameLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(totalOutstandingFinesLabel);

        add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {"Fine ID", "Book Title", "Due Date", "Return Date", "Days Overdue", "Amount", "Status", "Reason"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                    case 4:
                        return Integer.class;
                    case 5:
                        return Double.class;
                    default:
                        return String.class;
                }
            }
        };

        finesTable = new JTable(tableModel);
        finesTable.setFillsViewportHeight(true);
        finesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        finesTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        finesTable.setRowHeight(25);

        finesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        finesTable.getTableHeader().setBackground(new Color(70, 130, 180));
        finesTable.getTableHeader().setForeground(Color.WHITE);

        finesTable.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < finesTable.getColumnCount(); i++) {
            finesTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(finesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        noFinesLabel = new JLabel("You have no existing fines.", SwingConstants.CENTER);
        noFinesLabel.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        noFinesLabel.setForeground(Color.GRAY);

        paymentReminderLabel = new JLabel("<html><div style='text-align: center;'>Please settle your outstanding payments at the library. Thank you!</div></html>", SwingConstants.CENTER);
        paymentReminderLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        paymentReminderLabel.setForeground(new Color(200, 0, 0));
        paymentReminderLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        paymentReminderLabel.setVisible(false);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(scrollPane, "FINES_TABLE");
        contentPanel.add(noFinesLabel, "NO_FINES");

        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(contentPanel, BorderLayout.CENTER);
        bottomPanel.add(paymentReminderLabel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.CENTER);
    }

    public void loadFinesData() {
        tableModel.setRowCount(0);
        double totalOutstanding = 0.0;

        List<Fine> fines = fineDAO.getFinesByMemberID(String.valueOf(currentUser.getUserId()));

        if (fines != null && !fines.isEmpty()) {
            for (Fine fine : fines) {
                if (!"Paid".equalsIgnoreCase(fine.get_status())) {
                    totalOutstanding += fine.getAmount();
                }

                tableModel.addRow(new Object[]{
                    fine.getFineID(),
                    fine.getBook_title() != null ? fine.getBook_title() : "N/A",
                    fine.getDue_date() != null ? DATE_FORMAT.format(fine.getDue_date()) : "N/A",
                    fine.getReturn_date() != null ? DATE_FORMAT.format(fine.getReturn_date()) : "N/A",
                    fine.getDays_overdue(),
                    String.format("%.2f", fine.getAmount()),
                    fine.get_status(),
                    fine.getReason() != null ? fine.getReason() : "N/A"
                });
            }
            cardLayout.show(contentPanel, "FINES_TABLE");
        } else {
            cardLayout.show(contentPanel, "NO_FINES");
        }

        totalOutstandingFinesLabel.setText("Total Outstanding Fines: ₱" + String.format("%.2f", totalOutstanding));

        System.out.println("DEBUG: Total Outstanding Fines for " + currentUser.getUsername() + ": ₱" + String.format("%.2f", totalOutstanding));

        paymentReminderLabel.setVisible(totalOutstanding > 0);
        
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }

    public void refreshFines() {
        loadFinesData();
    }
}