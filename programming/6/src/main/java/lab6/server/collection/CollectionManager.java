package lab6.server.collection;

import java.time.LocalDate;
import java.util.Map;

public interface CollectionManager<C extends Map<String, T>, T> {
  C getCollection();

  void setCollection(C value);

  void addElementToCollection(String key, T object);

  void removeElementToCollection(String key);

  void clearCollection();

  LocalDate getCreationDate();

  void loadCollection();

  void saveCollection();

  boolean checkObjectByKey(String key);

  void sortCollection();
}
