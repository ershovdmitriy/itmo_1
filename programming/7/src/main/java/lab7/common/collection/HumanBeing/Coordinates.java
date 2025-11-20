package lab7.common.collection.HumanBeing;

import java.io.Serializable;

public class Coordinates implements Comparable<Coordinates>, Serializable {
  private Double x; // Поле не может быть null
  private long y;

  public Coordinates(Double x, long y) {
    this.x = x;
    this.y = y;
  }

  public Coordinates() {}

  public void setX(Double x) {
    this.x = x;
  }

  public Double getX() {
    return x;
  }

  public void setY(long y) {
    this.y = y;
  }

  public long getY() {
    return y;
  }

  @Override
  public int compareTo(Coordinates o) {
    if (o == null) {
      return 1;
    }
    int result = this.x.compareTo(o.x);
    if (result == 0) result = Long.compare(this.y, o.y);
    return result;
  }

  @Override
  public String toString() {
    return "Coordinates: {" + "x = " + x + ", y = " + y + "}";
  }
}
