package lab7.client.network;

public class ServerUnavailableException extends RuntimeException {
  public ServerUnavailableException(String message) {
    super(message);
  }
}
