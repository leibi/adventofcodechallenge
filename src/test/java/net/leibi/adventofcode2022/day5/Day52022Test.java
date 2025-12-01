package net.leibi.adventofcode2022.day5;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import net.leibi.adventofcode2022.day5.Day52022.Move;
import net.leibi.adventofcode2022.day5.Day52022.Stacks;
import org.junit.jupiter.api.Test;

class Day52022Test {


  @Test
  void getStacksFromInput() {
    assertThat(Day52022.getStacksFromInput(Input.small).size()).isEqualTo(3);
  }

  @Test
  void getTopCratesSmall(){
    assertThat(Day52022.getTopCratesAfterMove(Input.small)).isEqualTo("CMZ");
  }

  @Test
  void getTopCrates9001Small(){
    assertThat(Day52022.getTopCratesAfterMove9001(Input.small)).isEqualTo("MCD");
  }

  @Test
  void getTopCratesLarge(){
    assertThat(Day52022.getTopCratesAfterMove(Input.large)).isEqualTo("QPJPLMNNR");
  }

  @Test
  void getTopCratesLarge9001(){
    assertThat(Day52022.getTopCratesAfterMove9001(Input.large)).isEqualTo("BQDNWJPVJ");
  }

  @Test
  void getMovesFromInput() {
    assertThat(Day52022.getMovesFromInput(Input.small)).hasSize(4);
  }

  @Test
  void getStacksFromSubInput() {
      @SuppressWarnings("MisleadingEscapedSpace")
    String s = """
                [A] \s
            [D] [Y] \s
        [N] [C] [X] \s
        [Z] [M] [P] \s
         0   1   2
        """;
    assertThat(Day52022.getStacksFromSubInput(s).size()).isEqualTo(3);
    assertThat(Day52022.getStacksFromSubInput(s).getTopCrate(0)).hasValue('N');
    assertThat(Day52022.getStacksFromSubInput(s).getTopCrate(1)).hasValue('D');
    assertThat(Day52022.getStacksFromSubInput(s).getTopCrate(2)).hasValue('A');
  }

  @Test
  void moveCrate(){
      @SuppressWarnings("MisleadingEscapedSpace")
    String s = """
                [A] \s
            [D] [Y] \s
        [N] [C] [X] \s
        [Z] [M] [P] \s
         0   1   2
        """;

    Stacks stacksFromInput = Day52022.getStacksFromSubInput(s);
    stacksFromInput.applyMove(new Move(1,1,2));

    assertThat(stacksFromInput).isNotNull();
    assertThat(stacksFromInput.stacks().size()).isEqualTo(3);
    assertThat(stacksFromInput.stacks().get(0).getTopCrate()).hasValue('Z');
    assertThat(stacksFromInput.stacks().get(1).getTopCrate()).hasValue('N');
    assertThat(stacksFromInput.stacks().get(2).getTopCrate()).hasValue('A');

    stacksFromInput.applyMove(new Move(1,1,4));

    assertThat(stacksFromInput).isNotNull();
    assertThat(stacksFromInput.stacks().size()).isEqualTo(4);
    assertThat(stacksFromInput.stacks().get(0).getTopCrate()).isEmpty();
    assertThat(stacksFromInput.stacks().get(1).getTopCrate()).hasValue('N');
    assertThat(stacksFromInput.stacks().get(2).getTopCrate()).hasValue('A');
    assertThat(stacksFromInput.stacks().get(3).getTopCrate()).hasValue('Z');

  }

  @Test
  void moveCrate9001(){
      @SuppressWarnings("MisleadingEscapedSpace")
    String s = """
                [A] \s
            [D] [Y] \s
        [N] [C] [X] \s
        [Z] [M] [P] \s
         0   1   2
        """;

    Stacks stacksFromInput = Day52022.getStacksFromSubInput(s);
    stacksFromInput.applyMove9001(new Move(1,1,2));

    assertThat(stacksFromInput).isNotNull();
    assertThat(stacksFromInput.stacks().size()).isEqualTo(3);
    assertThat(stacksFromInput.stacks().get(0).getTopCrate()).hasValue('Z');
    assertThat(stacksFromInput.stacks().get(1).getTopCrate()).hasValue('N');
    assertThat(stacksFromInput.stacks().get(2).getTopCrate()).hasValue('A');

    stacksFromInput.applyMove9001(new Move(2,2,3));

    assertThat(stacksFromInput).isNotNull();
    assertThat(stacksFromInput.stacks().size()).isEqualTo(3);
    assertThat(stacksFromInput.stacks().get(0).getTopCrate()).hasValue('Z');
    assertThat(stacksFromInput.stacks().get(1).getTopCrate()).hasValue('C');
    assertThat(stacksFromInput.stacks().get(2).getTopCrate()).hasValue('N');

  }



}