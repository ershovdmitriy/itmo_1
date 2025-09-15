package lab7.server.network;

import lab7.common.service.CommandResponse;

import java.net.DatagramSocket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResponseSender {
    private final ExecutorService responsePool;
    private final DatagramSocket serverSocket;

    public ResponseSender(DatagramSocket serverSocket, int poolSize) {
        this.serverSocket = serverSocket;
        this.responsePool = Executors.newFixedThreadPool(poolSize);
    }

    public void sendResponse(CommandResponse<?> response, SocketAddress clientAddress) {
        Runnable sendTask = new SendResponseTask(serverSocket, response, clientAddress);
        responsePool.execute(sendTask);
    }

    public void shutdown() {
        responsePool.shutdown();
    }
}
