package lab5.commandManagers.Commands;

import java.util.Comparator;
import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.collectionManagers.buildersManagers.ObjectBuilder;
import lab5.commandManagers.Command;

public class ReplaceIfLowe<T> extends Command {

  private final CollectionManager<? extends Map<String, T>, T> manager;
  private final ObjectBuilder<T> builder;
  private final Comparator<T> comparator;

  public ReplaceIfLowe(
      CollectionManager<? extends Map<String, T>, T> manager,
      ObjectBuilder<T> builder,
      Comparator<T> comparator) {
    super(false);
    this.manager = manager;
    this.builder = builder;
    this.comparator = comparator;
  }

  @Override
  public String getName() {
    return "replace_if_lowe [key]";
  }

  @Override
  public String getDescr() {
    return "Заменяет значение по ключу, если новое значение меньше старого";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      System.out.println("replace_if_lowe имеет аргумент типа String");
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      T newObject = builder.build();
      if (comparator.compare(newObject, manager.getCollection().get(getArgument())) < 0) {
        manager.addElementToCollection(getArgument(), newObject);
      }
      System.out.println("Элемент с данным ключом изменен.");
    }
  }
}
