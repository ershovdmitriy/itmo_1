package lab6.client.builders;

import java.util.Scanner;

public interface ObjectBuilder<T> {
  T build() throws IllegalArgumentException;

  void setScanner(Scanner scanner);
}
