package net.leibi.adventofcode2024.day3;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day3Test {

  private final Day3 day3 = new Day3();

  @Test
  void testPart1() {
    assertThat(day3.part1(Input.SMALL)).isEqualTo(161);
  }

  @Test
  @Disabled("Does not work yet")
  void testPart2() {
    assertThat(day3.part2(Input.SMALL2)).isEqualTo(48);
  }

  @Test
  void testPart1Big() {
    assertThat(day3.part1(Input.BIG)).isEqualTo(173731097L);
  }

  @Test
  void getValidMulPart1() {
    final String regex = "mul\\((\\d+),(\\d+)\\)";
    assertThat(day3.getValidMul(Input.SMALL, regex))
        .hasSize(4)
        .containsExactly(new Mul(2, 4), new Mul(5, 5), new Mul(11, 8), new Mul(8, 5));
  }
}
