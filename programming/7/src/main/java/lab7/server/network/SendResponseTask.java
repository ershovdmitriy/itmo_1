package lab7.server.network;

import lab7.common.service.CommandResponse;
import lab7.common.service.Serializer;
import lab7.server.logging.ServerLogger;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketAddress;

public class SendResponseTask implements Runnable {
    private final DatagramSocket serverSocket;
    private final CommandResponse<?> response;
    private final SocketAddress clientAddress;

    public SendResponseTask(DatagramSocket serverSocket, CommandResponse<?> response, SocketAddress clientAddress) {
        this.serverSocket = serverSocket;
        this.response = response;
        this.clientAddress = clientAddress;
    }

    @Override
    public void run() {
        try {
            byte[] responseData = Serializer.serialize(response);
            DatagramPacket responsePacket = new DatagramPacket(
                    responseData,
                    responseData.length,
                    clientAddress
            );
            serverSocket.send(responsePacket);
            ServerLogger.log("Ответ успешно отправлен пользователю");
        } catch (Exception e) {
            ServerLogger.error("Не удалось отправить ответ пользователю");
        }
    }
}
