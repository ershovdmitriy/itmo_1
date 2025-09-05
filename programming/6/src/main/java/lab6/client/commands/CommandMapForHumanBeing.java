package lab6.client.commands;

import java.util.LinkedHashMap;
import lab6.client.builders.humanBeingBuilder.HumanBeingBuilder;
import lab6.client.commands.HumanBeingCommands.*;

public class CommandMapForHumanBeing implements CommandMap<LinkedHashMap<String, ClientCommand>> {

  private final LinkedHashMap<String, ClientCommand> commandMap;

  public CommandMapForHumanBeing(HumanBeingBuilder humanBeingBuilder) {
    this.commandMap = new LinkedHashMap<>();
    commandMap.put("info", new Info());
    commandMap.put("help", new Help());
    commandMap.put("show", new Show());
    commandMap.put("insert", new Insert<>(humanBeingBuilder));
    commandMap.put("update", new Update<>(humanBeingBuilder));
    commandMap.put("remove_key", new RemoveKey());
    commandMap.put("clear", new Clear());
    commandMap.put("replace_if_lowe", new ReplaceIfLowe<>(humanBeingBuilder));
    commandMap.put("remove_greater_key", new RemoveGreaterKey());
    commandMap.put("remove_lower_key", new RemoveLowerKey());
    commandMap.put("remove_all_by_impact_speed", new RemoveAllByImpactSpeed());
    commandMap.put("min_by_impact_speed", new MinByImpactSpeed());
    commandMap.put("count_greater_than_impact_speed", new CountGreaterThanImpactSpeed());
  }

  public LinkedHashMap<String, ClientCommand> getCommandMap() {
    return commandMap;
  }
}
