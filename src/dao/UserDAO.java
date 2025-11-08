package dao;

import util.DBConnection;
import model.User;
import java.sql.*;

public class UserDAO {
    public boolean registerUser(User user) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Registration error: " + e.getMessage());
            return false;
        }
    }


    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();  // true if a user found
        } catch (SQLException e) {
            System.out.println("❌ Login error: " + e.getMessage());
            return false;
        }
    }
    public int getUserIdByUsername(String username) {
    String query = "SELECT id FROM users WHERE username = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) return rs.getInt("id");
    } catch (SQLException e) {
        System.out.println("Error fetching user ID: " + e.getMessage());
    }
    return -1;
}

}
