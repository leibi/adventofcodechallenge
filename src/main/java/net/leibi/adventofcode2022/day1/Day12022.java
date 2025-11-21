package net.leibi.adventofcode2022.day1;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.leibi.helpers.InputHelper.getIntegerListFromInput;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Day12022 {

  public static Integer getMostCalories( final String input) {
    return getElfesFromInput(input).stream().map(Elf::totalCalories).max(Integer::compareTo)
        .orElse(0);
  }

  public static Integer getCaloriesTopThreeElfes( final String input) {
    return getElfesFromInput(input).stream().map(Elf::totalCalories)
        .sorted((i1, i2) -> Integer.compare(i2,i1))
        .limit(3)
        .reduce(0,Integer::sum);

  }

  static List<Elf> getElfesFromInput(final String input) {
    List<String> substrings = Arrays.stream(input.split("\\n\\n")).toList();
    return getElfesFromStringslist(substrings);
  }

  private static List<Elf> getElfesFromStringslist( final List<String> substrings) {
    List<Elf> elfes = new ArrayList<>();
    for (String substring : substrings) {
      elfes.add(new Elf(getIntegerListFromInput(substring)));
    }
    return elfes;
  }


}
