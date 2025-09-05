package lab6.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import lab6.common.service.CommandRequest;
import lab6.common.service.CommandResponse;
import lab6.common.service.Serializer;

public class UdpClient implements AutoCloseable {
  private DatagramChannel channel;
  private int timeout;
  private ByteBuffer responseBuffer = ByteBuffer.allocate(65536);
  private ByteBuffer requestBuffer = ByteBuffer.allocate(65536);

  public void connect(String host, int port, int timeout) throws IOException {
    InetSocketAddress serverAddress = new InetSocketAddress(host, port);
    this.timeout = timeout;

    this.channel = DatagramChannel.open();
    channel.configureBlocking(false);
    channel.connect(serverAddress);
  }

  public CommandResponse<?> sendRequest(CommandRequest<?, ?> request, int maxRetries)
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
