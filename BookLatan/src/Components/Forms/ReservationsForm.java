/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Forms;

import Utilities.Design;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Model.Member;
import Model.Book;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Joseph Rey
 */
public class ReservationsForm extends JDialog {
    
    private JPanel mainPanel;
    private JPanel formPanel;
    private JPanel btnHolder;
    private HeaderPanel header;
    public CustomButton save;
    public CustomButton cancel;
    public JComboBox<Member> memberComboBox;
    public JComboBox<Book> bookComboBox;
    public JSpinner reservationDate;
    public JTextArea notes;
    private JLabel memberLabel;
    private JLabel bookLabel;
    private JLabel dateLabel;
    private JLabel notesLabel;

    public ReservationsForm() {
        initDialog();
        initComponents();
        initLayout();
    }
    
    private void initDialog() {
        this.setLayout(new BorderLayout());
        this.setSize(new Dimension(Design.FRAME_SIZE.width /2, Design.FRAME_SIZE.height - 200));
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
        header.setSubtitle("Please select the member and book to reserve");
        
        formPanel = new JPanel();
        formPanel.setBackground(Design.PRIME_COLOR);
        
        memberLabel = new JLabel("Select Member:");
        memberLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        memberComboBox = new JComboBox<>();
        memberComboBox.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 16));
        memberComboBox.setPreferredSize(new Dimension(400, 50));
        memberComboBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        memberComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Member) {
                    Member member = (Member) value;
                    setText(String.format("%06d - %s", member.getMemberID(), member.getName()));
                }
                return this;
            }
        });
        
        bookLabel = new JLabel("Select Book:");
        bookLabel.setFont(Design.PRIME_FONT.deriveFont(Font.BOLD, 18));
        bookComboBox = new JComboBox<>();
        bookComboBox.setFont(Design.PRIME_FONT.deriveFont(Font.PLAIN, 16));
        bookComboBox.setPreferredSize(new Dimension(400, 50));
        bookComboBox.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
        bookComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Book) {
                    Book book = (Book) value;
                    setText(String.format("%d - %s", book.getBookID(), book.getTitle()));
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
        formPanel.add(memberLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(memberComboBox, gbc);
        gbc.gridy = 2;
        formPanel.add(bookLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(bookComboBox, gbc);
        gbc.gridy = 4;
        formPanel.add(dateLabel, gbc);
        gbc.gridy = 5;
        formPanel.add(reservationDate, gbc);
        gbc.gridy = 6;
        formPanel.add(notesLabel, gbc);
        gbc.gridy = 7;
        
        JScrollPane notesScrollPane = new JScrollPane(notes);
        notesScrollPane.setPreferredSize(new Dimension(400, 100));
        formPanel.add(notesScrollPane, gbc);
        
        btnHolder.add(Box.createHorizontalGlue());
        btnHolder.add(cancel);
        btnHolder.add(Box.createHorizontalStrut(10));
        btnHolder.add(save);
        btnHolder.add(Box.createHorizontalGlue());
        
        mainPanel.add(header);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(btnHolder);
        mainPanel.add(Box.createVerticalStrut(20));
        
        this.add(mainPanel);
    }
    
    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    public void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void setMembers(java.util.List<Member> members) {
        DefaultComboBoxModel<Member> model = new DefaultComboBoxModel<>();
        model.addElement(null); // Add empty option
        for (Member member : members) {
            model.addElement(member);
        }
        memberComboBox.setModel(model);
    }
    
    public void setBooks(java.util.List<Book> books) {
        DefaultComboBoxModel<Book> model = new DefaultComboBoxModel<>();
        model.addElement(null); // Add empty option
        for (Book book : books) {
            model.addElement(book);
        }
        bookComboBox.setModel(model);
    }
}
