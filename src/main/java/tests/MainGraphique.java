package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainGraphique extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource("/views/login.fxml");
        if (fxmlLocation == null) {
            throw new IOException("Le fichier Inscription.fxml est introuvable à l'emplacement /views/Inscription.fxml");
        }

        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(loader.load());

        primaryStage.setTitle("STOCK MANAGEMENT");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Démarrage de l'application JavaFX
    }
}
