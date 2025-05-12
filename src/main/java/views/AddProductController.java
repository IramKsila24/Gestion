package views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Product;
import services.ProductService;

public class AddProductController {

    @FXML
    private TextField productNameField;

    @FXML
    private TextArea productDescriptionField;

    @FXML
    private TextField productPriceField;

    private ProductController parentController;

    public void setParentController(ProductController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleAddProduct(ActionEvent event) {
        String name = productNameField.getText().trim();
        String description = productDescriptionField.getText().trim();
        String priceText = productPriceField.getText().trim();

        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty()) {
            // Afficher un message d'erreur si des champs sont vides
            Alert alert = new Alert(Alert.AlertType.WARNING, "Tous les champs doivent être remplis !");
            alert.showAndWait();
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            Product newProduct = new Product(name, description, price);
            ProductService productService = new ProductService();

            if (productService.addProduct(newProduct)) {
                // Si le produit a été ajouté avec succès, fermer la fenêtre
                parentController.refreshProducts(null);  // Rafraîchir la liste des produits
                closeDialog();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de l'ajout du produit.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Afficher un message d'erreur si le prix n'est pas un nombre valide
            Alert alert = new Alert(Alert.AlertType.ERROR, "Veuillez entrer un prix valide !");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleCancel(ActionEvent event) {
        closeDialog();
    }

    private void closeDialog() {

        Stage stage = (Stage) productNameField.getScene().getWindow();
        stage.close();
    }
}
