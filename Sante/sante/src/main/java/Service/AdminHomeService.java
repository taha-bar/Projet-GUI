package Service;

import Entite.Categorie;
import Utils.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminHomeService {

    // Méthode pour récupérer toutes les catégories depuis la base de données
    public List<Categorie> getAllCategories() {
        List<Categorie> categories = new ArrayList<>();
        String query = "SELECT id, name, description FROM categories";

        try (Connection connection = Test.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                categories.add(new Categorie(id, name, description));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }

    // Méthode pour supprimer une catégorie par ID
    public boolean deleteCategory(int categoryId) {
        String query = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = Test.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, categoryId);
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
