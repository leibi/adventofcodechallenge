package net.leibi.helpers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class InputHelper {

    public static List<Integer> getIntegerListFromInput(final String input) {
        return Arrays.stream(input.split("\\r?\\n")).map(Integer::valueOf).toList();
    }

    public static List<String> getRowListFromInput(final String input) {
        return input.lines().toList();
    }

    public static int getIntegerFromString(String s) {
        int[] intArray = getIntArrayFromString(s);
        return getIntegerFromIntArray(intArray);
    }

    public static int[] getIntArrayFromString(String s, String delimiter) {
        return Arrays.stream(s.split(delimiter))
                .filter(string -> !string.isBlank())
                .filter(string -> !string.isEmpty())
                .map(Integer::valueOf)
                .mapToInt(i -> i)
                .toArray();
    }

    public static int[] getIntArrayFromString(String s) {
        return Arrays.stream(s.split(""))
                .filter(string -> !string.isBlank())
                .filter(string -> !string.isEmpty())
                .map(Integer::valueOf)
                .mapToInt(i -> i)
                .toArray();
    }

    public static int getIntegerFromIntArray(int[] intArray) {
        int result = 0;
        int multiplier = 1;
        for (int i = intArray.length - 1; i >= 0; --i) {
            result += (multiplier * intArray[i]);
            multiplier *= 2;
        }
        return result;
    }


    public static int[][] getIntMatrixFromInput(String input) {
        List<String> rowList = input.lines().toList();
        int[][] result = new int[rowList.size()][rowList.get(0).length()];

        int currentRow = 0;
        for (String s : rowList) {
            int[] row = InputHelper.getIntArrayFromString(s);
            result[currentRow++] = row;
        }
        return result;
    }

    public static char[][] getCharMatrixFromInput(String input) {
        List<String> rowList = input.lines().toList();
        char[][] result = new char[rowList.size()][rowList.getFirst().length()];

        int currentRow = 0;
        for (String s : rowList) {
            char[] row = s.toCharArray();
            result[currentRow++] = row;
        }
        return result;
    }

    public static void printCharArray(char[][] charMatrix) {
        for (char[] row : charMatrix) {
            log.info(Arrays.toString(row));
        }
    }

    public static List<String> getColumnListFromInput(String clean) {
        List<String> rowList = getRowListFromInput(clean);
        int numberOfColumns = rowList.getFirst().length();

        String[] columns = new String[numberOfColumns];
        Arrays.fill(columns, "");

        for (String row : rowList) {
            for (int colIndex = 0; colIndex < numberOfColumns; colIndex++) {
                columns[colIndex] += row.charAt(colIndex);
            }
        }
        return Arrays.stream(columns).toList();
    }

    public static List<String> getDiagonalListFromInput(String clean) {
     // assume the string is a matrix n times m
        var rowList =getRowListFromInput(clean);
        int numberOfRows = rowList.size();
        int numberOfColumns = rowList.getFirst().length();

        // there are (n + m -1) diagonals
        String[] diagonals = new String[2*(numberOfRows + numberOfColumns - 1)];
        Arrays.fill(diagonals, "");


        //get all diagonals from the columns starting from the first row
        for (int col = 0; col < numberOfColumns; col++) {
            int currentCol = col;
            StringBuilder diagonal = new StringBuilder();
            for (int row = 0; row < numberOfRows && currentCol < numberOfColumns; row++) {
                diagonal.append(rowList.get(row).charAt(currentCol));
                currentCol++;
            }
            diagonals[col] = diagonal.toString();
        }

        //get all the diagonals from right to left starting from the first column
        for (int col = 1; col < numberOfColumns; col++) {
            int currentCol = col;
            StringBuilder diagonal = new StringBuilder();
            for (int row = 0; row < numberOfRows && currentCol >= 0; row++) {
                diagonal.append(rowList.get(row).charAt(currentCol));
                currentCol--;
            }
            diagonals[numberOfColumns -1 + col] = diagonal.toString();
        }


        // get the diagonals from left to right starting from the second row
        for (int row = 1; row < numberOfRows; row++) {
            int currentRow = row;
            StringBuilder diagonal = new StringBuilder();
            for (int col = numberOfColumns -1; col >=0 && currentRow < numberOfRows; col--) {
                diagonal.append(rowList.get(currentRow).charAt(col));
                currentRow++;
            }
            diagonals[ (numberOfRows -1) + (numberOfColumns -1) + row -1] = diagonal.toString();
        }

        //get all diagonals from the rows starting from the second row
        for (int row = 1; row < numberOfRows; row++) {
            int currentRow = row;
            StringBuilder diagonal = new StringBuilder();
            for (int col = 0; col < numberOfColumns && currentRow < numberOfRows; col++) {
                diagonal.append(rowList.get(currentRow).charAt(col));
                currentRow++;
            }
            diagonals[(numberOfRows -1) + (numberOfColumns -1) + (numberOfRows -1) + row -1] = diagonal.toString();
        }


        return Arrays.stream(diagonals).toList();
    }
}
