package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Product;
import models.Stock;
import services.StockService;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class StockController {

    @FXML private TextField searchField;
    @FXML private TableView<Stock> stockTable;
    @FXML private TableColumn<Stock, Integer> colIdStock;
    @FXML private TableColumn<Stock, Integer> colProductId;
    @FXML private TableColumn<Stock, Integer> colQuantity;
    @FXML private TableColumn<Stock, String> colLocation;
    @FXML private TableColumn<Stock, String> colUpdated;
    @FXML private TableColumn<Stock, Void> actionColumn;
    @FXML
    private BorderPane mainPane;
    private final StockService stockService = new StockService();
    private ObservableList<Stock> stockList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadStockData();
        addActionButtons();
        configureTableColumns();

    }

    private void loadStockData() {
        ObservableList<Stock> stocks = FXCollections.observableArrayList(stockService.getAllStocks());
        stockTable.setItems(stocks);
    }

    private void configureTableColumns() {
        colIdStock.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdStock()).asObject());
        colProductId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdProduct()).asObject());
        colQuantity.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        colLocation.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLocation()));
        colUpdated.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLastUpdated().toString()));
    }


    @FXML
    private void handleSearch() {

    }

    @FXML
    private void handleResetSearch() {
        searchField.clear();
        loadStockData();
    }

    @FXML
    private void showAddStockDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/showAddStockDialog.fxml"));
            Parent root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter un Stock");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            // Check if mainPane is null and handle that case
            if (mainPane != null && mainPane.getScene() != null) {
                dialogStage.initOwner(mainPane.getScene().getWindow());
            } else {

                Window activeWindow = Stage.getWindows().stream()
                        .filter(Window::isShowing)
                        .findFirst()
                        .orElse(null);

                if (activeWindow != null) {
                    dialogStage.initOwner(activeWindow);
                }

            }

            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load showAddStockDialog.fxml: " + e.getMessage());
        }
    }

    @FXML
    private void refreshStocks() {
        loadStockData();
    }

    @FXML
    private void goToDashboard() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/Dashboard.fxml"));
            // Get any active window if mainPane is null
            Stage stage = (Stage) stockTable.getScene().getWindow(); // Using another injected component
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();

        }
    }
    private void addActionButtons() {
        actionColumn.setCellFactory(param -> new TableCell<Stock, Void>() {
            private final Button btnEdit = new Button("Modifier");
            private final Button btnDelete = new Button("Supprimer");
            private final HBox pane = new HBox(10, btnEdit, btnDelete);

            {
                btnEdit.setStyle("-fx-background-color: #FFC107; -fx-text-fill: black;");
                btnDelete.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");

                // Action du bouton Modifier
                btnEdit.setOnAction(event -> {
                    Stock stock = getTableView().getItems().get(getIndex());
                    handleEditStock(stock);
                });

                // Action du bouton Supprimer
                btnDelete.setOnAction(event -> {
                    Stock stock = getTableView().getItems().get(getIndex());
                    handleDeleteStock(stock);
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

    private void handleDeleteStock(Stock stock) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer ce stock ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (stockService.deleteStock(stock.getIdStock())) {
                    // Recharger la liste des stocks après la suppression
                    stockList.setAll(stockService.getAllStocks());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Stock supprimé !");
                    alert.show();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Échec de la suppression").show();
                }
            }
        });
    }

    private void handleEditStock(Stock stock) {
        TextInputDialog dialog = new TextInputDialog(stock.getLocation());
        dialog.setHeaderText("Modifier le stock");
        dialog.setContentText("Emplacement :");
        dialog.showAndWait().ifPresent(newLocation -> {
            stock.setLocation(newLocation);

            if (stockService.updateStock(stock)) {
                stockTable.refresh();
                new Alert(Alert.AlertType.INFORMATION, "Stock mis à jour !").show();
            } else {
                new Alert(Alert.AlertType.ERROR, "Échec de la mise à jour").show();
            }
        });
    }


}
