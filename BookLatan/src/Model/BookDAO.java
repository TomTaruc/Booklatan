/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
/**
 *
 * @author Dinel
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO extends DataAccessObject {
    
    private PublisherDAO publisherDAO = new PublisherDAO();
    private AuthorDAO authorDAO = new AuthorDAO();

    // CREATE: Add a new book
    public void addBook(Book book) throws SQLException {
        // 1. Ensure publisher exists
        Publisher publisher = book.getPublisher();
        if (publisher != null) {
            Publisher existingPublisher = publisherDAO.getPublisherById(publisher.getPubID());
            if (existingPublisher == null) {
                // If no email, generate a dummy unique email
                String email = publisher.getEmail() == null || publisher.getEmail().isEmpty()
                    ? ("publisher" + publisher.getPubID() + "@example.com")
                    : publisher.getEmail();
                Publisher toInsert = new Publisher(
                    publisher.getPubID(),
                    publisher.getName(),
                    email,
                    publisher.getPhone() == null ? "" : publisher.getPhone(),
                    publisher.getAddress() == null ? "" : publisher.getAddress()
                );
                publisherDAO.addPublisher(toInsert);
                publisher = publisherDAO.getPublisherById(publisher.getPubID());
            }
            book.setPublisher(publisher);
        }

        // 2. Ensure all authors exist
        List<Author> authors = book.getAuthors();
        List<Author> actualAuthors = new java.util.ArrayList<>();
        if (authors != null) {
            for (Author author : authors) {
                Author existingAuthor = authorDAO.getAuthorByName(author.getName());
                if (existingAuthor == null) {
                    authorDAO.addAuthor(author);
                    existingAuthor = authorDAO.getAuthorByName(author.getName());
                }
                actualAuthors.add(existingAuthor);
            }
        }
        book.setAuthors(actualAuthors);

        // 3. Insert the book
        String sql = "INSERT INTO book (title, category, pubDate, lang, _status, shelfLocation, pubID) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getCategory());
            stmt.setDate(3, new java.sql.Date(book.getPubDate().getTime()));
            stmt.setString(4, book.getLanguage());
            stmt.setString(5, book.getStatus().name().toLowerCase());
            stmt.setString(6, book.getShelfLocation());
            stmt.setInt(7, book.getPublisher() != null ? book.getPublisher().getPubID() : 0);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                book.setBookID(rs.getInt(1));
            }
        }

        // 4. Link authors in BookAuthor
        if (book.getBookID() > 0 && actualAuthors != null && !actualAuthors.isEmpty()) {
            List<String> authorNames = new java.util.ArrayList<>();
            for (Author a : actualAuthors) authorNames.add(a.getName());
            authorDAO.updateBookAuthors(book.getBookID(), authorNames);
        }
    }

    // READ: Get all books
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM book";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    // UPDATE: Update a book
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE book SET title=?, category=?, pubDate=?, lang=?, _status=?, shelfLocation=?, pubID=? WHERE bookID=?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getCategory());
            stmt.setDate(3, new java.sql.Date(book.getPubDate().getTime()));
            stmt.setString(4, book.getLanguage());
            stmt.setString(5, book.getStatus().name().toLowerCase());
            stmt.setString(6, book.getShelfLocation());
            stmt.setInt(7, book.getPublisher().getPubID());
            stmt.setInt(8, book.getBookID());
            stmt.executeUpdate();
        }
    }

    // DELETE: Delete a book using stored procedure
    public void deleteBook(int bookID) throws SQLException {
        String sql = "CALL DeleteBook(?)";
        try (Connection conn = getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {
            stmt.setInt(1, bookID);
            stmt.execute();
        }
    }
    
    // Alternative DELETE method that handles foreign key constraints manually
    public void deleteBookManual(int bookID) throws SQLException {
        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try {
                // Try to delete from each table individually, with error handling
                
                // Delete all author relationships for this book (we know this works from AuthorDAO)
                try {
                    String deleteAuthorLinks = "DELETE FROM bookauthor WHERE bookID = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(deleteAuthorLinks)) {
                        stmt.setInt(1, bookID);
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println("[DEBUG] Could not delete from bookauthor: " + e.getMessage());
                    // Continue with other deletions
                }
                
                // Try to delete loan records (if table exists)
                try {
                    String deleteLoans = "DELETE FROM loanbook WHERE bookID = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(deleteLoans)) {
                        stmt.setInt(1, bookID);
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println("[DEBUG] Could not delete from loanbook: " + e.getMessage());
                    // Continue with other deletions
                }
                
                // Try to delete reservation records (if table exists)
                try {
                    String deleteReservations = "DELETE FROM reservation WHERE bookID = ?";
                    try (PreparedStatement stmt = conn.prepareStatement(deleteReservations)) {
                        stmt.setInt(1, bookID);
                        stmt.executeUpdate();
                    }
                } catch (SQLException e) {
                    System.out.println("[DEBUG] Could not delete from reservation: " + e.getMessage());
                    // Continue with other deletions
                }
                
                // Finally delete the book itself
                String deleteBook = "DELETE FROM book WHERE bookID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(deleteBook)) {
                    stmt.setInt(1, bookID);
                    stmt.executeUpdate();
                }
                
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    // SQL-based search: Get books with filters for title, category, status, publisher, author
    public List<Book> getBooksWithFilters(String searchText, String category, String status, String publisherName, String authorName) throws SQLException {
        List<Book> books = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        sql.append("SELECT DISTINCT b.* FROM book b ");

        boolean joinPublisher = (publisherName != null && !publisherName.trim().isEmpty()) || (searchText != null && !searchText.trim().isEmpty());
        boolean joinAuthor = (authorName != null && !authorName.trim().isEmpty()) || (searchText != null && !searchText.trim().isEmpty());

        if (joinPublisher) {
            sql.append("LEFT JOIN Publisher p ON b.pubID = p.pubID ");
        }
        if (joinAuthor) {
            sql.append("LEFT JOIN bookauthor ba ON b.bookID = ba.bookID ");
            sql.append("LEFT JOIN Author a ON ba.authorID = a.authorID ");
        }

        sql.append("WHERE 1=1 ");

        // Dropdown filters (AND)
        if (category != null && !category.trim().isEmpty()) {
            sql.append("AND b.category = ? ");
            parameters.add(category.trim());
        }
        if (status != null && !status.trim().isEmpty()) {
            // Convert readable status to enum name for database search
            BookStatus bookStatus = BookStatus.fromString(status.trim());
            System.out.println("[DEBUG] Status filter: '" + status.trim() + "' -> " + (bookStatus != null ? bookStatus.name() : "null"));
            if (bookStatus != null) {
                sql.append("AND b._status = ? ");
                parameters.add(bookStatus.name());
            } else {
                // Fallback: try direct match
                sql.append("AND b._status LIKE ? ");
                parameters.add("%" + status.trim().toLowerCase() + "%");
            }
        }
        if (publisherName != null && !publisherName.trim().isEmpty()) {
            sql.append("AND p.name LIKE ? ");
            parameters.add("%" + publisherName.trim() + "%");
        }
        if (authorName != null && !authorName.trim().isEmpty()) {
            sql.append("AND a.name LIKE ? ");
            parameters.add("%" + authorName.trim() + "%");
        }

        // Search bar (OR)
        if (searchText != null && !searchText.trim().isEmpty()) {
            sql.append("AND (");
            sql.append("b.title LIKE ? OR ");
            sql.append("b._status LIKE ? OR ");
            sql.append("b.category LIKE ? ");
            if (joinPublisher) {
                sql.append("OR p.name LIKE ? ");
            }
            if (joinAuthor) {
                sql.append("OR a.name LIKE ? ");
            }
            sql.append(") ");
            parameters.add("%" + searchText.trim() + "%"); // title
            parameters.add("%" + searchText.trim() + "%"); // status
            parameters.add("%" + searchText.trim() + "%"); // category
            if (joinPublisher) {
                parameters.add("%" + searchText.trim() + "%"); // publisher
            }
            if (joinAuthor) {
                parameters.add("%" + searchText.trim() + "%"); // author
            }
        }

        sql.append("ORDER BY b.title");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            for (int i = 0; i < parameters.size(); i++) {
                stmt.setObject(i + 1, parameters.get(i));
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = mapResultSetToBook(rs);
                books.add(book);
            }
        }
        return books;
    }

    // Helper: Map ResultSet to Book object using the better approach
    private Book mapResultSetToBook(ResultSet results) throws SQLException {
        Book book = new Book();
        book.setBookID(results.getInt("bookID"));
        book.setTitle(results.getString("Title"));
        book.setAuthors(authorDAO.getAllAuthors(book.getBookID()));
        
        // Load publisher with fallback
        int pubID = results.getInt("pubID");
        Publisher publisher = null;
        try {
            publisher = publisherDAO.getPublisherById(pubID);
        } catch (SQLException e) {
            System.out.println("[DEBUG] Error loading publisher ID " + pubID + ": " + e.getMessage());
        }
        if (publisher == null) {
            // Create a fallback publisher with just the ID
            publisher = new Publisher(pubID, "Unknown Publisher", "", "", "");
            System.out.println("[DEBUG] Using fallback publisher for ID " + pubID);
        }
        book.setPublisher(publisher);
        
        book.setCategory(results.getString("category"));
        book.setPubDate(results.getDate("pubDate"));
        book.setLanguage(results.getString("lang"));
        book.setShelfLocation(results.getString("shelfLocation"));
        book.setStatus(BookStatus.fromString(results.getString("_status")));
        return book;
    }
    
    public Book getBookByTitle(String title) {
        String sql = "SELECT * FROM book WHERE title = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Book book = new Book();
                book.setBookID(rs.getInt("bookID"));
                book.setTitle(rs.getString("title"));

                String statusStr = rs.getString("_status");
                try {
                    BookStatus status = BookStatus.fromString(statusStr);
                    book.setStatus(status);
                } catch (IllegalArgumentException ex) {
                    book.setStatus(BookStatus.NOT_AVAILABLE); 
                }

                return book;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
    public Book getBookByID(int bookID) {
            try {
                Connection conn = super.getConnection();
                PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM book WHERE bookID = ?");
                pstmt.setInt(1, bookID);
                ResultSet result = pstmt.executeQuery();
                if(result.next()) {
                    return this.mapResultSetToBook(result);
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
                return null;
        }

public ArrayList<Book> getAllBooksByTitleStatus(String title, BookStatus status) {
        Connection conn = super.getConnection();
        ArrayList<Book> books = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement("Select * from book where title like ? AND _status = ?");
            pstmt.setString(1, "%" + title + "%");
            pstmt.setString(2, status.toString().toLowerCase());
            ResultSet results = pstmt.executeQuery();
            while(results.next()) {
                Book book = mapResultSetToBook(results);
                books.add(book);
            }

            results.close();
            pstmt.close();
            conn.close();
            return books;
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    };    
}
