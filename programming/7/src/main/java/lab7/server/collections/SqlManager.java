package lab7.server.collections;

import lab7.common.collection.User;

import java.util.Map;

public interface SqlManager<C extends Map<String, T>, T>{
    C loadData();

    void addObject(String key, T obj, Long id);

    void deleteObject(String key);

    void clear(Long id);
}
