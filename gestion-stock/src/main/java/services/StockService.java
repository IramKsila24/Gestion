package services;

import models.Stock;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
public class StockService {
    private Connection connection;

    public StockService() {
        this.connection = DBConnection.getConnection();
    }

    // Ajouter un stock
    public boolean addStock(Stock stock) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DBConnection.getConnection();  // Vous pouvez aussi utiliser votre méthode d'obtention de la connexion ici

            // Vérifiez si la connexion est ouverte avant de procéder
            if (connection == null || connection.isClosed()) {
                System.err.println("❌ Connexion à la base de données fermée.");
                return false;
            }

            String query = "INSERT INTO stock (id_product, quantity, location, last_updated) VALUES (?, ?, ?, ?)";
            statement = connection.prepareStatement(query);

            statement.setInt(1, stock.getIdProduct());
            statement.setInt(2, stock.getQuantity());
            statement.setString(3, stock.getLocation());
            statement.setTimestamp(4, Timestamp.valueOf(stock.getLastUpdated()));

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de l'ajout du stock : " + e.getMessage());
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null && !connection.isClosed()) {
                    connection.close();  // Fermer la connexion ici, si vous ne l'utilisez pas avec un pool de connexions
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void resetConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            connection = DBConnection.getConnection();
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la réinitialisation de la connexion : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Récupérer tous les stocks
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection(); // Get new connection
            String query = "SELECT * FROM stock";
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Stock stock = new Stock();
                stock.setIdStock(resultSet.getInt("id_stock"));
                stock.setIdProduct(resultSet.getInt("id_product"));
                stock.setQuantity(resultSet.getInt("quantity"));
                stock.setLocation(resultSet.getString("location"));
                stock.setLastUpdated(resultSet.getTimestamp("last_updated").toLocalDateTime());
                stocks.add(stock);
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la récupération des stocks : " + e.getMessage());
        } finally {
            // Close resources in reverse order
            try { if (resultSet != null) resultSet.close(); } catch (SQLException e) { /* ignored */ }
            try { if (statement != null) statement.close(); } catch (SQLException e) { /* ignored */ }
            try { if (connection != null) connection.close(); } catch (SQLException e) { /* ignored */ }
        }
        return stocks;
    }

    // Mettre à jour un stock
    public boolean updateStock(Stock stock) {
        String query = "UPDATE stock SET location = ?, last_updated = NOW() WHERE id_stock = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, stock.getLocation());
            stmt.setInt(2, stock.getIdStock());
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Supprimer un stock
    public boolean deleteStock(int idStock) {
        String query = "DELETE FROM stock WHERE id_stock = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idStock);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la suppression du stock : " + e.getMessage());
            return false;
        }
    }


    public List<Stock> getStocksWithProductDetails() {
        List<Stock> stocks = new ArrayList<>();
        String query = "SELECT s.*, p.name FROM stock s JOIN product p ON s.id_product = p.id_product";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                int idStock = resultSet.getInt("idStock");
                int id_product = resultSet.getInt("id_product");
                int quantity = resultSet.getInt("quantity");
                String location = resultSet.getString("location");
                LocalDateTime lastUpdated = resultSet.getTimestamp("last_updated").toLocalDateTime();
                String productName = resultSet.getString("name");

                
                Stock stock = new Stock(idStock, id_product, quantity, location, lastUpdated);
                stocks.add(stock);
                System.out.println("Produit : " + productName); // Afficher le nom du produit
            }
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la jointure : " + e.getMessage());
        }
        return stocks;
    }
}
