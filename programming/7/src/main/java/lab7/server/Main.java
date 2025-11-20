package lab7.server;

import lab7.common.collection.HumanBeing.HumanBeing;
import lab7.server.collections.*;
import lab7.server.commands.CommandExecutor;
import lab7.server.commands.CommandMapForHumanBeing;
import lab7.server.commands.ServerCommand;
import lab7.server.logging.ServerLogger;
import lab7.server.network.UdpServer;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {
    private static final int BUFFER_SIZE = 65536;

    public static void main(String[] args) {
        try {
            System.out.println("Запуск приложения. Напишите номер порта:");
            int port = (new Scanner(System.in)).nextInt();
            String user, password;
            System.out.print("Введите имя пользователя PostgreSQL: ");
            user = System.console().readLine();
            System.out.print("Введите пароль пользователя PostgreSQL: ");
            password = System.console().readLine();
            HumanBeingSqlManager humanBeingSqlManager = new HumanBeingSqlManager("jdbc:postgresql://localhost:5432/studs", user, password);
            UsersSqlManager usersSqlManager = new UsersSqlManager("jdbc:postgresql://localhost:5432/studs", user, password);
            MetaSqlManager metaSqlManager = new MetaSqlManager("jdbc:postgresql://localhost:5432/studs", user, password);
            HumanBeingManager humanBeingManager = new HumanBeingManager(humanBeingSqlManager, usersSqlManager);
            UserManager userManager = new UserManager(usersSqlManager);
            CommandMapForHumanBeing commandMap = new CommandMapForHumanBeing(humanBeingManager, userManager, metaSqlManager);
            UdpServer<LinkedHashMap<String, ServerCommand>> udpServer = new UdpServer<>(port, BUFFER_SIZE, commandMap, userManager);
            udpServer.start();
        } catch (Exception e) {
            ServerLogger.error("Критическая ошибка при запуске сервера: " + e.getMessage());
        }
    }
}
