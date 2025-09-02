package lab5.commandManagers.Commands;

import java.util.Map;
import lab5.collectionManagers.CollectionManager;
import lab5.commandManagers.Command;

public class Info extends Command {

  private final CollectionManager<? extends Map<?, ?>, ?> manager;

  public Info(CollectionManager<? extends Map<?, ?>, ?> manager) {
    super(false);
    this.manager = manager;
  }

  @Override
  public String getName() {
    return "info";
  }

  @Override
  public String getDescr() {
    return "Выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
  }

  @Override
  public boolean checkArgument(Object inputArgument) {
    if (inputArgument == null) {
      return true;
    } else {
      System.out.println("info не имеет аргументов!");
      return false;
    }
  }

  @Override
  public void execute() {
    if (checkArgument(getArgument())) {
      System.out.println("Информация о коллекции:");
      System.out.println("Тип коллекции: " + manager.getCollection().getClass());
      System.out.println("Дата создания: " + manager.getCreationDate());
      System.out.println("Количество элементов: " + manager.getCollection().size());
    }
  }
}
