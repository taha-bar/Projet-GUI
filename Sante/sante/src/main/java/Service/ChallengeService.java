package Service;

import Entite.Challenge;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChallengeService implements IService<Challenge> {

    private Connection connection;

    public ChallengeService() {
        // Assurez-vous que la connexion est correctement initialisée
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/nom_de_votre_base", "utilisateur", "mot_de_passe");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean ajouter(Challenge challenge) throws SQLException {
        String query = "INSERT INTO Challenge (name, description, category_id) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, challenge.getName());
            stmt.setString(2, challenge.getDescription());
            stmt.setInt(3, challenge.getCategoryId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean supprimer(Challenge challenge) throws SQLException {
        String query = "DELETE FROM Challenge WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, challenge.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public boolean update(Challenge challenge) throws SQLException {
        String query = "UPDATE Challenge SET name = ?, description = ?, category_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, challenge.getName());
            stmt.setString(2, challenge.getDescription());
            stmt.setInt(3, challenge.getCategoryId());
            stmt.setInt(4, challenge.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public Challenge findById(int id) throws SQLException {
        String query = "SELECT * FROM Challenge WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Challenge(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id")
                );
            }
        }
        return null;
    }

    @Override
    public List<Challenge> readAll() throws SQLException {
        List<Challenge> challenges = new ArrayList<>();
        String query = "SELECT * FROM Challenge";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                challenges.add(new Challenge(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("category_id")
                ));
            }
        }
        return challenges;
    }
}
