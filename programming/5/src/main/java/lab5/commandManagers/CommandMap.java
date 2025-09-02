package lab5.commandManagers;

import java.util.Map;

public interface CommandMap<C extends Map<String, Command>> {
  C getCommandMap();
}
