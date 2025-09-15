package lab7.server.network;

import lab7.common.service.CommandRequest;
import lab7.common.service.Serializer;
import lab7.server.collections.UserManager;
import lab7.server.commands.CommandExecutor;
import lab7.server.commands.CommandMap;
import lab7.server.commands.ServerCommand;
import lab7.server.logging.ServerLogger;

import java.net.DatagramPacket;
import java.util.Map;

public class RequestHandler<C extends Map<String, ServerCommand>> implements Runnable{

    private final DatagramPacket receivePacket;
    private final C commandMap;
    private final UserManager userManager;
    private final ResponseSender responseSender;

    public RequestHandler(DatagramPacket receivePacket, CommandMap<C> commandMap, UserManager userManager, ResponseSender responseSender) {
        this.receivePacket = receivePacket;
        this.commandMap = commandMap.getCommandMap();
        this.userManager = userManager;
        this.responseSender = responseSender;
    }

    @Override
    public void run() {
        try {
            CommandRequest<?, ?> request = Serializer.deserialize(receivePacket.getData(), CommandRequest.class);
            ServerLogger.log("Получена команда: " + request.getCommandName() + " от пользователя: " + request.getUser());
            ServerCommand command = commandMap.get(request.getCommandName());
            CommandExecutor commandExecutor = new CommandExecutor(command, userManager, request, responseSender, receivePacket.getSocketAddress());
            Thread executionThread = new Thread(commandExecutor);
            executionThread.start();

        } catch (Exception e) {
            System.out.println("RequestHandler");
            System.out.println(e);
            System.out.println(e.getMessage());
        }
    }
}
