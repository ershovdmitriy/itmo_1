package lab5.collectionManagers;

import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import lab5.collection.HumanBeing.HumanBeing;

public class HumanBeingManagers
    implements CollectionManager<LinkedHashMap<String, HumanBeing>, HumanBeing> {
  private LinkedHashMap<String, HumanBeing> humanBeingLinkedHashMap;
  private final String pathToDataFile;
  private final FileManager<LinkedHashMap<String, HumanBeing>> fileManager;
  private final LocalDate creationDate;

  public HumanBeingManagers(
      FileManager<LinkedHashMap<String, HumanBeing>> fileManager, String envKey) {
    creationDate = LocalDate.now();
    humanBeingLinkedHashMap = new LinkedHashMap<>();
    this.fileManager = fileManager;
    this.pathToDataFile = System.getenv(envKey);
    if (pathToDataFile == null) {
      System.out.println("Переменной окружения lab5 не существует!");
    } else if (pathToDataFile.trim().split(" ").length != 1) {
      System.out.println(
          "Некорректно задана переменная окружения lab5! "
              + "\nЗадайте переменную окружения с именем \"lab5\", поместив туда полный путь к .json файлу.");
    }
    loadCollection();
  }

  @Override
  public void loadCollection() {
    try {
      humanBeingLinkedHashMap = fileManager.readFromFile(pathToDataFile, new TypeReference<>() {});
    } catch (IOException | IllegalArgumentException e) {
      throw new IllegalArgumentException("Ошибка при загрузке коллекции: " + e.getMessage());
    }
  }

  @Override
  public void saveCollection() {
    try {
      fileManager.writeToFile(pathToDataFile, humanBeingLinkedHashMap);
    } catch (IOException e) {
      throw new IllegalArgumentException("Ошибка при сохранении коллекции: " + e.getMessage());
    }
  }

  @Override
  public LinkedHashMap<String, HumanBeing> getCollection() {
    return humanBeingLinkedHashMap;
  }

  @Override
  public void setCollection(LinkedHashMap<String, HumanBeing> humanBeingLinkedHashMap) {
    this.humanBeingLinkedHashMap = humanBeingLinkedHashMap;
  }

  @Override
  public void addElementToCollection(String key, HumanBeing humanBeing) {
    humanBeingLinkedHashMap.put(key, humanBeing);
  }

  @Override
  public void removeElementToCollection(String key) {
    humanBeingLinkedHashMap.remove(key);
  }

  @Override
  public void clearCollection() {
    humanBeingLinkedHashMap.clear();
  }

  @Override
  public LocalDate getCreationDate() {
    return creationDate;
  }

  @Override
  public boolean checkObjectByKey(String key) {
    return humanBeingLinkedHashMap.containsKey(key);
  }
}
