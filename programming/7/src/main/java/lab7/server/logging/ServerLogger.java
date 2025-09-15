package lab7.server.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerLogger {
  private static final String LOG_FILE = "server.log";
  private static final DateTimeFormatter TIMESTAMP_FORMAT =
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  private static PrintWriter logWriter;

  static {
    try {
      File logFile = new File(LOG_FILE);
      boolean fileExists = logFile.exists();
      logWriter = new PrintWriter(new FileWriter(logFile, true));
      if (!fileExists) {
        logWriter.println("===== Server Log Started =====");
      }
    } catch (IOException e) {
      System.err.println("Не удалось запустить логирование: " + e.getMessage());
      logWriter = null;
    }
  }

  public static synchronized void log(String message) {
    logInternal("INFO", message);
  }

  public static synchronized void error(String message) {
    logInternal("ERROR", message);
  }

  private static void logInternal(String level, String message) {
    String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
    String logEntry = String.format("[%s] [%s] %s", timestamp, level, message);
    System.out.println(logEntry);
    if (logWriter != null) {
      logWriter.println(logEntry);
      logWriter.flush();
    }
  }

  public static void close() {
    if (logWriter != null) {
      log("Завершение работы сервера");
      logWriter.close();
    }
  }
}
