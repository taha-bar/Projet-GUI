package Controllers;

import Service.ChallengeService;
import Entite.Challenge;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class AjouterChallengeController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtCategoryId;

    @FXML
    void ajouterChallenge(ActionEvent event) {
        String name = txtName.getText();
        String description = txtDescription.getText();
        int categoryId = Integer.parseInt(txtCategoryId.getText());

        Challenge challenge = new Challenge(name, description, categoryId);
        ChallengeService service = new ChallengeService();

        try {
            if (service.ajouter(challenge)) {
                System.out.println("Challenge ajouté avec succès !");
            } else {
                System.out.println("Erreur lors de l'ajout du challenge.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
