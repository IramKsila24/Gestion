package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.User;
import services.UserService;
import utils.SceneManager;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private UserService userService = new UserService(); // Add this line

    @FXML
    private void handleSignUpAction() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        // Create user object
        User newUser = new User();
        newUser.setUsername(name);
        newUser.setEmail(email);
        newUser.setPassword(password); // Will be hashed in register()
        newUser.setRole("EMPLOYEE"); // Default role

        // Register user
        boolean registrationSuccess = userService.register(newUser);

        if (registrationSuccess) {
            showAlert("Succès", "Inscription réussie.");
            handleCancelAction(); // Clear fields
        } else {
            showAlert("Erreur", "L'inscription a échoué. L'email existe peut-être déjà.");
        }
    }

    @FXML
    private void handleCancelAction() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    private void handleBackToLogin() {
        SceneManager.changeScene("/views/Login.fxml", "Login");
    }
}