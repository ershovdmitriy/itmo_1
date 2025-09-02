package lab5.exceptions;

public class CommandInterruptedException extends RuntimeException {
  public CommandInterruptedException(String message) {
    super(message);
    System.err.println(message);
  }
}
