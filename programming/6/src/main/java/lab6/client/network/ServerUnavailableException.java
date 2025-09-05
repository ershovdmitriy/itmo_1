package lab6.client.network;

public class ServerUnavailableException extends RuntimeException {
  public ServerUnavailableException(String message) {
    super(message);
  }
}
