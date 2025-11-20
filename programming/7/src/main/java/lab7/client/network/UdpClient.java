package lab7.client.network;

import lab7.common.service.CommandRequest;
import lab7.common.service.CommandResponse;
import lab7.common.service.Serializer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class UdpClient implements AutoCloseable {
  private DatagramChannel channel;
  private final int timeout;
  private final int maxRetries;
  private ByteBuffer responseBuffer = ByteBuffer.allocate(65536);
  private ByteBuffer requestBuffer = ByteBuffer.allocate(65536);

  public UdpClient(int timeout, int maxRetries){
    this.timeout = timeout;
    this.maxRetries = maxRetries;
  }

  public void connect(String host, int port) throws IOException {
    InetSocketAddress serverAddress = new InetSocketAddress(host, port);

    this.channel = DatagramChannel.open();
    channel.configureBlocking(false);
    channel.connect(serverAddress);
  }

  public CommandResponse<?> sendRequest(CommandRequest<?, ?> request)
      throws ServerUnavailableException {
    try {
      byte[] requestData = Serializer.serialize(request);
      requestBuffer.clear();
      responseBuffer.clear();
      requestBuffer = ByteBuffer.wrap(requestData);

      int retries = 0;
      while (retries < maxRetries) {
        try {
          channel.write(requestBuffer);
          long startTime = System.currentTimeMillis();
          while (System.currentTimeMillis() - startTime < timeout) {
            try {
              if (channel.read(responseBuffer) > 0) {
                responseBuffer.flip();
                return Serializer.deserialize(responseBuffer.array(), CommandResponse.class);
              }
            } catch (PortUnreachableException e) {
            }
            Thread.sleep(100);
          }

          retries++;
          System.out.println("Таймаут ответа, попытка " + retries + "/" + maxRetries);

        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new ServerUnavailableException("Прервано ожидание ответа:" + e.getMessage());
        } catch (ClassNotFoundException e) {
          throw new ServerUnavailableException("Ошибка при передаче данных:" + e.getMessage());
        }
      }
    } catch (IOException e) {
      throw new ServerUnavailableException("Ошибка подключения к серверу");
    }
    throw new ServerUnavailableException("Сервер недоступен после " + maxRetries + " попыток");
  }

  @Override
  public void close() throws IOException {
    if (channel != null && channel.isOpen()) {
      channel.close();
    }
  }
}
