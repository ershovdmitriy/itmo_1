package lab7.server.commands;

import lab7.common.collection.HumanBeing.comparators.*;
import lab7.server.collections.HumanBeingManager;
import lab7.server.collections.MetaSqlManager;
import lab7.server.collections.UserManager;
import lab7.server.commands.HumanBeingCommands.*;

import java.util.LinkedHashMap;

public class CommandMapForHumanBeing implements CommandMap<LinkedHashMap<String, ServerCommand>> {

  private final LinkedHashMap<String, ServerCommand> commandMap;

  public CommandMapForHumanBeing(
          HumanBeingManager humanBeingManager, UserManager userManager, MetaSqlManager metaSqlManager) {
    this.commandMap = new LinkedHashMap<>();
    commandMap.put("info", new Info(humanBeingManager, metaSqlManager));
    commandMap.put("help", new Help(commandMap));
    commandMap.put("show", new Show<>(humanBeingManager));
    commandMap.put("insert", new Insert<>(humanBeingManager, userManager));
    commandMap.put("update", new Update<>(humanBeingManager, userManager));
    commandMap.put("remove_key", new RemoveKey(humanBeingManager, userManager));
    commandMap.put("clear", new Clear(humanBeingManager, userManager));
    commandMap.put(
        "replace_if_lowe", new ReplaceIfLowe<>(humanBeingManager, new HumanBeingComparator(), userManager));
    commandMap.put("remove_greater_key", new RemoveGreaterKey(humanBeingManager, userManager));
    commandMap.put("remove_lower_key", new RemoveLowerKey(humanBeingManager, userManager));
    commandMap.put("remove_all_by_impact_speed", new RemoveAllByImpactSpeed(humanBeingManager, userManager));
    commandMap.put(
        "min_by_impact_speed",
        new MinByImpactSpeed(humanBeingManager, new HumanBeingComparatorByImpactSpeed()));
    commandMap.put(
        "count_greater_than_impact_speed", new CountGreaterThanImpactSpeed(humanBeingManager));
    commandMap.put("authorization", new Authorization());
    commandMap.put("registration", new Registration(userManager));
  }

  public LinkedHashMap<String, ServerCommand> getCommandMap() {
    return commandMap;
  }
}
