package Controllers;

import Service.CategorieService;
import Entite.Categorie;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AjouterCategorieController {

    @FXML
    private TextField categoryNameField;

    @FXML
    private TextField categoryDescriptionField;

    private final CategorieService categorieService = new CategorieService(); // Service utilisé

    @FXML
    private void handleAddCategory() {
        String name = categoryNameField.getText();
        String description = categoryDescriptionField.getText();

        // Validation des champs
        if (name == null || name.trim().isEmpty()) {
            showAlert("Erreur", "Nom requis", "Veuillez fournir un nom pour la catégorie.");
            return;
        }

        if (description == null || description.trim().isEmpty()) {
            description = "Description par défaut";
        }

        Categorie category = new Categorie(name.trim(), description.trim());

        if (categorieService.addCategory(category)) {
            showAlert("Succès", "Catégorie ajoutée", "La catégorie a été ajoutée avec succès !");
            clearFields(); // Nettoyage des champs
        } else {
            showAlert("Erreur", "Ajout échoué", "Impossible d'ajouter la catégorie.");
        }
    }

    private void showAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        categoryNameField.clear();
        categoryDescriptionField.clear();
    }
}
