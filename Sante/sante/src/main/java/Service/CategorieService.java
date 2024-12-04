package Service;

import Entite.Categorie;
import Utils.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategorieService {

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

    // Méthode pour ajouter une nouvelle catégorie
    public boolean addCategory(Categorie category) { // Nom unifié
        String query = "INSERT INTO categories (name, description) VALUES (?, ?)";
        try (Connection connection = Test.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;  // Retourne true si l'insertion réussit

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour vérifier si une catégorie existe déjà
    public boolean doesCategoryExist(String categoryName) {
        String query = "SELECT COUNT(*) FROM categories WHERE name = ?";
        try (Connection connection = Test.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, categoryName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCategory(Categorie category) {
        return false; // Non implémentée pour l'instant
    }
}
