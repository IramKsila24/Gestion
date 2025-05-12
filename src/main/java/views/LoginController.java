package views;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import models.User;
import services.UserService;
import utils.SceneManager;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Hyperlink registerLink;

    private UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        // Validation des champs
        if (username.isEmpty() || password.isEmpty()) {
            showError("Veuillez remplir tous les champs");
            return;
        }

        // Tentative de connexion
        User user = userService.login(username, password);

        if (user != null) {
            // Connexion réussie
            System.out.println("Connexion réussie pour: " + user.getUsername());

            // Redirection vers la page principale
            SceneManager.changeScene("/views/Dashboard.fxml", "Tableau de bord");
        } else {
            // Échec de connexion
            showError("Identifiant ou mot de passe incorrect");
            passwordField.clear();
        }
    }

    @FXML
    private void handleRegisterLink() {
        SceneManager.changeScene("/views/Inscription.fxml", "Inscription");
    }



    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);

        // Masquer le message après 5 secondes
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        javafx.application.Platform.runLater(() -> {
                            errorLabel.setVisible(false);
                        });
                    }
                },
                5000
        );
    }
}