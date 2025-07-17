package Model;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PublisherDAO {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String DB_URL = dotenv.get("DB_URL");
    private static final String DB_USER = dotenv.get("DB_USER");
    private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");

    public void addPublisher(Publisher publisher) throws SQLException {
        String sql = "INSERT INTO Publisher (name, address, phone, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getAddress());
            stmt.setString(3, publisher.getPhone());
            stmt.setString(4, publisher.getEmail());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                publisher.setPubID(rs.getInt(1));
            }
        }
    }

    public Publisher getPublisherById(int pubID) throws SQLException {
        String sql = "SELECT * FROM Publisher WHERE pubID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
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

    public List<Publisher> getAllPublishers() throws SQLException {
        List<Publisher> publishers = new ArrayList<>();
        String sql = "SELECT * FROM Publisher";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                publishers.add(new Publisher(
                    rs.getInt("pubID"),
                    rs.getString("name"),
                    rs.getString("address"),
                    rs.getString("phone"),
                    rs.getString("email")
                ));
            }
        }
        return publishers;
    }

    public void updatePublisher(Publisher publisher) throws SQLException {
        String sql = "UPDATE Publisher SET name = ?, address = ?, phone = ?, email = ? WHERE pubID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, publisher.getName());
            stmt.setString(2, publisher.getAddress());
            stmt.setString(3, publisher.getPhone());
            stmt.setString(4, publisher.getEmail());
            stmt.setInt(5, publisher.getPubID());
            stmt.executeUpdate();
        }
    }

    public void deletePublisher(int pubID) throws SQLException {
        String sql = "DELETE FROM Publisher WHERE pubID = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pubID);
            stmt.executeUpdate();
        }
    }
} 