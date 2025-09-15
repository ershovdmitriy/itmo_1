package lab7.server.collections;

import lab7.server.logging.ServerLogger;

import java.sql.*;

public class MetaSqlManager {
    private final String url;
    private final String user;
    private final String password;

    public MetaSqlManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getType() {
        String sql = "select type from meta";
        String type = null;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                type = rs.getString("type");
            }
            else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка при считывании мета-информации");
        }
        return type;
    }

    public Date getDate(){
        String sql = "select date from meta";
        Date date = null;
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                date = rs.getDate("date");
            }
            else{
                throw new SQLException();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка при считывании мета-информации");
        }
        return date;
    }
}
