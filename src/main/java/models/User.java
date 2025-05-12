package models;

public class User {
    private int idUser;
    private String username;
    private String password;
    private String email;
    private String role; // "ADMIN" ou "EMPLOYEE"

    public User() {
    }

    public User(int idUser, String username, String password, String email, String role) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Getters et Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}