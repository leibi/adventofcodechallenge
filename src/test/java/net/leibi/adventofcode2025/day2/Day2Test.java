package net.leibi.adventofcode2025.day2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day2Test {

  @Test
  void day2Part1Small() {
    assertThat(Day2.day2Part1(Input.SMALL)).isEqualTo(1227775554L);
  }

  @Test
  void day2Part1Big() {
    assertThat(Day2.day2Part1(Input.BIG)).isEqualTo(35367539282L);
  }
}
