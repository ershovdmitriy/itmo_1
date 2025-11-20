package lab7.server.collections;

import lab7.common.collection.User;
import lab7.server.logging.ServerLogger;

import java.sql.*;
import java.util.ArrayList;

public class UsersSqlManager {

    private final String url;
    private final String user;
    private final String password;

    public UsersSqlManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Long getIdUser(User userData){
        Long id = null;
        String sql = "SELECT id FROM users WHERE login = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password)){
            try (PreparedStatement coordinatesStmt = conn.prepareStatement(sql)) {
                coordinatesStmt.setString(1, userData.getLogin());
                coordinatesStmt.setString(2, userData.getPassword());
                ResultSet rs = coordinatesStmt.executeQuery();
                if (rs.next()) {
                    id = rs.getLong(1);
                }
                rs.close();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка при считывании id пользователя");
        }
        return id;
    }

    public ArrayList<User> getUsers(){
        ArrayList<User> users = new ArrayList<>();
        String sql = "select * from users";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                User user = new User(rs.getString("login"), rs.getString("password"));
                users.add(user);
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка при считывании списка пользователей");
        }
        return users;
    }

    public void addUser(User userData){
        String sql = "INSERT INTO users(login, password) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            try (PreparedStatement coordinatesStmt = conn.prepareStatement(sql)){
                coordinatesStmt.setString(1, userData.getLogin());
                coordinatesStmt.setString(2, userData.getPassword());
                coordinatesStmt.executeUpdate();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка при добавлении пользователя");
        }
    }
}
