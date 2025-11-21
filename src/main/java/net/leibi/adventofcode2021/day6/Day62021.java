package net.leibi.adventofcode2021.day6;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static net.leibi.helpers.InputHelper.getIntArrayFromString;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day62021 {

  static List<Integer> getFishesFromInput(String input) {
    return Arrays.stream(getIntArrayFromString(input.trim(), ",")).boxed().toList();
  }

   static BigDecimal getNumberOfFishes(List<Integer> inputIntegers,int days) {
    var map = inputIntegers
        .stream()
        .collect(Collectors.groupingBy(Integer::intValue))
        .entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> new BigDecimal(e.getValue().size())));
    for (int i = 0; i < days; i++) {
      Map<Integer, BigDecimal> newMap = new HashMap<>();
      BigDecimal newFishes = BigDecimal.ZERO;
      for (Map.Entry<Integer,BigDecimal> entry : map.entrySet()) {
        if (entry.getKey() == 0) {
          newFishes = entry.getValue();
          newMap.merge(6, entry.getValue(), BigDecimal::add);
        } else {
          newMap.merge(entry.getKey() - 1, entry.getValue(), BigDecimal::add);
        }
      }
      newMap.put(8, newFishes);
      map = newMap;
    }

    return map.values().stream().reduce(BigDecimal.ZERO, (BigDecimal::add));
  }

}
