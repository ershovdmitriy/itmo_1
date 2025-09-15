package lab7.common.service;

import java.io.*;

public class Serializer {
  public static byte[] serialize(Object obj) throws IOException {
    try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
      objectStream.writeObject(obj);
      return byteStream.toByteArray();
    }
  }

  public static <T> T deserialize(byte[] data, Class<T> clazz)
      throws IOException, ClassNotFoundException {
    try (ByteArrayInputStream byteStream = new ByteArrayInputStream(data);
        ObjectInputStream objectStream = new ObjectInputStream(byteStream)) {
      return clazz.cast(objectStream.readObject());
    }
  }
}
