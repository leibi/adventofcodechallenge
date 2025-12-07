package net.leibi.adventofcode2025.day1;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.helpers.InputHelper;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@Log4j2
public class Day1 {

  record Order(Character direction, int distance) {

    Order(String line) {
      this(line.charAt(0), Integer.parseInt(line.substring(1)));
    }
  }

  public static int day1Part1(String input) {

    var currentPosition = 50;
    var zeros = 0;

    for (var line : input.split("\\r?\\n")) {
      String direction = line.substring(0, 1);
      int distance = Integer.parseInt(line.substring(1));

      if (direction.equals("L")) {
        currentPosition = (currentPosition - distance) % 100;
      } else {
        currentPosition = (currentPosition + distance) % 100;
      }

      if (currentPosition == 0) {
        zeros++;
      }
    }
    return zeros;
  }

  public static int day1Part2(String input) {

    var inputLines = InputHelper.getRowListFromInput(input);
    var orderList = inputLines.stream().map(Order::new).toList();

    var currentPosition = 50;
    var zeros = 0;

    for (var order : orderList) {

      var nextPosition = order.direction == 'L'
          ? (currentPosition - order.distance)
          : (currentPosition + order.distance);

      log.info("{}: CurrentPos: {}, NextPos: {}", order, currentPosition, nextPosition);

      // how many times we pass 0
      if (order.direction == 'L') {
        if (nextPosition <= 0 && currentPosition != 0) {
          zeros += Math.abs(nextPosition) / 100 + 1;
        }
      } else {
        if (nextPosition >= 100) {
          zeros += Math.abs(nextPosition) / 100;
        }
      }
      currentPosition = (100 + (nextPosition % 100)) % 100;
      log.info("{}: New CurrentPos: {}", order, currentPosition);
    }

    return zeros;
  }
}
