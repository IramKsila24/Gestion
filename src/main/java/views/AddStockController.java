package views;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Product;
import models.Stock;
import services.ProductService;
import services.StockService;

import java.time.LocalDateTime;
import java.util.List;

public class AddStockController {

    @FXML private ComboBox<Product> productComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField locationField;

    private final StockService stockService = new StockService();
    private final ProductService productService = new ProductService();

    @FXML
    public void initialize() {
        // Load products into ComboBox
        List<Product> products = productService.getAllProducts();
        productComboBox.getItems().addAll(products);

        // Configure product display in ComboBox
        productComboBox.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Product item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item.getName() + " (ID: " + item.getIdProduct() + ")");
            }
        });

        productComboBox.setConverter(new StringConverter<Product>() {
            @Override
            public String toString(Product product) {
                return product == null ? "" : product.getName() + " (ID: " + product.getIdProduct() + ")";
            }

            @Override
            public Product fromString(String string) {
                return null; // Not needed for display
            }
        });
    }

    @FXML
    private void handleAddStock() {
        try {
            Product selectedProduct = productComboBox.getSelectionModel().getSelectedItem();

            if (selectedProduct == null) {
                System.err.println("❌ Veuillez sélectionner un produit.");
                return;
            }

            int quantity = Integer.parseInt(quantityField.getText());
            String location = locationField.getText();

            if (location.isEmpty()) {
                System.err.println("❌ Emplacement vide.");
                return;
            }

            Stock stock = new Stock();
            stock.setIdProduct(selectedProduct.getIdProduct());  // Use selected product's ID
            stock.setQuantity(quantity);
            stock.setLocation(location);
            stock.setLastUpdated(LocalDateTime.now());

            boolean success = stockService.addStock(stock);

            if (success) {
                System.out.println("✅ Stock ajouté avec succès !");
                closeWindow();
            } else {
                System.err.println("❌ Échec de l'ajout du stock.");
            }

        } catch (NumberFormatException e) {
            System.err.println("❌ Veuillez entrer une quantité valide.");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) productComboBox.getScene().getWindow();
        stage.close();
    }
}