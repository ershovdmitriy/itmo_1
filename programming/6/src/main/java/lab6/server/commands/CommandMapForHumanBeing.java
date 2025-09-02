package lab6.server.commands;

import java.util.LinkedHashMap;
import lab6.common.collection.HumanBeing.HumanBeing;
import lab6.common.collection.HumanBeing.comparators.*;
import lab6.server.collection.HumanBeingManagers;
import lab6.server.collection.IdManager;
import lab6.server.commands.HumanBeingCommands.*;

public class CommandMapForHumanBeing implements CommandMap<LinkedHashMap<String, ServerCommand>> {

  private final LinkedHashMap<String, ServerCommand> commandMap;

  public CommandMapForHumanBeing(
      HumanBeingManagers humanBeingManagers, IdManager<HumanBeing> idManager) {
    this.commandMap = new LinkedHashMap<>();
    commandMap.put("info", new Info(humanBeingManagers));
    commandMap.put("help", new Help(commandMap));
    commandMap.put("show", new Show<>(humanBeingManagers));
    commandMap.put("insert", new Insert<>(humanBeingManagers));
    commandMap.put("update", new Update<>(humanBeingManagers, idManager));
    commandMap.put("remove_key", new RemoveKey(humanBeingManagers));
    commandMap.put("clear", new Clear(humanBeingManagers));
    commandMap.put(
        "replace_if_lowe", new ReplaceIfLowe<>(humanBeingManagers, new HumanBeingComparator()));
    commandMap.put("remove_greater_key", new RemoveGreaterKey(humanBeingManagers));
    commandMap.put("remove_lower_key", new RemoveLowerKey(humanBeingManagers));
    commandMap.put("remove_all_by_impact_speed", new RemoveAllByImpactSpeed(humanBeingManagers));
    commandMap.put(
        "min_by_impact_speed",
        new MinByImpactSpeed(humanBeingManagers, new HumanBeingComparatorByImpactSpeed()));
    commandMap.put(
        "count_greater_than_impact_speed", new CountGreaterThanImpactSpeed(humanBeingManagers));
  }

  public LinkedHashMap<String, ServerCommand> getCommandMap() {
    return commandMap;
  }
}
