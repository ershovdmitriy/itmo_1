package lab7.server.collections;

import lab7.common.collection.HumanBeing.Coordinates;
import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.collection.HumanBeing.WeaponType;
import lab7.common.collection.User;
import lab7.server.logging.ServerLogger;

import java.sql.*;
import java.util.LinkedHashMap;

public class HumanBeingSqlManager implements SqlManager<LinkedHashMap<String, HumanBeing>, HumanBeing> {

    private final String url;
    private final String user;
    private final String password;

    public HumanBeingSqlManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public LinkedHashMap<String, HumanBeing> loadData(){
        LinkedHashMap<String, HumanBeing> collection = new LinkedHashMap<>();
        String sql = "SELECT h.key, h.name, h.id, c.x, c.y, h.creation_date, h.real_hero, h.has_toothpick,"
        + "h.impact_speed, h.soundtrack_name, h.minutes_of_waiting, h.weapon_type, cars.cool,"
        + "u.login, u.password, u.id FROM human_beings h JOIN coordinates c ON h.coordinates_id = c.id"
                +" JOIN cars ON h.car_id = cars.id JOIN users u ON h.user_id = u.id";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                HumanBeing h = new HumanBeing();
                String key = rs.getString("key");
                h.setId(rs.getInt("id"));
                h.setName(rs.getString("name"));
                Coordinates c = new Coordinates();
                c.setX(rs.getDouble("x"));
                c.setY(rs.getInt("y"));
                h.setCoordinates(c);
                h.setCreationDate(rs.getDate("creation_date"));
                h.setRealHero(rs.getBoolean("real_hero"));
                h.setHasToothpick(rs.getBoolean("has_toothpick"));
                h.setImpactSpeed(rs.getLong("impact_speed"));
                h.setSoundtrackName(rs.getString("soundtrack_name"));
                h.setMinutesOfWaiting(rs.getInt("minutes_of_waiting"));
                h.setWeaponType(WeaponType.valueOf(rs.getString("weapon_type")));
                User u = new User(rs.getString("login"), rs.getString("password"));
                h.setUser(u);
                collection.put(key, h);
            }
        }catch (SQLException e){
            ServerLogger.error("Ошибка загрузки коллекции");
        }
        return collection;
    }

    @Override
    public void addObject(String key, HumanBeing object, Long userId){
        String humanBeingSql = "INSERT INTO human_beings (key, name, coordinates_id,"
                + "real_hero, has_toothpick, impact_speed, soundtrack_name, minutes_of_waiting,"
                + "weapon_type, car_id, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::weapon_type, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            Long coordinatesId = null;
            String coordinatesSql = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
            try (PreparedStatement coordinatesStmt = conn.prepareStatement(coordinatesSql)){
                coordinatesStmt.setDouble(1, object.getCoordinates().getX());
                coordinatesStmt.setFloat(2, object.getCoordinates().getY());
                ResultSet rs = coordinatesStmt.executeQuery();
                if(rs.next()){
                    coordinatesId = rs.getLong("id");
                }
                else{
                    throw new SQLException();
                }
                rs.close();
            }

            Long carsId = null;
            String carsSql = "SELECT id FROM cars WHERE cars.cool = ?";
            try (PreparedStatement carsStmt = conn.prepareStatement(carsSql)){
                carsStmt.setBoolean(1, object.getCar().getCool());
                ResultSet rs = carsStmt.executeQuery();
                if (rs.next()) {
                    carsId = rs.getLong("id");
                } else {
                    throw new SQLException();
                }
                rs.close();
            }

            try (PreparedStatement humanBeingStmt = conn.prepareStatement(humanBeingSql)){
                humanBeingStmt.setString(1, key);
                humanBeingStmt.setString(2, object.getName());
                humanBeingStmt.setLong(3, coordinatesId);
                humanBeingStmt.setBoolean(4, object.getRealHero());
                humanBeingStmt.setBoolean(5, object.getHasToothpick());
                humanBeingStmt.setDouble(6, object.getImpactSpeed());
                humanBeingStmt.setString(7, object.getSoundtrackName());
                humanBeingStmt.setLong(8, object.getMinutesOfWaiting());
                humanBeingStmt.setString(9, object.getWeaponType().toString());
                humanBeingStmt.setLong(10, carsId);
                humanBeingStmt.setLong(11, userId);
                humanBeingStmt.executeUpdate();
                conn.commit();
            }
        }catch (SQLException e){
            ServerLogger.error("Ошибка добавления элемента");
        }
    }

    @Override
    public void deleteObject(String key){
        try (Connection conn = DriverManager.getConnection(url, user, password)){
            String sql = "DELETE FROM human_beings WHERE key = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setString(1, key);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка удаления элемента");
        }
    }

    @Override
    public void clear(Long userId){
        try (Connection conn = DriverManager.getConnection(url, user, password)){
            String sql = "DELETE FROM human_beings WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)){
                stmt.setLong(1, userId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            ServerLogger.error("Ошибка очистки элементов");
        }
    }
}
