package lab7.common.collection.HumanBeing.comparators;

import lab7.common.collection.HumanBeing.HumanBeing;

import java.util.Comparator;

public class HumanBeingComparator implements Comparator<HumanBeing> {

  @Override
  public int compare(HumanBeing o1, HumanBeing o2) {
    if (o1 == null) {
      if (o2 == null) {
        return 0;
      } else {
        return -1;
      }
    }
    return o1.compareTo(o2);
  }
}
