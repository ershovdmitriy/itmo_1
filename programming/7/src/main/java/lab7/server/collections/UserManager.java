package lab7.server.collections;

import lab7.common.collection.User;
import lab7.server.logging.ServerLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class UserManager {
    private ArrayList<User> users;
    private final UsersSqlManager sqlManager;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public UserManager(UsersSqlManager sqlManager) {
        this.sqlManager = sqlManager;
        users = sqlManager.getUsers();
    }

    public void loadCollection(){
        lock.writeLock().lock();
        try {
            users = sqlManager.getUsers();
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean addUser(User user) {
        lock.writeLock().lock();
        try {
            if (users.contains(user)) {
                return false;
            }
            sqlManager.addUser(user);
            loadCollection();
            return true;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public User getUser(String login, String password) {
        lock.readLock().lock();
        try {
            password += "81=VLfEr";
            try {
                MessageDigest md2 = MessageDigest.getInstance("MD2");
                byte[] hashBytes = md2.digest(password.getBytes());
                password = HexFormat.of().formatHex(hashBytes);
            } catch (NoSuchAlgorithmException e) {
                ServerLogger.error("Ошибка при хешировании пароля");
            }
            return new User(login, password);
        } finally {
            lock.readLock().unlock();
        }
    }

    public boolean checkUser(User user) {
        lock.readLock().lock();
        try {
            for (User u : users) {
                if (u.compareTo(user) == 0) {
                    return true;
                }
            }
            return false;
        } finally {
            lock.readLock().unlock();
        }
    }
}
