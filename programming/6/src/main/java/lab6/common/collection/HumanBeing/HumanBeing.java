package lab6.common.collection.HumanBeing;

import java.io.Serializable;
import java.util.Date;
import lab6.common.collection.HavingId;

public class HumanBeing implements Comparable<HumanBeing>, HavingId, Serializable {
  private int id; // Значение поля должно быть больше 0, Значение этого поля должно быть уникальным,
  // Значение этого поля должно генерироваться автоматически
  private String name; // Поле не может быть null, Строка не может быть пустой
  private Coordinates coordinates; // Поле не может быть null
  private Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
  // автоматически
  private Boolean realHero; // Поле не может быть null
  private boolean hasToothpick;
  private Long impactSpeed;
  private String soundtrackName; // Поле не может быть null
  private long minutesOfWaiting; // Поле может быть null
  private WeaponType weaponType; // Поле не может быть null
  private Car car; // Поле не может быть null

  public HumanBeing(
      int id,
      String name,
      Coordinates coordinates,
      Date creationDate,
      Boolean realHero,
      boolean hasToothpick,
      Long impactSpeed,
      String soundtrackName,
      long minutesOfWaiting,
      WeaponType weaponType,
      Car car) {
    this.id = id;
    this.name = name;
    this.coordinates = coordinates;
    this.creationDate = creationDate;
    this.realHero = realHero;
    this.hasToothpick = hasToothpick;
    this.impactSpeed = impactSpeed;
    this.soundtrackName = soundtrackName;
    this.minutesOfWaiting = minutesOfWaiting;
    this.weaponType = weaponType;
    this.car = car;
  }

  public HumanBeing() {}

  public void setId(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  public Coordinates getCoordinates() {
    return coordinates;
  }

  public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
  }

  public Date getCreationDate() {
    return creationDate;
  }

  public void setRealHero(Boolean realHero) {
    this.realHero = realHero;
  }

  public Boolean getRealHero() {
    return realHero;
  }

  public void setHasToothpick(boolean hasToothpick) {
    this.hasToothpick = hasToothpick;
  }

  public boolean getHasToothpick() {
    return hasToothpick;
  }

  public void setImpactSpeed(Long impactSpeed) {
    this.impactSpeed = impactSpeed;
  }

  public Long getImpactSpeed() {
    return impactSpeed;
  }

  public void setSoundtrackName(String soundtrackName) {
    this.soundtrackName = soundtrackName;
  }

  public String getSoundtrackName() {
    return soundtrackName;
  }

  public void setMinutesOfWaiting(long minutesOfWaiting) {
    this.minutesOfWaiting = minutesOfWaiting;
  }

  public long getMinutesOfWaiting() {
    return minutesOfWaiting;
  }

  public void setWeaponType(WeaponType weaponType) {
    this.weaponType = weaponType;
  }

  public WeaponType getWeaponType() {
    return weaponType;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public Car getCar() {
    return car;
  }

  @Override
  public int compareTo(HumanBeing o) {
    int result = this.name.compareTo(o.name);
    if (result == 0) {
      result = this.coordinates.compareTo(o.coordinates);
    }
    if (result == 0) {
      result = this.creationDate.compareTo(o.creationDate);
    }
    if (result == 0) {
      result = this.realHero.compareTo(o.realHero);
    }
    if (result == 0) {
      result = Boolean.compare(this.hasToothpick, o.hasToothpick);
    }
    if (result == 0) {
      if (this.impactSpeed != null) {
        result = this.impactSpeed.compareTo(o.impactSpeed);
      } else if (o.impactSpeed != null) {
        result = -o.impactSpeed.compareTo(this.impactSpeed);
      }
    }
    if (result == 0) {
      result = this.soundtrackName.compareTo(o.soundtrackName);
    }
    if (result == 0) {
      result = Long.compare(this.minutesOfWaiting, o.minutesOfWaiting);
    }
    if (result == 0) {
      result = this.weaponType.compareTo(o.weaponType);
    }
    if (result == 0) {
      result = this.car.compareTo(o.car);
    }
    return result;
  }

  @Override
  public String toString() {
    String result;
    result =
        "HumanBeing: {"
            + "id = "
            + id
            + ", name = '"
            + name
            + "'"
            + ", coordinates = "
            + coordinates.toString()
            + ", creationDate = "
            + creationDate.toString()
            + ", realHero = "
            + realHero
            + ", hasToothpick = "
            + hasToothpick;
    if (impactSpeed == null) {
      result += ", impactSpeed = null";
    } else {
      result += ", impactSpeed = " + impactSpeed;
    }
    result +=
        ", soundtrackName = "
            + soundtrackName
            + ", minutesOfWaiting = "
            + minutesOfWaiting
            + ", weaponType = "
            + weaponType
            + ", car = "
            + car
            + "}";
    return result;
  }
}
