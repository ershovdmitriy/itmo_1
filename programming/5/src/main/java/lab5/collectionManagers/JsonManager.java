package lab5.collectionManagers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.*;

public class JsonManager<T> implements FileManager<T> {
  private final ObjectMapper objectMapper;

  public JsonManager() {
    this.objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.registerModule(new JavaTimeModule());
  }

  @Override
  public T readFromFile(String pathToDataFile, TypeReference<T> typeReference) throws IOException {
    try (InputStreamReader reader = new InputStreamReader(new FileInputStream(pathToDataFile))) {
      return objectMapper.readValue(reader, typeReference);

    } catch (IOException e) {
      throw new IOException("Ошибка при загрузке объекта из файла: " + e.getMessage());
    }
  }

  @Override
  public void writeToFile(String pathToDataFile, T object) throws IOException {
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(pathToDataFile))) {
      objectMapper.writeValue(writer, object);

    } catch (IOException e) {
      throw new IOException("Ошибка при записи объекта в файла: " + e.getMessage());
    }
  }
}
