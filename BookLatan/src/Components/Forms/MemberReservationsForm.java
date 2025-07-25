/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Utilities.Design;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Model.Book;

import javax.swing.*;
import java.awt.*;

/**
 * Member-specific reservation form that doesn't require member selection
 * @author Generated
 */
public class MemberReservationsForm extends JDialog {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel btnHolder;
    private HeaderPanel header;
    public CustomButton save;
    public CustomButton cancel;
    public JComboBox<Book> bookComboBox;
    public JSpinner reservationDate;
    public JTextArea notes;
    private JLabel bookLabel;
    private JLabel dateLabel;
    private JLabel notesLabel;

    public MemberReservationsForm() {
        initDialog();
        initComponents();
        initLayout();
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width /2, Design.FRAME_SIZE.height - 250));
        this.setIconImage(Design.appIcon);
        this.setTitle("Create Reservation");
        this.setLocationRelativeTo(this.getParent());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setResizable(false);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Design.PRIME_COLOR);
        
        //Header
        header = new HeaderPanel(new Dimension(Design.FRAME_SIZE.width /2, 100));
        header.setTitle("Create Reservation");
        header.setSubtitle("Please select the book you want to reserve");
        
        formPanel = new JPanel();
        formPanel.setBackground(Design.PRIME_COLOR);
        
        bookLabel = new JLabel("Select Book:");
        bookLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        bookComboBox = new JComboBox<>();
        bookComboBox.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 16));
        bookComboBox.setPreferredSize(new Dimension(400, 50));
        bookComboBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        bookComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Book) {
                    Book book = (Book) value;
                    setText(book.getTitle() + " (ID: " + book.getBookID() + ")");
                }
                return this;
            }
        });
        
        dateLabel = new JLabel("Reservation Date (YYYY-MM-DD):");
        dateLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        SpinnerDateModel model = new SpinnerDateModel();
        reservationDate = new JSpinner(model);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(reservationDate, "yyyy-MM-dd");
        editor.getTextField().setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        reservationDate.setEditor(editor);
        reservationDate.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 18));
        reservationDate.setPreferredSize(new Dimension(400, 50));
        reservationDate.setBorder(BorderFactory.createEmptyBorder());
        
        notesLabel = new JLabel("Notes (Optional):");
        notesLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        notes = new JTextArea(4, 30);
        notes.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 16));
        notes.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(10, 5, 10, 5)
        ));
        notes.setLineWrap(true);
        notes.setWrapStyleWord(true);
        
        btnHolder = new JPanel();
        btnHolder.setBackground(Design.PRIME_COLOR);
        
        save = new CustomButton("Save Reservation");
        save.setPrimaryColor(Design.BTN2);
        save.setHoverColor(Design.BTN2_HOVER);
        cancel = new CustomButton("Cancel");
        cancel.setPrimaryColor(Design.BTN3);
        cancel.setHoverColor(Design.BTN3_HOVER);
    }
    
    private void initLayout() {
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        formPanel.setLayout(new GridBagLayout());
        btnHolder.setLayout(new BoxLayout(btnHolder, BoxLayout.X_AXIS));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth = 100;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(bookLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(bookComboBox, gbc);
        gbc.gridy = 2;
        formPanel.add(dateLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(reservationDate, gbc);
        gbc.gridy = 4;
        formPanel.add(notesLabel, gbc);
        gbc.gridy = 5;
        
        JScrollPane notesScrollPane = new JScrollPane(notes);
        notesScrollPane.setPreferredSize(new Dimension(400, 100));
        formPanel.add(notesScrollPane, gbc);
        
        btnHolder.add(Box.createHorizontalGlue());
        btnHolder.add(cancel);
        btnHolder.add(Box.createHorizontalStrut(10));
        btnHolder.add(save);
        btnHolder.add(Box.createHorizontalGlue());
        
        mainPanel.add(header);
        mainPanel.add(formPanel);
        mainPanel.add(btnHolder);
        
        this.add(mainPanel);
    }
    
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setBooks(java.util.List<Book> books) {
        bookComboBox.removeAllItems();
        for (Book book : books) {
            bookComboBox.addItem(book);
        }
    }
}
