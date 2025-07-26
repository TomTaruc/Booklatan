package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DataAccessObject {

    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO Author (name, bio) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getBio());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                author.setAuthorID(rs.getInt(1));
            }
        }
    }

    public Author getAuthorById(int authorID) throws SQLException {
        String sql = "SELECT * FROM Author WHERE authorID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, authorID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Author(
                    rs.getInt("authorID"),
                    rs.getString("name"),
                    rs.getString("bio")
                );
            }
        }
        return null;
    }
    
    public List<Author> getAllAuthors(int bookID) throws SQLException {
        List<Author> authors = new ArrayList<>();
        
        // Try the view first
        String sql = "SELECT * FROM completebookinfo where bookID = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                    rs.getInt("authorID"),
                    rs.getString("authorName"),
                    rs.getString("bio")
                ));
            }
        } catch (SQLException e) {
            System.out.println("[DEBUG] View query failed, trying direct JOIN: " + e.getMessage());
        }
        
        // If view failed or returned no results, try direct JOIN
        if (authors.isEmpty()) {
            sql = "SELECT a.authorID, a.name, a.bio FROM Author a " +
                  "JOIN BookAuthor ba ON a.authorID = ba.authorID " +
                  "WHERE ba.bookID = ?";
            try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, bookID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    authors.add(new Author(
                        rs.getInt("authorID"),
                        rs.getString("name"),
                        rs.getString("bio")
                    ));
                }
            }
        }
        
        System.out.println("[DEBUG] getAllAuthors for book " + bookID + " returned " + authors.size() + " authors");
        return authors;
    }
    

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                authors.add(new Author(
                    rs.getInt("authorID"),
                    rs.getString("name"),
                    rs.getString("bio")
                ));
            }
        }
        return authors;
    }

    public void updateAuthor(Author author) throws SQLException {
        String sql = "UPDATE Author SET name = ?, bio = ? WHERE authorID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getBio());
            stmt.setInt(3, author.getAuthorID());
            stmt.executeUpdate();
        }
    }

    public void deleteAuthor(int authorID) throws SQLException {
        String sql = "DELETE FROM Author WHERE authorID = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, authorID);
            stmt.executeUpdate();
        }
    }

    public void updateBookAuthors(int bookID, List<String> authorNames) throws SQLException {
        // Remove all existing links for this book
        String deleteSql = "DELETE FROM BookAuthor WHERE bookID = ?";
        try (Connection conn = getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
            deleteStmt.setInt(1, bookID);
            deleteStmt.executeUpdate();
        }
        // For each author, add if not exists, then link
        for (String name : authorNames) {
            int authorID = getOrCreateAuthorIdByName(name.trim());
            String insertSql = "INSERT INTO BookAuthor (bookID, authorID) VALUES (?, ?)";
            try (Connection conn = getConnection();
                 PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, bookID);
                insertStmt.setInt(2, authorID);
                insertStmt.executeUpdate();
            }
        }
    }
    // Helper: get or create author by name
    private int getOrCreateAuthorIdByName(String name) throws SQLException {
        String selectSql = "SELECT authorID FROM Author WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
            selectStmt.setString(1, name);
            ResultSet rs = selectStmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("authorID");
            }
        }
        // If not found, insert
        String insertSql = "INSERT INTO Author (name, bio) VALUES (?, '')";
        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setString(1, name);
            insertStmt.executeUpdate();
            ResultSet rs = insertStmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        throw new SQLException("Failed to create author: " + name);
    }

    public Author getAuthorByName(String name) throws java.sql.SQLException {
        String sql = "SELECT * FROM Author WHERE name = ?";
        try (java.sql.Connection conn = getConnection();
             java.sql.PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            try (java.sql.ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int authorId = rs.getInt("authorID");
                    String authorName = rs.getString("name");
                    String bio = rs.getString("bio");
                    return new Author(authorId, authorName, bio);
                }
            }
        }
        return null;
    }
} 