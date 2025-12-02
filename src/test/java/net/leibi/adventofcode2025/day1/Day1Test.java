package net.leibi.adventofcode2025.day1;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Day1Test {

  @Test
  void day1Part1Small() {
    assertThat(Day1.day1Part1(Input.SMALL)).isEqualTo(3);
  }

  @Test
  void day1Part1Big() {
    assertThat(Day1.day1Part1(Input.BIG)).isEqualTo(1118);
  }

  @Test
  void day1Part2Small() {
    assertThat(Day1.day1Part2(Input.SMALL)).isEqualTo(6);
  }

  @Test
  void day1Part2Big() {
    assertThat(Day1.day1Part2(Input.BIG)).isEqualTo(6);
  }

  @ParameterizedTest
  @CsvSource({
    "R100,1",
    "L100,1",
    "R150,2",
    "L150,2",
    "R99,1",
    "L99,1",
    "R1,0",
    "L1,0",
    "R49,0",
    "L49,0",
    "R50,1",
    "L50,1",
    "R200,2",
    "L200,2",
    "R250,3",
    "L250,3"
  })
  void testMultiRot(String input, int expectedZeros) {
    assertThat(Day1.day1Part2(input))
        // .withFailMessage(() -> "Failed for input: %s".formatted(input))
        .isEqualTo(expectedZeros);
  }
}
