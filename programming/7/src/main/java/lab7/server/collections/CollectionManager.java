package lab7.server.collections;

import lab7.common.collection.User;

import java.util.Map;

public interface CollectionManager<C extends Map<String, T>, T> {
  C getCollection();

  void addElementToCollection(String key, T object, User user);

  void updateElementToCollection(String key, T object, User user);

  void removeElementToCollection(String key, User user);

  void clearCollection(User user);

  void loadCollection();

  boolean checkObjectByKey(String key);

  void sortCollection();

  String getKeyById(Integer id);
}
