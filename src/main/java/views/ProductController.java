package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Product;
import services.ProductService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> colId;


    @FXML
    private TableColumn<Product, String> colName;

    @FXML
    private TableColumn<Product, String> colDescription;

    @FXML
    private TableColumn<Product, Double> colPrice;

    @FXML
    private TableColumn<Product, String> colCreatedAt;

    @FXML
    private TableColumn<Product, Void> actionColumn;

    @FXML
    private TextField searchField;

    private final ProductService productService = new ProductService();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configureTableColumns();
        loadProducts();
        addActionButtons();
    }

    private void addActionButtons() {
        actionColumn.setCellFactory(param -> new TableCell<>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;");
                btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                btnEdit.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    handleEditProduct(product);
                });

                btnDelete.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    handleDeleteProduct(product);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }
    private void handleDeleteProduct(Product product) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer ce produit ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (productService.deleteProduct(product.getIdProduct())) {
                    productList.remove(product);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Produit supprimé !");
                    alert.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Échec de la suppression").show();
                }
            }
        });
    }
    private void handleEditProduct(Product product) {

        TextInputDialog dialog = new TextInputDialog(product.getName());
        dialog.setHeaderText("Modifier le nom du produit");
        dialog.setContentText("Nom :");
        dialog.showAndWait().ifPresent(newName -> {
            product.setName(newName);
            if (productService.updateProduct(product)) {
                productTable.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Produit mis à jour !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Échec de la mise à jour").show();
            }
        });
    }

    private void configureTableColumns() {
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdProduct()).asObject());
        colName.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        colDescription.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        colPrice.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        colCreatedAt.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCreatedAt().toString()));
    }

    private void loadProducts() {
        List<Product> products = productService.getAllProducts();
        productList = FXCollections.observableArrayList(products);
        productTable.setItems(productList);
    }

    @FXML
    public void refreshProducts(ActionEvent event) {
        searchField.clear();
        loadProducts();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().trim();
        if (keyword.isEmpty()) {
            loadProducts();
        } else {
            List<Product> filtered = productService.searchProductsByName(keyword);
            productTable.setItems(FXCollections.observableArrayList(filtered));
        }
    }

    @FXML
    private void handleResetSearch(ActionEvent event) {
        searchField.clear();
        loadProducts();
    }

    @FXML
    private void showAddProductDialog(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AddProductDialog.fxml"));
            Parent root = loader.load();


            AddProductController controller = loader.getController();
            controller.setParentController(this);


            Stage dialog = new Stage();
            dialog.setTitle("Ajouter un Produit");
            dialog.setScene(new Scene(root));
            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Dashboard.fxml"));
            Parent dashboardView = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(dashboardView);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
