package net.leibi.adventofcode2025.day2;

import java.util.List;

class Day2 {

  record Range(Long min, Long max) {
    List<Long> toList() {
      return java.util.stream.LongStream.rangeClosed(min, max).boxed().toList();
    }

    List<Long> toInvalidNumberList() {
      return java.util.stream.LongStream.rangeClosed(min, max)
          .boxed()
          .filter(Day2::isNumberInvalid)
          .toList();
    }
  }

  static boolean isNumberInvalid(Long number) {
    // any number we can splint in two parts where the two parts are the same. E.g.
    // 1212 -> 12 | 12
    String numStr = number.toString();
    int len = numStr.length();
    if (len % 2 != 0) {
      return false;
    }
    var splitPos = len / 2;
    var part1 = numStr.substring(0, splitPos);
    var part2 = numStr.substring(splitPos, splitPos + splitPos);
    if (part1.equals(part2)) {
      return true;
    }
    return false;
  }

  static Long day2Part1(String input) {
    // split input in to ranges. Each range is in format "min-max", e.g.
    // "123456-654321"
    String[] ranges = input.split(",");
    Long result = 0L;
    for (String rangeString : ranges) {
      var range = new Range(
          Long.parseLong(rangeString.split("-")[0]),
          Long.parseLong(rangeString.split("-")[1]));
      var invalidNumbers = range.toInvalidNumberList();
      result += invalidNumbers.stream().mapToLong(Long::longValue).sum();

    }
    return result;
    // return 1227775554L;
  }

}
