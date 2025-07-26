/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components.Managers;

import Model.Author;
import Model.AuthorDAO;
import Model.Book;
import Model.BookDAO;
import Model.BookStatus;
import Model.Publisher;
import Model.PublisherDAO;
import Components.Forms.AddBookForm;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Dinel
 */
public class BookManagerController {
    private BookManager view;
    private BookDAO bookDAO;
    private PublisherDAO publisherDAO;
    private AuthorDAO authorDAO;
    private Book selectedBook;
    
    public BookManagerController(BookManager view) {
        this.view = view;
        this.bookDAO = new BookDAO();
        this.publisherDAO = new PublisherDAO();
        this.authorDAO = new AuthorDAO();
        
        initializeListeners();
        loadBooks();
    }
    
    private void initializeListeners() {
        // Table selection listener
        this.view.booksTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int rowIndex = view.booksTable.getSelectedRow();
                if (rowIndex >= 0) {
                    loadBookDetails(rowIndex);
                }
            }
        });
        
        // Search functionality
        this.view.searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterBooks();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterBooks();
            }
        });
        
        // Filter listeners
        this.view.statusFilter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    filterBooks();
                }
            }
        });
        
        this.view.categoryFilter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    filterBooks();
                }
            }
        });
        
        // Button listeners
        if (view.addBtn != null) {
            view.addBtn.addActionListener(e -> addBook());
        }
        
        if (view.updateBtn != null) {
            view.updateBtn.addActionListener(e -> updateBook());
        }
        
        if (view.deleteBtn != null) {
            view.deleteBtn.addActionListener(e -> deleteBook());
        }
    }
    
    public void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            System.out.println("[DEBUG] loadBooks() fetched " + books.size() + " books from DB");
            updateTable(books);
        } catch (SQLException ex) {
            Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(view, "Error loading books: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void refreshTable() {
        System.out.println("[DEBUG] refreshTable() called");
        loadBooks();
    }
    
    public void updateTable(List<Book> books) {
        System.out.println("[DEBUG] updateTable() called with " + books.size() + " books");
        SwingUtilities.invokeLater(() -> {
            view.booksTableModel.setRowCount(0);
            for (Book book : books) {
                try {
                    System.out.println("[DEBUG] Processing book ID: " + book.getBookID() + ", Title: " + book.getTitle() + ", Status: " + book.getStatus());
                    // Load authors for this book
                    List<Author> authors = authorDAO.getAllAuthors(book.getBookID());
                    book.setAuthors(authors);
                    System.out.println("[DEBUG] Loaded " + (authors != null ? authors.size() : 0) + " authors for book " + book.getBookID());
                    // Build comma-separated authors string
                    String authorsString = "";
                    if (authors != null && !authors.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < authors.size(); i++) {
                            if (i > 0) sb.append(", ");
                            sb.append(authors.get(i).getName());
                        }
                        authorsString = sb.toString();
                        System.out.println("[DEBUG] Authors string: " + authorsString);
                    } else {
                        System.out.println("[DEBUG] No authors found for book " + book.getBookID());
                    }
                    // Fetch full publisher info
                    Publisher publisher = null;
                    if (book.getPublisher() != null) {
                        try {
                            publisher = publisherDAO.getPublisherById(book.getPublisher().getPubID());
                            System.out.println("[DEBUG] Publisher loaded: " + (publisher != null ? publisher.getName() : "null") + " (ID: " + book.getPublisher().getPubID() + ")");
                        } catch (SQLException e) {
                            publisher = book.getPublisher(); // fallback
                            System.out.println("[DEBUG] Publisher fallback: " + (publisher != null ? publisher.getName() : "null"));
                        }
                    } else {
                        System.out.println("[DEBUG] No publisher object for book " + book.getBookID());
                    }
                    System.out.println("[DEBUG] Adding row for book: " + book.getBookID() + " - " + book.getTitle());
                    view.booksTableModel.addRow(new Object[] {
                        book.getBookID(),
                        book.getTitle(),
                        authorsString,
                        publisher != null ? publisher.getName() : "",
                        book.getCategory(),
                        book.getStatus().toReadableString()
                    });
                } catch (SQLException ex) {
                    Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("[DEBUG] Error processing book ID: " + book.getBookID() + " - " + ex.getMessage());
                    // Add row without authors if there's an error
                    Publisher publisher = null;
                    if (book.getPublisher() != null) {
                        try {
                            publisher = publisherDAO.getPublisherById(book.getPublisher().getPubID());
                        } catch (SQLException e) {
                            publisher = book.getPublisher(); // fallback
                        }
                    }
                    view.booksTableModel.addRow(new Object[] {
                        book.getBookID(),
                        book.getTitle(),
                        "",
                        publisher != null ? publisher.getName() : "",
                        book.getCategory(),
                        book.getStatus().toReadableString()
                    });
                }
            }
            System.out.println("[DEBUG] Table refresh completed. Total rows: " + view.booksTableModel.getRowCount());
        });
    }
    
    private void loadBookDetails(int rowIndex) {
        try {
            int bookID = (Integer) view.booksTable.getValueAt(rowIndex, 0);
            List<Book> books = bookDAO.getAllBooks();
            
            for (Book book : books) {
                if (book.getBookID() == bookID) {
                    // Load complete book information
                    List<Author> authors = authorDAO.getAllAuthors(book.getBookID());
                    book.setAuthors(authors);
                    
                    if (book.getPublisher() != null) {
                        Publisher publisher = publisherDAO.getPublisherById(book.getPublisher().getPubID());
                        if (publisher != null) {
                            book.setPublisher(publisher);
                        }
                    }
                    
                    selectedBook = book;
                    populateFields(book);
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void populateFields(Book book) {
        if (book != null) {
            view.fields.get(0).setText(book.getTitle()); // Title
            view.fields.get(1).setText(getAuthorsString(book.getAuthors())); // Authors
            view.fields.get(2).setText(book.getPublisher() != null ? book.getPublisher().getName() : ""); // Publisher
            view.fields.get(3).setText(book.getCategory()); // Category
            view.fields.get(4).setText(book.getStatus().toReadableString()); // Status
            view.fields.get(5).setText(book.getShelfLocation()); // Shelf Location
            view.fields.get(6).setText(book.getPublisher() != null ? String.valueOf(book.getPublisher().getPubID()) : ""); // Publisher ID
            view.fields.get(7).setText(String.valueOf(book.getBookID())); // Library ID
        }
    }
    
    private String getAuthorsString(List<Model.Author> authors) {
        if (authors == null || authors.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(authors.get(i).getName());
        }
        return sb.toString();
    }
    
    private void filterBooks() {
        String searchText = view.searchBar.getText().trim();
        String statusFilter = (String) view.statusFilter.getSelectedItem();
        String categoryFilter = (String) view.categoryFilter.getSelectedItem();

        // Normalize filters
        if (statusFilter != null && (statusFilter.equals("Status") || statusFilter.equals("All Statuses"))) {
            statusFilter = null;
        }
        if (categoryFilter != null && (categoryFilter.equals("Category") || categoryFilter.equals("All Categories"))) {
            categoryFilter = null;
        }
        if (searchText.isEmpty() || searchText.equals("Search books")) {
            searchText = null;
        }

        try {
            // Use SQL-based filtering for all fields
            List<Book> filteredBooks = bookDAO.getBooksWithFilters(
                searchText, // searchText for title, author, publisher, status, category
                categoryFilter,
                statusFilter,
                null, // publisherName (not needed, searchText covers it)
                null  // authorName (not needed, searchText covers it)
            );
            updateTable(filteredBooks);
        } catch (SQLException ex) {
            Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[DEBUG] SQLException in filterBooks: " + ex.getMessage());
        }
    }
    
    private void addBook() {
        System.out.println("[DEBUG] addBook() called");
        // Open the Add Book form as a modal JDialog
        AddBookForm addBookForm = new AddBookForm();
        addBookForm.setVisible(true);
        
        System.out.println("[DEBUG] AddBookForm closed. isConfirmed: " + addBookForm.isConfirmed());
        
        // Check if a book was created (more reliable than isConfirmed)
        Book newBook = addBookForm.getNewBook();
        System.out.println("[DEBUG] New book object: " + (newBook != null ? newBook.getTitle() : "null"));
        
        if (newBook != null) {
            try {
                System.out.println("[DEBUG] Adding book: " + newBook.getTitle());
                bookDAO.addBook(newBook);
                System.out.println("[DEBUG] Book added with ID: " + newBook.getBookID());
                
                // Create author links in BookAuthor table
                if (newBook.getAuthors() != null && !newBook.getAuthors().isEmpty()) {
                    List<String> authorNames = new java.util.ArrayList<>();
                    for (Author author : newBook.getAuthors()) {
                        authorNames.add(author.getName());
                    }
                    System.out.println("[DEBUG] Creating author links for: " + authorNames);
                    authorDAO.updateBookAuthors(newBook.getBookID(), authorNames);
                    System.out.println("[DEBUG] Author links created successfully");
                } else {
                    System.out.println("[DEBUG] No authors to link for book: " + newBook.getTitle());
                }
                
                JOptionPane.showMessageDialog(view, "Book added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("[DEBUG] About to refresh table...");
                refreshTable(); // Refresh the table
            } catch (SQLException ex) {
                Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("[DEBUG] SQLException in addBook: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error adding book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("[DEBUG] General exception in addBook: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error adding book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("[DEBUG] No book was created");
        }
    }
    
    private void updateBook() {
        System.out.println("[DEBUG] updateBook() called");
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(view, "Please select a book to update", "No Book Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            System.out.println("[DEBUG] Updating book ID: " + selectedBook.getBookID());
            
            // Update the selected book with field values
            String newTitle = view.fields.get(0).getText().trim();
            String newAuthors = view.fields.get(1).getText().trim();
            String newPublisherName = view.fields.get(2).getText().trim();
            String newCategory = view.fields.get(3).getText().trim();
            String newStatus = view.fields.get(4).getText().trim();
            String newShelfLocation = view.fields.get(5).getText().trim();
            String newPublisherIdStr = view.fields.get(6).getText().trim();
            String newLibraryId = view.fields.get(7).getText().trim();
            
            System.out.println("[DEBUG] New values - Title: " + newTitle + ", Authors: " + newAuthors + ", Publisher: " + newPublisherName);
            
            // Validate required fields
            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Title is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update book object
            selectedBook.setTitle(newTitle);
            selectedBook.setCategory(newCategory);
            selectedBook.setShelfLocation(newShelfLocation);
            
            // Handle publisher
            int publisherId = 0;
            Publisher publisher = null;
            if (!newPublisherIdStr.isEmpty()) {
                try {
                    publisherId = Integer.parseInt(newPublisherIdStr);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(view, "Invalid Publisher ID format", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (publisherId > 0) {
                publisher = publisherDAO.getPublisherById(publisherId);
                if (publisher == null) {
                    if (!newPublisherName.isEmpty()) {
                        publisher = new Publisher(publisherId, newPublisherName, "", "", "");
                        publisherDAO.addPublisher(publisher);
                        publisher = publisherDAO.getPublisherById(publisherId);
                        System.out.println("[DEBUG] Inserted new publisher: " + (publisher != null ? publisher.getName() : "null") + " (ID: " + publisherId + ")");
                    }
                }
            }
            // Fallback if publisher is still null
            if (publisher == null) {
                publisher = new Publisher(0, newPublisherName.isEmpty() ? "Unknown Publisher" : newPublisherName, "", "", "");
                System.out.println("[DEBUG] Fallback publisher used: " + publisher.getName());
            }
            selectedBook.setPublisher(publisher);
            
            // Handle status
            try {
                BookStatus status = BookStatus.fromString(newStatus);
                if (status == null) {
                    status = BookStatus.valueOf(newStatus.toUpperCase().replace(' ', '_'));
                }
                selectedBook.setStatus(status);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(view, "Invalid status value: " + newStatus, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Update book in database
            System.out.println("[DEBUG] Updating book in database...");
            bookDAO.updateBook(selectedBook);
            System.out.println("[DEBUG] Book updated in database successfully");
            
            // Update authors in BookAuthor table
            if (!newAuthors.isEmpty()) {
                List<String> authorNames = new java.util.ArrayList<>();
                for (String name : newAuthors.split(",")) {
                    if (!name.trim().isEmpty()) authorNames.add(name.trim());
                }
                System.out.println("[DEBUG] Updating author links: " + authorNames);
                authorDAO.updateBookAuthors(selectedBook.getBookID(), authorNames);
                System.out.println("[DEBUG] Author links updated successfully");
            } else {
                System.out.println("[DEBUG] No authors to update");
            }
            
            // Fetch full publisher info after update
            if (publisherId > 0) {
                Publisher updatedPublisher = publisherDAO.getPublisherById(publisherId);
                if (updatedPublisher != null) {
                    selectedBook.setPublisher(updatedPublisher);
                    System.out.println("[DEBUG] Refreshed publisher after update: " + updatedPublisher.getName() + " (ID: " + updatedPublisher.getPubID() + ")");
                }
            }
            
            JOptionPane.showMessageDialog(view, "Book updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("[DEBUG] About to refresh table...");
            refreshTable(); // Refresh the table
        } catch (SQLException ex) {
            Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("[DEBUG] SQLException in updateBook: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error updating book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            System.out.println("[DEBUG] General exception in updateBook: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error updating book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteBook() {
        System.out.println("[DEBUG] deleteBook() called");
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(view, "Please select a book to delete", "No Book Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(view, 
            "Are you sure you want to delete the book '" + selectedBook.getTitle() + "'?", 
            "Confirm Delete", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                System.out.println("[DEBUG] Deleting book ID: " + selectedBook.getBookID());
                bookDAO.deleteBookManual(selectedBook.getBookID());
                System.out.println("[DEBUG] Book deleted successfully");
                JOptionPane.showMessageDialog(view, "Book deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                selectedBook = null;
                clearFields();
                System.out.println("[DEBUG] About to refresh table...");
                refreshTable(); // Refresh the table
                
            } catch (SQLException ex) {
                Logger.getLogger(BookManagerController.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("[DEBUG] SQLException in deleteBook: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error deleting book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                System.out.println("[DEBUG] General exception in deleteBook: " + ex.getMessage());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(view, "Error deleting book: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("[DEBUG] Delete cancelled by user");
        }
    }
    
    private void clearFields() {
        for (int i = 0; i < view.fields.size(); i++) {
            view.fields.get(i).setText("");
        }
    }
} 