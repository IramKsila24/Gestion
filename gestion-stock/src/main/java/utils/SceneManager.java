package utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    public static void changeScene(Stage currentStage, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource(fxmlPath));
            Stage stage = currentStage != null ? currentStage : new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));

            if (currentStage == null) {
                stage.show();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors du chargement de la scène: " + fxmlPath);
            e.printStackTrace();
        }
    }

    public static void changeScene(String fxmlPath, String title) {
        changeScene(null, fxmlPath, title);
    }
}