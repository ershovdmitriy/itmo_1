package lab6.client.builders;

import java.util.Scanner;

public abstract class ObjectBuilderNew<T> {
  protected Scanner scanner;

  public abstract T build() throws IllegalArgumentException;

  public void setScanner(Scanner scanner) {
    this.scanner = scanner;
  }
}
