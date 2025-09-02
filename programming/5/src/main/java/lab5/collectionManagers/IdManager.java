package lab5.collectionManagers;

import java.util.HashSet;
import java.util.Map;
import lab5.collection.HavingId;

public class IdManager<T extends HavingId> {

  private final HashSet<Integer> usedIds;
  private final CollectionManager<? extends Map<String, T>, T> manager;

  public IdManager(CollectionManager<? extends Map<String, T>, T> manager) {
    usedIds = new HashSet<>();
    this.manager = manager;
  }

  public boolean isTaken(int id) {
    return usedIds.contains(id);
  }

  public void takeId(int id) {
    usedIds.add(id);
  }

  public int generateId() {
    int id;
    do {
      id = (int) (Math.random() * Integer.MAX_VALUE) + 1;
    } while (isTaken(id));
    takeId(id);
    return id;
  }

  public String checkObjectById(int id) {
    for (String key : manager.getCollection().keySet()) {
      if (manager.getCollection().get(key).getId() == id) {
        return key;
      }
    }
    return null;
  }
}
