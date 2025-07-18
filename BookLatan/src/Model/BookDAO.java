/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    // CREATE: Add a new book
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, category, pubDate, lang, _status, shelfLocation, pubID, libID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getCategory());
            stmt.setDate(3, new java.sql.Date(book.getPubDate().getTime()));
            stmt.setString(4, book.getLanguage());
            stmt.setString(5, book.getStatus().name().toLowerCase());
            stmt.setString(6, book.getShelfLocation());
            stmt.setInt(7, book.getPublisher().getPubID());
            stmt.setInt(8, book.getLibID());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                book.setBookID(rs.getInt(1));
            }
        }
    }

    // READ: Get all books
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseUtil.getConnection();
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
        String sql = "UPDATE books SET title=?, category=?, pubDate=?, lang=?, _status=?, shelfLocation=?, pubID=?, libID=? WHERE infobookID=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getCategory());
            stmt.setDate(3, new java.sql.Date(book.getPubDate().getTime()));
            stmt.setString(4, book.getLanguage());
            stmt.setString(5, book.getStatus().name().toLowerCase());
            stmt.setString(6, book.getShelfLocation());
            stmt.setInt(7, book.getPublisher().getPubID());
            stmt.setInt(8, book.getLibID());
            stmt.setInt(9, book.getBookID());
            stmt.executeUpdate();
        }
    }

    // DELETE: Delete a book
    public void deleteBook(int infobookID) throws SQLException {
        String sql = "DELETE FROM books WHERE infobookID=?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, infobookID);
            stmt.executeUpdate();
        }
    }

    // Helper: Map ResultSet to Book object
    private Book mapResultSetToBook(ResultSet rs) throws SQLException {
        int bookID = rs.getInt("infobookID");
        String title = rs.getString("title");
        String category = rs.getString("category");
        java.util.Date pubDate = rs.getDate("pubDate");
        String lang = rs.getString("lang");
        String statusStr = rs.getString("_status");
        String shelfLocation = rs.getString("shelfLocation");
        int pubID = rs.getInt("pubID");
        int libID = rs.getInt("libID");

        Publisher publisher = new Publisher(pubID, "", "", "", "");
        BookStatus status = BookStatus.valueOf(statusStr.toUpperCase().replace(' ', '_'));

        // For simplicity, authors are not loaded here
        return new Book(bookID, title, null, publisher, category, pubDate, lang, status, shelfLocation, libID);
    }
}
