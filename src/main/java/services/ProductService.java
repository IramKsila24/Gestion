package services;

import models.Product;
import utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    // Méthode pour ajouter un produit
    public boolean addProduct(Product product) {
        String query = "INSERT INTO product (name, description, price) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout du produit : " + e.getMessage());
            return false;
        }
    }



    // Méthode pour récupérer un produit par son ID
    public Product getProductById(int idProduct) {
        String query = "SELECT * FROM product WHERE id_product = ?";
        Product product = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idProduct);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du produit : " + e.getMessage());
        }
        return product;
    }

    // Méthode pour récupérer tous les produits
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des produits : " + e.getMessage());
        }
        return products;
    }

    // Méthode pour mettre à jour un produit
    public boolean updateProduct(Product product) {
        String query = "UPDATE product SET name = ?, description = ?, price = ? WHERE id_product = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getIdProduct());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du produit : " + e.getMessage());
            return false;
        }
    }

    // Méthode pour supprimer un produit
    public boolean deleteProduct(int productId) {
        String sql = "DELETE FROM product WHERE id_Product = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, productId);
            int rowsAffected = statement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Méthode pour rechercher des produits par nom
    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE name LIKE ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id_product"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des produits : " + e.getMessage());
        }
        return products;
    }


}
