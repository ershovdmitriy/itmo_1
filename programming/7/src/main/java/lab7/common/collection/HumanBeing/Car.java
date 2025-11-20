package lab7.common.collection.HumanBeing;

import java.io.Serializable;

public class Car implements Comparable<Car>, Serializable {
  private boolean cool;

  public Car(boolean cool) {
    this.cool = cool;
  }

  public Car() {}

  public void setCool(boolean cool) {
    this.cool = cool;
  }

  public boolean getCool() {
    return cool;
  }

  @Override
  public int compareTo(Car o) {
    if (o == null) {
      return 1;
    }
    return Boolean.compare(this.cool, o.cool);
  }

  @Override
  public String toString() {
    if (cool) {
      return "Cool car";
    }
    return "Not a cool car";
  }
}
