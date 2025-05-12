package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/gestion_stock_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static Connection connection;

    // Private constructor to prevent instantiation
    private DBConnection() {}

    public static Connection getConnection() {
        try {
            // Check if connection is null, closed, or invalid
            if (connection == null || connection.isClosed() || !connection.isValid(2)) {
                try {
                    // Close the old connection if it exists
                    if (connection != null && !connection.isClosed()) {
                        connection.close();
                    }

                    // Create new connection
                    connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    System.out.println("✅ Connexion à la base de données établie !");
                } catch (SQLException e) {
                    System.err.println("❌ Erreur lors de l'établissement de la connexion : " + e.getMessage());
                    return null;
                }
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("❌ Erreur lors de la vérification de la connexion : " + e.getMessage());
            return null;
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }
}