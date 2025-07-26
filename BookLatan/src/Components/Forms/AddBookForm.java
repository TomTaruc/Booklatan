package Components.Forms;

import Model.Book;
import Model.BookStatus;
import Model.Publisher;
import Model.Author;
import Components.Designs.CustomButton;
import Components.Designs.HeaderPanel;
import Utilities.Design;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

public class AddBookForm extends JDialog {
    private JTextField titleField;
    private JTextField authorsField;
    private JTextField publisherField;
    private JTextField publisherIdField;
    private JComboBox<String> categoryCombo;
    private JComboBox<String> statusCombo;
    private JTextField shelfLocationField;
    private JTextField languageField;
    private JTextField publicationDateField;

    private Book newBook;
    private boolean confirmed = false;

    public CustomButton saveButton;
    public CustomButton cancelButton;

    public AddBookForm() {
        super((Frame) null, "Add New Book", true); // Make it modal
        initComponents();
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = new Dimension((int)((double) screenSize.width * 0.5), (int) ((double) screenSize.height * 0.7));
        this.setSize(frameSize);

        Font primaryFont = new Font("Tahoma", Font.PLAIN, 16);
        Color primaryColor = new Color(245, 245, 245);

        HeaderPanel header = new HeaderPanel(new Dimension(this.getWidth(), 100));
        header.setTitle("Add New Book");
        header.setSubtitle("Please enter the following information");
        this.add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getSize().height - 100));
        formPanel.setMaximumSize(new Dimension(this.getPreferredSize().width, formPanel.getPreferredSize().height));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setBackground(primaryColor);

        // Field labels and fields
        addField(formPanel, "Title:", titleField = new JTextField(30), primaryFont);
        addField(formPanel, "Authors (comma separated):", authorsField = new JTextField(30), primaryFont);
        addField(formPanel, "Publisher:", publisherField = new JTextField(30), primaryFont);
        addField(formPanel, "Publisher ID:", publisherIdField = new JTextField(30), primaryFont);
        categoryCombo = new JComboBox<>(new String[]{"Fiction", "Non-Fiction", "Poetry", "Drama", "Mythology", "Educational"});
        categoryCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        categoryCombo.setFont(primaryFont);
        addField(formPanel, "Category:", categoryCombo, primaryFont);
        statusCombo = new JComboBox<>(new String[]{"Available", "Reserved", "Not Available", "Loaned"});
        statusCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        statusCombo.setFont(primaryFont);
        addField(formPanel, "Status:", statusCombo, primaryFont);
        addField(formPanel, "Shelf Location:", shelfLocationField = new JTextField(30), primaryFont);
        addField(formPanel, "Language:", languageField = new JTextField(30), primaryFont);
        addField(formPanel, "Publication Date (YYYY-MM-DD):", publicationDateField = new JTextField(30), primaryFont);

        this.add(formPanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(formPanel.getSize().width, 100));
        buttonPanel.setMaximumSize(new Dimension(700, 100));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.setBackground(primaryColor);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        saveButton = new CustomButton("Save");
        saveButton.setPrimaryColor(new Color(168, 213, 186));
        saveButton.setHoverColor(new Color(148, 193, 166));
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("[DEBUG] Save button clicked");
                if (validateFields()) {
                    System.out.println("[DEBUG] Fields validated successfully");
                    try {
                        createBook();
                        System.out.println("[DEBUG] Book created successfully: " + (newBook != null ? newBook.getTitle() : "null"));
                    } catch (Exception ex) {
                        System.out.println("[DEBUG] Error creating book: " + ex.getMessage());
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(AddBookForm.this, "Error creating book: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        return; // Don't confirm if there's an error
                    }
                    // Set confirmed to true only if book creation was successful
                    confirmed = true;
                    JOptionPane.showMessageDialog(AddBookForm.this, "Book added!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {
                    System.out.println("[DEBUG] Field validation failed");
                }
            }
        });

        cancelButton = new CustomButton("Cancel");
        cancelButton.setPrimaryColor(new Color(247, 161, 161));
        cancelButton.setHoverColor(new Color(227, 141, 141));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(AddBookForm.this, "Are you sure you want to cancel?", "Cancel", JOptionPane.YES_NO_OPTION);
                if(answer == JOptionPane.YES_OPTION) {
                    dispose();
                }
            }
        });

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(cancelButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(JPanel panel, String labelText, JComponent field, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font.deriveFont(Font.BOLD));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setFont(font);
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        if (field instanceof JTextField) {
            ((JTextField) field).setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            ((JTextField) field).setHorizontalAlignment(JTextField.LEFT);
        }
        if (field instanceof JComboBox) {
            ((JComboBox<?>) field).setAlignmentX(Component.LEFT_ALIGNMENT);
        }
        panel.add(field);
        panel.add(Box.createVerticalStrut(10));
    }

    private boolean validateFields() {
        if (titleField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (publisherField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Publisher is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (publisherIdField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Publisher ID is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (categoryCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Category is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (shelfLocationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Shelf Location is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void createBook() throws SQLException {
        try {
            System.out.println("[DEBUG] Starting createBook()");
            Model.PublisherDAO publisherDAO = new Model.PublisherDAO();
            int pubId = Integer.parseInt(publisherIdField.getText().trim());
            String pubName = publisherField.getText().trim();
            
            // First try to get existing publisher by ID
            Publisher publisher = publisherDAO.getPublisherById(pubId);
            System.out.println("[DEBUG] Looking for publisher ID: " + pubId + ", found: " + (publisher != null ? publisher.getName() : "null"));
            
            if (publisher == null) {
                // Try to find by name instead
                List<Publisher> allPublishers = publisherDAO.getAllPublishers();
                for (Publisher p : allPublishers) {
                    if (p.getName().equalsIgnoreCase(pubName)) {
                        publisher = p;
                        System.out.println("[DEBUG] Found existing publisher by name: " + publisher.getName() + " (ID: " + publisher.getPubID() + ")");
                        break;
                    }
                }
            }
            
            if (publisher == null) {
                // Create new publisher (let database auto-generate ID)
                System.out.println("[DEBUG] Creating new publisher: " + pubName);
                publisher = new Publisher(0, pubName, "", "", "");
                publisherDAO.addPublisher(publisher);
                // Publisher now has the auto-generated ID
                System.out.println("[DEBUG] New publisher created with ID: " + publisher.getPubID());
            }
            
            System.out.println("[DEBUG] Final publisher used: " + publisher.getName() + " (ID: " + publisher.getPubID() + ")");

            // Create authors list
            List<Author> authors = new ArrayList<>();
            String authorsText = authorsField.getText().trim();
            Model.AuthorDAO authorDAO = new Model.AuthorDAO();
            if (!authorsText.isEmpty()) {
                String[] authorNames = authorsText.split(",");
                for (String name : authorNames) {
                    String trimmed = name.trim();
                    Author author = authorDAO.getAuthorByName(trimmed);
                    if (author == null) {
                        author = new Author(0, trimmed, "");
                        authorDAO.addAuthor(author);
                        author = authorDAO.getAuthorByName(trimmed);
                    }
                    authors.add(author);
                }
            }
            System.out.println("[DEBUG] Authors used: " + authors.size() + " authors");

            Date pubDate = new Date();
            String dateText = publicationDateField.getText().trim();
            if (!dateText.isEmpty()) {
                try {
                    pubDate = java.sql.Date.valueOf(dateText);
                } catch (Exception e) {
                    // Use current date if parsing fails
                }
            }
            System.out.println("[DEBUG] Publication date: " + pubDate);

            String category = categoryCombo.getSelectedItem().toString().trim();
            String status = (String) statusCombo.getSelectedItem();
            System.out.println("[DEBUG] Category: " + category + ", Status: " + status);

            // Convert status with fallback
            BookStatus bookStatus = BookStatus.fromString(status);
            if (bookStatus == null) {
                System.out.println("[DEBUG] Status conversion failed for: '" + status + "', using AVAILABLE as fallback");
                bookStatus = BookStatus.AVAILABLE;
            } else {
                System.out.println("[DEBUG] Status converted successfully: " + status + " -> " + bookStatus.name());
            }

            newBook = new Book(
                0,
                titleField.getText().trim(),
                authors,
                publisher,
                category,
                pubDate,
                languageField.getText().trim(),
                bookStatus,
                shelfLocationField.getText().trim()
            );
            System.out.println("[DEBUG] Book object created: " + newBook.getTitle());
            System.out.println("[DEBUG] newBook field set to: " + (newBook != null ? newBook.getTitle() : "null"));
        } catch (NumberFormatException e) {
            System.out.println("[DEBUG] NumberFormatException: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Invalid Publisher ID format", "Error", JOptionPane.ERROR_MESSAGE);
            throw e;
        } catch (SQLException e) {
            System.out.println("[DEBUG] SQLException in createBook: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            System.out.println("[DEBUG] General exception in createBook: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public Book getNewBook() {
        System.out.println("[DEBUG] getNewBook() called, returning: " + (newBook != null ? newBook.getTitle() : "null"));
        return newBook;
    }

    public boolean isConfirmed() {
        return confirmed;
    }
} 