package net.leibi.adventofcode2024.day4;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Day4Test {


    @Test
    void testDay4Small() {
        assertThat(Day4.solveDay4Part1(INPUT.SMALL)).isEqualTo(18);
    }

    @Test
    void testDay4SmallClean() {
        assertThat(Day4.solveDay4Part1(INPUT.SMALL_CLEAN)).isEqualTo(Day4.solveDay4Part1(INPUT.SMALL));
    }

    @Test
    void testDay4Part1Horizontal() {
        assertThat(Day4.countHorizontal(INPUT.CLEAN)).isEqualTo(2);
    }

    @Test
    void testDay4Part1HorizontalSMALL() {
        assertThat(Day4.countHorizontal(INPUT.SMALL)).isEqualTo(5);
    }


    @Test
    void testDay4Part1Vertical() {
        assertThat(Day4.countVertical(INPUT.CLEAN)).isEqualTo(1);
    }

    @Test
    void testDay4Part1VerticalSMALL() {
        assertThat(Day4.countVertical(INPUT.SMALL)).isEqualTo(3);
    }
    @Test
    void testDay4Part1Diag() {
        assertThat(Day4.countDiag(INPUT.CLEAN)).isEqualTo(1);
    }

    @Test
    void testDay4Part1DiagSmall() {
        assertThat(Day4.countDiag(INPUT.SMALL)).isEqualTo(10);
    }
}
