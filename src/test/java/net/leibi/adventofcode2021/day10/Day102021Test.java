package net.leibi.adventofcode2021.day10;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Day102021Test {

  @Test
  void getCompletionString() {
    assertThat(Day102021.getCompletionString("[({(<(())[]>[[{[]{<()<>>")).isEqualTo("}}]])})]");
    assertThat(Day102021.getCompletionString("[(()[<>])]({[<{<<[]>>(")).isEqualTo(")}>]})");
    assertThat(Day102021.getCompletionString("(((({<>}<{<{<>}{[]{[]{}")).isEqualTo("}}>}>))))");
    assertThat(Day102021.getCompletionString("{<[[]]>}<{[{[{[]{()[[[]")).isEqualTo("]]}}]}]}>");
    assertThat(Day102021.getCompletionString("<{([{{}}[<[[[<>{}]]]>[]]")).isEqualTo("])}>");
  }

  @Test
  void getConflictingChar_SingeLines() {
    assertThat(Day102021.getFirstWrongChar(Input.SHORT_INPUT_LINE1)).isEqualTo("}");
    assertThat(Day102021.getFirstWrongChar(Input.SHORT_INPUT_LINE2)).isEqualTo(")");
    assertThat(Day102021.getFirstWrongChar(Input.SHORT_INPUT_LINE3)).isEqualTo("]");
    assertThat(Day102021.getFirstWrongChar(Input.SHORT_INPUT_LINE4)).isEqualTo(")");
    assertThat(Day102021.getFirstWrongChar(Input.SHORT_INPUT_LINE5)).isEqualTo(">");
  }

  @Test
  void solveFirstPartShort() {
    assertThat(Day102021.solveFirstPart(Input.SHORT_INPUT)).isEqualTo(26397);
  }

  @Test
  void solveFirstPartLong() {
    assertThat(Day102021.solveFirstPart(Input.LONG_INPUT)).isEqualTo(315693);
  }
}
