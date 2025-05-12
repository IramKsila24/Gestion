package models;

import java.time.LocalDateTime;

public class Product {
    private int idProduct;
    private String name;
    private String description;
    private double price;
    private LocalDateTime createdAt;

    public Product() {
    }

    public Product(int idProduct, String name, String description, double price, LocalDateTime createdAt) {
        this.idProduct = idProduct;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    // Getters et Setters
    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Product(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "idProduct=" + idProduct +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", createdAt=" + createdAt +
                '}';
    }
}