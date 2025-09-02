package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collection.HavingId;
import lab5.collectionManagers.CollectionManager;
import lab5.collectionManagers.IdManager;
import lab5.collectionManagers.buildersManagers.ObjectBuilder;
import lab5.commandManagers.Command;

public class Update<T extends HavingId> extends Command {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final ObjectBuilder<T> builder;
  private final IdManager<T> idManager;

  public Update(
      CollectionManager<? extends Map<String, T>, T> manager,
      ObjectBuilder<T> builder,
      IdManager<T> idManager) {
    super(false);
    this.manager = manager;
    this.builder = builder;
    this.idManager = idManager;
  }

  @Override
  public String getName() {
    return "update [id]";
  }

  @Override
  public String getDescr() {
    return "Обновляет значение элемента коллекции, id которого равен заданному";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("update имеет аргумент типа int");
      return false;
    } else {
      try {
        Integer.parseInt((String) inputArgument);
        return true;
      } catch (NumberFormatException e) {
        System.out.println("update имеет аргумент типа int");
        return false;
      }
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      String key = idManager.checkObjectById(Integer.parseInt(getArgument()));
      if (key == null) {
        System.out.println("Элемента с таким id-номером нет в текущей коллекции!");
      } else {
        manager.addElementToCollection(key, builder.build());
        System.out.println("Элемента с данным ID успешно заменен.");
      }
    }
  }
}
