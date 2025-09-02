package lab5.commandManagers;

public abstract class Command implements CommandInterface {

  private final boolean hasArgument;

  private String argument;

  public Command(boolean hasArgument) {
    this.hasArgument = hasArgument;
  }

  @Override
  public abstract void execute() throws IllegalArgumentException;

  @Override
  public abstract String getName();

  @Override
  public abstract String getDescr();

  @Override
  public abstract boolean checkArgument(Object inputArgument);

  public boolean isHasArgument() {
    return hasArgument;
  }

  public String getArgument() {
    return argument;
  }

  public void setArgument(String argument) {
    this.argument = argument;
  }
}
