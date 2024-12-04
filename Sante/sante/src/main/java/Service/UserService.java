package Service;

import Entite.User;
import Utils.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    public User login(String email, String password) throws SQLException {
        Connection conn = Test.getConnection();
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("role"));
        }
        return null;
    }

    public boolean register(String email, String password) throws SQLException {
        Connection conn = Test.getConnection();
        String checkQuery = "SELECT * FROM users WHERE email = ?";
        PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
        checkStmt.setString(1, email);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            return false; // L'utilisateur existe déjà
        }

        String insertQuery = "INSERT INTO users (email, password, role) VALUES (?, ?, 'client')";
        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
        insertStmt.setString(1, email);
        insertStmt.setString(2, password); // Utilisez un hash pour plus de sécurité
        insertStmt.executeUpdate();
        return true;
    }
}