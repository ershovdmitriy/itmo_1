package lab7.server.collections;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.common.collection.User;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HumanBeingManager
    implements CollectionManager<LinkedHashMap<String, HumanBeing>, HumanBeing> {
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
  private LinkedHashMap<String, HumanBeing> humanBeingLinkedHashMap;
  private final HumanBeingSqlManager sqlManager;
  private final UsersSqlManager usersSqlManager;

  public HumanBeingManager(HumanBeingSqlManager sqlManager, UsersSqlManager usersSqlManager) {
    this.sqlManager = sqlManager;
    this.usersSqlManager = usersSqlManager;
    loadCollection();
  }

  @Override
  public void loadCollection() {
    readWriteLock.writeLock().lock();
    try {
      humanBeingLinkedHashMap = sqlManager.loadData();
    } catch (Exception e) {
     throw new RuntimeException("Ошибка подключения к базе данных");
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public LinkedHashMap<String, HumanBeing> getCollection() {
    readWriteLock.readLock().lock();
    try {
      return humanBeingLinkedHashMap;
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  @Override
  public void addElementToCollection(String key, HumanBeing humanBeing, User user) {
    readWriteLock.writeLock().lock();
    try{
      humanBeing.setUser(user);
      sqlManager.addObject(key, humanBeing, usersSqlManager.getIdUser(user));
      loadCollection();
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public void updateElementToCollection(String key, HumanBeing humanBeing, User user) {
    readWriteLock.writeLock().lock();
    try {
      HumanBeing existingHuman = humanBeingLinkedHashMap.get(key);
      if (existingHuman != null && existingHuman.getUser().compareTo(user) == 0) {
        removeElementToCollection(key, user);
        addElementToCollection(key, humanBeing, user);
      }
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public void removeElementToCollection(String key, User user) {
    readWriteLock.writeLock().lock();
    try {
      HumanBeing human = humanBeingLinkedHashMap.get(key);
      if (human != null && human.getUser().compareTo(user) == 0) {
        humanBeingLinkedHashMap.remove(key);
        sqlManager.deleteObject(key);
      }
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public void clearCollection(User user) {
    readWriteLock.writeLock().lock();
    try {
      Long userId = usersSqlManager.getIdUser(user);
      sqlManager.clear(userId);
      var iterator = humanBeingLinkedHashMap.entrySet().iterator();
      while (iterator.hasNext()) {
        var entry = iterator.next();
        if (entry.getValue().getUser().compareTo(user) == 0) {
          iterator.remove();
        }
      }
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public boolean checkObjectByKey(String key) {
    readWriteLock.readLock().lock();
    try {
      return humanBeingLinkedHashMap.containsKey(key);
    } finally {
      readWriteLock.readLock().unlock();
    }
  }

  @Override
  public void sortCollection() {
    readWriteLock.writeLock().lock();
    try {
      List<Map.Entry<String, HumanBeing>> entries = new ArrayList<>(humanBeingLinkedHashMap.entrySet());
      entries.sort(Map.Entry.comparingByKey());
      humanBeingLinkedHashMap.clear();
      for (Map.Entry<String, HumanBeing> entry : entries) {
        humanBeingLinkedHashMap.put(entry.getKey(), entry.getValue());
      }
    } finally {
      readWriteLock.writeLock().unlock();
    }
  }

  @Override
  public String getKeyById(Integer id) {
    readWriteLock.readLock().lock();
    try {
      for (Map.Entry<String, HumanBeing> entry : humanBeingLinkedHashMap.entrySet()) {
        if (entry.getValue().getId() == id) {
          return entry.getKey();
        }
      }
      return null;
    } finally {
      readWriteLock.readLock().unlock();
    }
  }
}
