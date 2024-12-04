package Controllers;

import Service.CategorieService;

import Entite.Categorie;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.Alert;

import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Button;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;

import javafx.scene.layout.TilePane;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdminHomeController {

    @FXML

    private TilePane categoriesTilePane;  // TilePane où les cartes des catégories seront ajoutées

    // Service pour récupérer les catégories depuis la base de données

    private final CategorieService categorieService = new CategorieService();

    @FXML

    public void initialize() {

        // Récupérer toutes les catégories depuis la base de données

        List<Categorie> categories = categorieService.getAllCategories();

        // Ajouter dynamiquement chaque catégorie dans le TilePane

        for (Categorie categorie : categories) {

            VBox categoryCard = new VBox(10);  // VBox pour chaque carte de catégorie

            categoryCard.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 15; -fx-border-color: #bdc3c7; -fx-border-radius: 5; -fx-background-radius: 5;");

            // Afficher le nom de la catégorie

            Label categoryNameLabel = new Label(categorie.getName());

            categoryNameLabel.setStyle("-fx-font-weight: bold;");

            // Afficher la description de la catégorie

            Label categoryDescriptionLabel = new Label(categorie.getDescription());

            // Créer un bouton pour rediriger vers les détails (non implémenté pour l'instant)

            Button detailsButton = new Button("Voir Détails");

            detailsButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

            // On associe une action (pour l'instant cela affiche juste un message)

            detailsButton.setOnAction(e -> showAlert("Action", "Détails de la catégorie : " + categorie.getName()));

            // Ajouter les éléments à la carte (VBox)

            categoryCard.getChildren().addAll(categoryNameLabel, categoryDescriptionLabel, detailsButton);

            // Ajouter la carte dans le TilePane

            categoriesTilePane.getChildren().add(categoryCard);

        }

    }

    // Méthode pour afficher une alerte

    @FXML

    private void showAlert(String title, String message) {

        Alert alert = new Alert(AlertType.INFORMATION);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

    // Méthodes pour les boutons dans la barre latérale (redirection)

    @FXML

    private void handleAddCategory() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCategorie.fxml"));

            AnchorPane pane = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(pane));

            stage.setTitle("Ajouter Catégorie");

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

            showAlert("Erreur", "Impossible de charger la fenêtre d'ajout de catégorie.");

        }

    }

    @FXML

    private void handleViewCategories() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterCategories.fxml"));

            AnchorPane pane = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(pane));

            stage.setTitle("Liste des Catégories");

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

            showAlert("Erreur", "Impossible de charger la fenêtre des catégories.");

        }

    }

    @FXML

    public void handleAddChallenge(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterChallenge.fxml"));
            Parent root = loader.load();
            // Code pour changer de scène ou afficher le nouveau contenu
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement du fichier FXML : " + e.getMessage());
        }
    }


    @FXML

    private void handleViewChallenges() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterChallenges.fxml"));

            AnchorPane pane = loader.load();

            Stage stage = new Stage();

            stage.setScene(new Scene(pane));

            stage.setTitle("Liste des Challenges");

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

            showAlert("Erreur", "Impossible de charger la fenêtre des challenges.");

        }

    }

}
