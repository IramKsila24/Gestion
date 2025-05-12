package tests;
import services.StockService;
import models.Stock;

import java.time.LocalDateTime;

public class MainConsole {
    public static void main(String[] args) {
        // Création de l'objet StockService
        StockService stockService = new StockService();

        // Test d'ajout de stock
        Stock stockToAdd1 = new Stock(6, 20, 160, "Entrepôt C", LocalDateTime.now());
        Stock stockToAdd2 = new Stock(7, 21, 200, "Entrepôt A", LocalDateTime.now());
        Stock stockToAdd3 = new Stock(8, 21, 250, "Entrepôt B", LocalDateTime.now());

        // Ajouter les stocks via StockService
        if (stockService.addStock(stockToAdd1)) {
            System.out.println("✅ Stock ajouté avec succès !");
        } else {
            System.out.println("❌ Échec de l'ajout du stock.");
        }

        if (stockService.addStock(stockToAdd2)) {
            System.out.println("✅ Stock ajouté avec succès !");
        } else {
            System.out.println("❌ Échec de l'ajout du stock.");
        }

        if (stockService.addStock(stockToAdd3)) {
            System.out.println("✅ Stock ajouté avec succès !");
        } else {
            System.out.println("❌ Échec de l'ajout du stock.");
        }
    }
}
