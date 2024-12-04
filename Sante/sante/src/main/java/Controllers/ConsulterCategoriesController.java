package Controllers;
import Service.CategorieService;
import Entite.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ConsulterCategoriesController {

    @FXML
    private TableView<Categorie> categoriesTable;
    @FXML
    private TableColumn<Categorie, String> nameColumn;
    @FXML
    private TableColumn<Categorie, String> descriptionColumn;
    @FXML
    private TableColumn<Categorie, Void> actionsColumn;

    private ObservableList<Categorie> categoriesList = FXCollections.observableArrayList();
    private CategorieService categorieService = new CategorieService();

    @FXML
    private void initialize() {
        loadCategories();
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        // Ajouter des boutons d'action dans chaque ligne
        actionsColumn.setCellFactory(new Callback<TableColumn<Categorie, Void>, TableCell<Categorie, Void>>() {
            @Override
            public TableCell<Categorie, Void> call(TableColumn<Categorie, Void> param) {
                return new TableCell<Categorie, Void>() {
                    private final Button modifyButton = new Button("Modifier");
                    private final Button deleteButton = new Button("Supprimer");

                    {
                        modifyButton.setOnAction(event -> modifyCategory(getTableRow().getItem()));
                        deleteButton.setOnAction(event -> deleteCategory(getTableRow().getItem()));
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox hbox = new HBox(10, modifyButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void loadCategories() {
        categoriesList.setAll(categorieService.getAllCategories());
        categoriesTable.setItems(categoriesList);
    }

    private void modifyCategory(Categorie category) {
        // Implémenter la logique de modification ici
        System.out.println("Modifier la catégorie : " + category.getName());
    }

    private void deleteCategory(Categorie category) {
        boolean success = categorieService.deleteCategory(category);
        if (success) {
            categoriesList.remove(category);
            showAlert("Succès", "Catégorie supprimée avec succès.", AlertType.INFORMATION);
        } else {
            showAlert("Erreur", "Erreur lors de la suppression de la catégorie.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Méthode pour gérer la vue des catégories
    public void handleViewCategories() {
        try {
            // Charger le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ConsulterCategories.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène et la définir sur la fenêtre actuelle
            Stage stage = (Stage) categoriesTable.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Erreur", "Erreur lors du chargement de la vue des catégories.", AlertType.ERROR);
        }
    }
}
