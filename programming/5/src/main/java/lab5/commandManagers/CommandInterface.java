package lab5.commandManagers;

public interface CommandInterface {
  void execute() throws IllegalArgumentException;

  String getName();

  String getDescr();

  boolean checkArgument(Object argument);
}
