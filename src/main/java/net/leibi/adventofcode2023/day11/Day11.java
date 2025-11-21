package net.leibi.adventofcode2023.day11;

import lombok.extern.log4j.Log4j2;
import net.leibi.helpers.InputHelper;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class Day11 {

    public char[][] getExpandedUniverse(String small) {
        var expandedRows = small.lines().map(row -> getExpandedRow(row, 2)).collect(Collectors.joining());
        return getExpandedColumns(expandedRows, 2);
    }

    public Long getSumOfShortestPaths(String small) {
        log.info("Start");
        var expandedUniverse = getExpandedUniverse(small);
        log.info("got the universe");
        var galaxiesFromUniverse = getGalaxiesFromUniverse(expandedUniverse);
        log.info("got the galaxies ({})", galaxiesFromUniverse.size());
        var galaxyPairs = getGalaxyPairs(galaxiesFromUniverse);
        log.info("got the galaxypairs ({})", galaxyPairs.size());
        return galaxyPairs
                .stream()
                .mapToLong(GalaxyPair::getDistance)
                .sum();

    }

    public Long getSumOfShortestPathsPart2(String small, Long factor) {
        log.info("Start");
        var galaxiesFromUniverse = getGalaxiesFromUniverse(small);

        var matrix = InputHelper.getCharMatrixFromInput(small);
        var colList = getColLost(matrix);
        var rowList = getRowList(matrix);

        var galaxiesFromExpandedUniverse = getGalaxiesFromExpandedUniverse(galaxiesFromUniverse, factor, colList, rowList);

        log.info("got the galaxies ({})", galaxiesFromExpandedUniverse.size());
        var galaxyPairs = getGalaxyPairs(galaxiesFromExpandedUniverse);
        log.info("got the galaxypairs ({})", galaxyPairs.size());
        return galaxyPairs
                .stream()
                .mapToLong(GalaxyPair::getDistance)
                .sum();

    }

    List<Galaxy> getGalaxiesFromExpandedUniverse(List<Galaxy> galaxiesFromUniverse, Long factor, List<Long> rowIndexList, List<Long> columnIndexList) {
        return galaxiesFromUniverse.stream().map(galaxy -> getExpandedGalaxy(galaxy, factor, rowIndexList, columnIndexList)).toList();

    }

    Set<GalaxyPair> getGalaxyPairs(List<Galaxy> galaxies) {

        var galaxyPairs = new HashSet<GalaxyPair>();
        for (int i = 0; i < galaxies.size(); i++) {
            var g1 = galaxies.get(i);
            for (int i1 = 0; i1 < galaxies.size(); i1++) {
                var g2 = galaxies.get(i1);
                if (g1.equals(g2)) continue;
                var gp = new GalaxyPair(g1, g2);
                var reverseGp = new GalaxyPair(g2, g1);
                if (!galaxyPairs.contains(gp) && !galaxyPairs.contains(reverseGp)) {
                    galaxyPairs.add(gp);
                }
            }
        }
        return galaxyPairs;
    }

    List<Galaxy> getGalaxiesFromUniverse(char[][] universe) {

        var galaxies = new ArrayList<Galaxy>();
        for (int x = 0; x < universe.length; x++) {
            for (int y = 0; y < universe[x].length; y++) {
                if (universe[x][y] == '#') {
                    galaxies.add(new Galaxy(x, y));
                }
            }
        }
        return galaxies;

    }

    Galaxy getExpandedGalaxy(Galaxy galaxy, Long factor, List<Long> rowIndexList, List<Long> columnIndexList) {
        var newX = getNewCoordinate(galaxy.x, factor, rowIndexList);
        var newY = getNewCoordinate(galaxy.y, factor, columnIndexList);

        return new Galaxy(newX, newY);


    }

    private Long getNewCoordinate(long coordinate, Long factor, List<Long> indexList) {
        // how can we get from the old coordinate to the new coordinate
        // with the columnIndex we know how many expansions we have before this

        var count = indexList.stream().filter(idx -> idx < coordinate).count();

        var expansion = factor * count;

        return coordinate + expansion;

    }

    List<Galaxy> getGalaxiesFromUniverse(String small) {
        var matrix = InputHelper.getCharMatrixFromInput(small);
        return getGalaxiesFromUniverse(matrix);
    }

    List<Long> getColLost(char[][] matrix) {
        var result = new ArrayList<Long>();
        for (int i = 0; i < matrix.length; i++) {
            if (!String.valueOf(matrix[i]).contains("#")) {
                result.add((long) i);
            }
        }
        return result;
    }

    private char[][] getExpandedColumns(String expandedRows, int repeatCount) {
        var array = InputHelper.getCharMatrixFromInput(expandedRows);
        var width = array[0].length;

        var indexList = getRowList(array);

        int columnCount = repeatCount - 1;

        //build new aray
        var newWidth = width + (columnCount * indexList.size());
        var chars = new char[array.length][newWidth];
        for (int i = 0; i < array.length; i++) {
            char[] currentRow = array[i];
            var newRow = new char[newWidth];
            for (int j = 0, nj = 0; j < currentRow.length; nj++, j++) {
                newRow[nj] = currentRow[j];
                if (indexList.contains(j)) {
                    for (int i1 = 0; i1 < columnCount; i1++) {
                        newRow[nj + 1] = '.';
                        nj++;
                    }
                }
            }
            chars[i] = newRow;
        }
        return chars;
    }

    ArrayList<Long> getRowList(char[][] array) {
        int width = array[0].length;
        var indexList = new ArrayList<Long>();
        for (long i = 0; i < width; i++) {
            Character[] col = getColumn(array, i);
            if (Arrays.stream(col).noneMatch(c -> c.equals('#'))) {
                indexList.add(i);
            }
        }
        return indexList;
    }

    private Character[] getColumn(char[][] array, long index) {

        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i][(int) index];
        }
        return result;
    }

    private String getExpandedRow(String string, int repeatCount) {

        var newString = string + "\n";

        if (string.contains("#")) return newString;
        return newString.repeat(repeatCount);
    }

    record GalaxyPair(Galaxy galaxy1, Galaxy galaxy2) {
        public Long getDistance() {
            var xDist = Math.abs(galaxy1.x - galaxy2.x);
            var yDist = Math.abs(galaxy1.y - galaxy2.y);
            return xDist + yDist;
        }
    }

    record Galaxy(long x, long y) {
    }
}
