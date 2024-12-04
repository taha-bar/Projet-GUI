package Controllers;

import Entite.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Service.UserService;

public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService();

    @FXML
    public void handleLogin(ActionEvent event) {
        try {
            String email = emailField.getText();
            String password = passwordField.getText();

            // Vérification des identifiants via UserService
            User user = userService.login(email, password);
            if (user != null) {
                FXMLLoader loader;
                if ("admin".equals(user.getRole())) {
                    // Charger l'interface Admin
                    loader = new FXMLLoader(getClass().getResource("/AdminHome.fxml"));
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Admin Home");
                    stage.show();
                    System.out.println("Admin connecté !");
                } else {
                    // Charger l'interface Client
                    loader = new FXMLLoader(getClass().getResource("/ClientHome.fxml"));
                    Stage stage = (Stage) emailField.getScene().getWindow();
                    stage.setScene(new Scene(loader.load()));
                    stage.setTitle("Client Home");
                    stage.show();
                    System.out.println("Client connecté !");
                }
            } else {
                // Afficher une alerte en cas de connexion échouée
                showAlert("Erreur", "Email ou mot de passe incorrect !");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème lors de la connexion !");
        }
    }

    @FXML
    public void openRegistrationForm(ActionEvent event) {
        try {
            // Charger l'interface de registre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/register.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Inscription");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Problème lors de l'ouverture du formulaire d'inscription !");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
