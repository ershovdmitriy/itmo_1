package lab5;

import java.util.HashMap;
import java.util.LinkedHashMap;
import lab5.collection.HumanBeing.HumanBeing;
import lab5.collectionManagers.HumanBeingManagers;
import lab5.collectionManagers.IdManager;
import lab5.collectionManagers.JsonManager;
import lab5.collectionManagers.buildersManagers.humanBeingBuilder.HumanBeingBuilder;
import lab5.commandManagers.Command;
import lab5.commandManagers.CommandExecutor;
import lab5.commandManagers.CommandMapForHumanBeing;

public class Main {
  public static void main(String[] args) {
    JsonManager<LinkedHashMap<String, HumanBeing>> jsonManager = new JsonManager<>();
    HumanBeingManagers humanBeingManagers = new HumanBeingManagers(jsonManager, "FILE_NAME");
    IdManager<HumanBeing> idManager = new IdManager<>(humanBeingManagers);
    HumanBeingBuilder humanBeingBuilder = new HumanBeingBuilder(idManager);
    CommandExecutor<LinkedHashMap<String, Command>, HumanBeing> commandExecutor =
        new CommandExecutor<>(humanBeingBuilder);
    CommandMapForHumanBeing commandMap =
        new CommandMapForHumanBeing(
            humanBeingManagers, humanBeingBuilder, idManager, commandExecutor);
    commandExecutor.setCommandMap(commandMap.getCommandMap());

    System.out.println("Программа успешно запущена.");
    System.out.println("Коллекция успешно загружена из файла.");
    System.out.println("Для списка доступных команд можно вызвать команду help.");
    commandExecutor.startExecuting(System.in);
  }
}
