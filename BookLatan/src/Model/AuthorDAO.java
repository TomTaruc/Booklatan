package Model;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO extends DataAccessObject{
    private static final Dotenv dotenv = Dotenv.load();
    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO Author (name, bio) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
        String sql = "SELECT * FROM completebookinfo where bookID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
        }
        return authors;
    }
    

    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<>();
        String sql = "SELECT * FROM Author";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, author.getName());
            stmt.setString(2, author.getBio());
            stmt.setInt(3, author.getAuthorID());
            stmt.executeUpdate();
        }
    }

    public void deleteAuthor(int authorID) throws SQLException {
        String sql = "DELETE FROM Author WHERE authorID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, authorID);
            stmt.executeUpdate();
        }
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


///


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
} 