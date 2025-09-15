package lab7.client.builders;

import java.util.Scanner;

public abstract class ObjectBuilder<T> {
  protected Scanner scanner;

  public abstract T build() throws IllegalArgumentException;

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }
}
