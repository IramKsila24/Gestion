package services;

import models.User;
import utils.DBConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {

    private Connection conn;

    public UserService() {
        conn = DBConnection.getConnection();  // Connexion à la base de données
    }

    // Inscription d'un nouvel utilisateur
    public boolean register(User user) {
        // Hashage du mot de passe
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        String query = "INSERT INTO user (username, password, email, role) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword);  // On insère le mot de passe hashé
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getRole());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Si l'insertion est réussie, retourner true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // En cas d'erreur, retour false
        }
    }

    // Connexion d'un utilisateur
    public User login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Récupérer l'utilisateur de la base de données
                String storedHashedPassword = resultSet.getString("password");

                // Comparer le mot de passe saisi avec le mot de passe hashé dans la base de données
                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    // Si les mots de passe correspondent, retourner l'utilisateur
                    User user = new User();
                    user.setIdUser(resultSet.getInt("id_user"));
                    user.setUsername(resultSet.getString("username"));
                    user.setPassword(storedHashedPassword);
                    user.setEmail(resultSet.getString("email"));
                    user.setRole(resultSet.getString("role"));
                    return user;
                } else {
                    System.out.println("Mot de passe incorrect !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Si aucun utilisateur trouvé ou mot de passe incorrect
    }

    // Déconnexion de l'utilisateur (côté session)
    public void logout() {
        // Pour la déconnexion, on peut simplement gérer l'état de l'utilisateur dans l'application
        // Ici, on n'a pas besoin d'une requête SQL particulière.
        System.out.println("L'utilisateur est maintenant déconnecté.");
    }
}
