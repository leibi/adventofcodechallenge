package net.leibi.adventofcode2023.day14;

import lombok.extern.log4j.Log4j2;
import net.leibi.helpers.InputHelper;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;


@Log4j2
public class Day14 {
    public static String replaceCharUsingCharArray(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public String tilt(String input) {

        final var charMatrixFromInput = InputHelper.getCharMatrixFromInput(input);
        tiltNorth(charMatrixFromInput);

        return getStringFromCharArray(charMatrixFromInput);
    }

    public String cycle(String input, long times) {
        final var charMatrixFromInput = InputHelper.getCharMatrixFromInput(input);

        for (long i = 0; i < times; i++) {
            if (i % 1_000_000 == 0)
                log.info("Cycle # {}", i);
            cycleMirrors(charMatrixFromInput);
        }
        return getStringFromCharArray(charMatrixFromInput);
    }

    public long sumLoad(String smallTilted) {
        AtomicLong myCount = new AtomicLong(smallTilted.lines().count());

        return smallTilted.lines()
                .map(string -> string
                        .replace("#", "")
                        .replace(".", "")
                        .trim())
                .mapToLong(line -> {
                    var r = line.length() * myCount.get();
                    myCount.getAndDecrement();
                    return r;
                })
                .sum();
    }

    public long part1(String small) {
        return sumLoad(tilt(small));
    }

    public long part2(String small) {
        return sumLoad(cycle(small, 1_000_000_000));
    }

    static void tiltSouth(char[][] charMatrixFromInput) {
        var tmpRow = 0;
        for (int row = charMatrixFromInput.length - 1; row >= 0; row--) {
            char[] currentRow = charMatrixFromInput[row];
            for (int col = 0; col < currentRow.length; col++) {
                // move the current item as high as it gets
                if (currentRow[col] == 'O') {
                    charMatrixFromInput[row][col] = '.';
                    tmpRow = row + 1;
                    while (tmpRow < charMatrixFromInput.length && charMatrixFromInput[tmpRow][col] == '.') {
                        tmpRow++;
                    }
                    tmpRow -= 1;
                    charMatrixFromInput[tmpRow][col] = 'O';
                }
            }
        }
    }

    static void tiltEast(char[][] charMatrixFromInput) {
        for (int row = 0; row < charMatrixFromInput.length; row++) {
            char[] currentRow = charMatrixFromInput[row];
            charMatrixFromInput[row] = moveRight(currentRow);
            //moveRightOld(currentRow);
        }
    }

    static void tiltWest(char[][] charMatrixFromInput) {
        for (int row = 0; row < charMatrixFromInput.length; row++) {
            char[] currentRow = charMatrixFromInput[row];
            charMatrixFromInput[row] = moveLeft(currentRow);
            //moveLeftOld(currentRow);
        }
    }

    static void tiltNorth(char[][] charMatrixFromInput) {
        var tmpRow = 0;
        for (int row = 1; row < charMatrixFromInput.length; row++) {
            char[] currentRow = charMatrixFromInput[row];
            for (int col = 0; col < currentRow.length; col++) {
                // move the current item as high as it gets
                if (currentRow[col] == 'O') {
                    currentRow[col] = '.';
                    tmpRow = row - 1;
                    while (tmpRow >= 0 && charMatrixFromInput[tmpRow][col] == '.') {
                        tmpRow--;
                    }
                    if (tmpRow != row) {
                        tmpRow += 1;
                        charMatrixFromInput[tmpRow][col] = 'O';
                    }
                }
            }
        }
    }

    static String getStringFromCharArray(char[][] charMatrixFromInput) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charMatrixFromInput.length; i++) {
            sb.append(new String(charMatrixFromInput[i]));
            sb.append("\n");
        }
        return sb.toString();
    }

    static void moveLeftOld(char[] currentRow) {
        var tmpCol = 0;
        for (int col = 0; col < currentRow.length; col++) {
            // move the left
            if (currentRow[col] == 'O') {
                currentRow[col] = '.';
                tmpCol = col - 1;
                while (tmpCol >= 0 && currentRow[tmpCol] == '.') {
                    tmpCol--;
                }
                if (tmpCol != col) {
                    tmpCol += 1;
                    currentRow[tmpCol] = 'O';
                }
            }
        }
    }

    static char[] moveRight(char[] currentRow) {

        var s = new String(currentRow);
        final var boulders = getBoulders(currentRow, s);
        final var maxRight = currentRow.length - 1;
        for (int col = maxRight; col >= 0; col--) {
            if (s.charAt(col) == 'O') {
                // find next edge (# or start of string)
                int finalCol = col;
                var nextBoulder = boulders.stream().filter(index -> index < finalCol).max(Integer::compareTo);
                var switchPosition = nextBoulder.map(integer -> integer + 1).orElseGet(() -> maxRight);
                if (switchPosition.equals(maxRight)) {
                    //check for Mirrors in between
                    final var nextMirror = s.indexOf('O', col + 1);
                    if (nextMirror != -1)
                        switchPosition = nextMirror - 1;
                }
                s = replaceCharUsingCharArray(s, '.', col);
                s = replaceCharUsingCharArray(s, 'O', switchPosition);
            }
        }
        return s.toCharArray();
    }

    static void moveRightOld(char[] currentRow) {

        var tmpCol = 0;
        for (int col = currentRow.length - 1; col >= 0; col--) {
            // move the left
            if (currentRow[col] == 'O') {
                currentRow[col] = '.';
                tmpCol = col + 1;
                while (tmpCol < currentRow.length && currentRow[tmpCol] == '.') {
                    tmpCol++;
                }
                if (tmpCol != col) {
                    tmpCol -= 1;
                    currentRow[tmpCol] = 'O';
                }
            }
        }
    }

    private static char[] moveLeft(char[] currentRow) {
        var s = new String(currentRow);
        final var boulders = getBoulders(currentRow, s);
        final var maxRight = currentRow.length - 1;
        for (int col = 0; col <= maxRight; col++) {
            if (s.charAt(col) == 'O') {
                final var leftToCurrent = s.charAt(col - 1);
                if (leftToCurrent == '#' || leftToCurrent == '0') {
                    continue;
                }
                // find next edge (# or start of string)
                int finalCol = col;
                var nextBoulder = boulders.stream().filter(index -> index < finalCol).min(Integer::compareTo);
                var switchPosition = nextBoulder.map(integer -> integer + 1).orElse(0);
                if (switchPosition.equals(0)) {
                    //check for Mirrors in between
                    final var nextMirror = s.indexOf('O');
                    if (nextMirror != col && nextMirror != -1)
                        switchPosition = nextMirror + 1;
                }
                if (switchPosition != col) {
                    s = replaceCharUsingCharArray(s, '.', col);
                    s = replaceCharUsingCharArray(s, 'O', switchPosition);
                }
            }
        }
        return s.toCharArray();
    }

    private static HashSet<Integer> getBoulders(char[] currentRow, String s) {
        var boulders = new HashSet<Integer>(currentRow.length);
        var lastIndex = 0;
        var currentIndex = 0;
        while (lastIndex >= 0) {
            currentIndex = s.indexOf('#', lastIndex + 1);
            if (currentIndex != -1) {
                boulders.add(currentIndex);
            }
            lastIndex = currentIndex;
        }
        return boulders;
    }

/*
    static void move(char[][] array, Direction direction){
        int x = 0;
        int y = 0;

        int horizontal = switch (direction){
            case EAST -> 1;
            case WEST -> -1;
            case NORTH, SOUTH -> 0;
        };

        int vertical = switch (direction){
            case NORTH -> 1;
            case SOUTH -> -1;
            case EAST, WEST -> 0;
        };

    }

 */

    private void cycleMirrors(char[][] charMatrixFromInput) {
        tiltNorth(charMatrixFromInput);
        tiltWest(charMatrixFromInput);
        tiltSouth(charMatrixFromInput);
        tiltEast(charMatrixFromInput);
    }

    enum Direction {
        NORTH,
        WEST,
        EAST,
        SOUTH
    }
}
