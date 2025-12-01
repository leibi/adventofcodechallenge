package net.leibi.adventofcode2025.day1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day1Test {

  @Test
  void day1Part1Small() {
    assertThat(Day1.day1Part1(Input.SMALL)).isEqualTo(3);
  }

    @Test
    void day1Part1Big() {
        assertThat(Day1.day1Part1(Input.BIG)).isEqualTo(1118);
    }

}
