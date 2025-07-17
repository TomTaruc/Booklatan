/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
/**
 *
 * @author Dinel
 */
public class BookDAO {

    // Add a new book
    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO Book (title, publisher, category, pubDate, language, status, shelfLocation) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, book.getTitle());
            stmt.setInt(2, book.getPublisher().getPubID());
            stmt.setString(3, book.getCategory());
            stmt.setDate(4, new java.sql.Date(book.getPubDate().getTime()));
            stmt.setString(5, book.getLanguage());
            stmt.setString(6, book.getStatus().name());
            stmt.setString(7, book.getShelfLocation());
            stmt.executeUpdate();


            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                book.setBookID(rs.getInt(1));
            }

            for (Author author : book.getAuthors()) {
                String baSql = "INSERT INTO BookAuthor (BookbookID, AuthorauthorID) VALUES (?, ?)";
                try (PreparedStatement baStmt = conn.prepareStatement(baSql)) {
                    baStmt.setInt(1, book.getBookID());
                    baStmt.setInt(2, author.getAuthorID());
                    baStmt.executeUpdate();
                }
            }
        }
    }

    public List<Book> searchBooks(String query) throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Book WHERE title LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int bookID = rs.getInt("bookID");
                String title = rs.getString("title");

                Publisher publisher = getPublisherById(rs.getInt("publisher"));

                List<Author> authors = getAuthorsByBookId(bookID);

                String category = rs.getString("category");
                Date pubDate = rs.getDate("pubDate");
                String language = rs.getString("language");
                BookStatus status = BookStatus.valueOf(rs.getString("status"));
                String shelfLocation = rs.getString("shelfLocation");

                Book book = new Book(bookID, title, authors, publisher, category, pubDate, language, status, shelfLocation);
                books.add(book);
            }
        }
        return books;
    }

    private List<Author> getAuthorsByBookId(int bookID) throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT a.authorID, a.name, a.bio FROM Author a JOIN BookAuthor ba ON a.authorID = ba.AuthorauthorID WHERE ba.BookbookID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(rs.getInt("authorID"), rs.getString("name"), rs.getString("bio")));
            }
        }
        return authors;
    }


    private Publisher getPublisherById(int pubID) throws SQLException {
        String sql = "SELECT * FROM Publisher WHERE pubID = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pubID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Publisher(
                    rs.getInt("pubID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email")
                );
            }
        }
        return null;
    }

}
