package models;

import java.time.LocalDateTime;
import models.Product;
public class Stock {
    private int idStock;
    private int id_product;
    private int quantity;
    private String location;
    private LocalDateTime lastUpdated;

    public Stock() {
    }

    public Stock(int idStock, int id_product, int quantity, String location, LocalDateTime lastUpdated) {
        this.idStock = idStock;
        this.id_product = id_product;
        this.quantity = quantity;
        this.location = location;
        this.lastUpdated = lastUpdated;
    }

    // Getters et Setters
    public int getIdStock() {
        return idStock;
    }

    public void setIdStock(int idStock) {
        this.idStock = idStock;
    }

    public int getIdProduct() {
        return id_product;
    }

    public void setIdProduct(int idProduct) {
        this.id_product = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


}
