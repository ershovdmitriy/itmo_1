package lab5.collectionManagers.buildersManagers;

import java.util.Scanner;

public interface ObjectBuilder<T> {
  T build() throws IllegalArgumentException;

  void setScanner(Scanner scanner);
}
