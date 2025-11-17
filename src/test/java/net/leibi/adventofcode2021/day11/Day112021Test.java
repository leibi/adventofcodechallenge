package net.leibi.adventofcode2021.day11;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day112021Test {

  @Test
  void getStepWhereAllFlashes_SHORT() {

    Day112021 day112021 = new Day112021(Input.SHORT_INPUT);
    assertThat(day112021.getStepWhereAllFlashes()).isEqualTo(195);
  }

  @Test
  void getStepWhereAllFlashes_PUZZLE() {

    Day112021 day112021 = new Day112021(Input.PUZZLE_INPUT);
    assertThat(day112021.getStepWhereAllFlashes()).isEqualTo(519);
  }

  @Test
  void getFlashesForSteps_Tiny() {

    Day112021 day112021 = new Day112021(Input.TINY_INPUT);
    assertThat(day112021.getFlashesForSteps(2)).isEqualTo(9);
  }

  @Test
  void getFlashesForSteps_Short_10() {

    Day112021 day112021 = new Day112021(Input.SHORT_INPUT);
    assertThat(day112021.getFlashesForSteps(10)).isEqualTo(204);
  }

  @Test
  void getFlashesForSteps_Short_100() {
    Day112021 day112021 = new Day112021(Input.SHORT_INPUT);
    assertThat(day112021.getFlashesForSteps(100)).isEqualTo(1656);
  }

  @Test
  void getFlashesForSteps_Long_100() {
    Day112021 day112021 = new Day112021(Input.PUZZLE_INPUT);
    assertThat(day112021.getFlashesForSteps(100)).isEqualTo(1679);
  }

  /*
   * @Test
   * void getNextStepTiny() {
   * 
   * Day112021 day112021 = new Day112021(Input.TINY_INPUT);
   * assertThat(day112021.getFlashes()).isZero();
   * day112021.getNextStep();
   * assertThat(day112021.getFlashes()).isEqualTo(9);
   * day112021.getNextStep();
   * assertThat(day112021.getFlashes()).isEqualTo(9);
   * }
   */
}
