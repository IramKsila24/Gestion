package views;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private void goToProductManagement() {
        try {
            // Charge la vue de gestion des produits
            Parent root = FXMLLoader.load(getClass().getResource("/views/ProductManagement.fxml"));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Erreur de navigation", "Impossible de charger la gestion des produits.");
        }
    }

    @FXML
    private void goToStockManagement() {
        try {
            // Charge la vue de gestion du stock
            Parent root = FXMLLoader.load(getClass().getResource("/views/stock-view.fxml"));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            System.out.println("mainPane: " + mainPane);
        } catch (IOException e) {
            showError("Erreur de navigation", "Impossible de charger la gestion du stock.");
        }
    }

    @FXML
    private void logout() {
        try {
            // Redirige vers la page de login
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) mainPane.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            showError("Erreur de déconnexion", "Impossible de se déconnecter.");
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
