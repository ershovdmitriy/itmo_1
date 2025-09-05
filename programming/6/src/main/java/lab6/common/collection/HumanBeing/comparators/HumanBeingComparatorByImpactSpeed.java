package lab6.common.collection.HumanBeing.comparators;

import java.util.Comparator;
import lab6.common.collection.HumanBeing.HumanBeing;

public class HumanBeingComparatorByImpactSpeed implements Comparator<HumanBeing> {

  @Override
  public int compare(HumanBeing o1, HumanBeing o2) {
    if (o1 == null) {
      if (o2 == null) {
        return 0;
      } else {
        return -1;
      }
    }
    return Long.compare(o1.getImpactSpeed(), o2.getImpactSpeed());
  }
}
