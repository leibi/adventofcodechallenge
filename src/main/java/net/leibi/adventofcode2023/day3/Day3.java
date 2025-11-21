package net.leibi.adventofcode2023.day3;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j2;

import java.io.LineNumberReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Log4j2
public class Day3 {

    public Integer getSumOfPartNummbers(String input) {
        return getParts(getMatrixEntriesFromInput(input))
                .stream()
                .mapToInt(m -> m.number)
                .sum();
    }

    List<MatrixEntry> getMatrixEntriesFromLine(String line, int lineNumber) {
        var map = new HashMap<String, List<Integer>>();
        return Arrays.stream(line.split("\\."))
                .flatMap(p -> splitToNumberAndSymbol(p).stream())
                .flatMap(part -> getMatrixEntryFromPart(part, line, lineNumber, map).stream())
                .toList();
    }

    boolean isPart(MatrixEntry matrixEntry, List<MatrixEntry> matrixEntries) {
        // an Entry is a Part if it's a number and if it is adjacent to a symbol
        if (matrixEntry.isSymbol) return false;

        // walk around the matrixEntry
        if (thereAreSymbolsAbove(matrixEntry, matrixEntries)) return true;
        if (thereAreSymbolsInTheSameRow(matrixEntry, matrixEntries)) return true;
        return thereAreSymbolsBelow(matrixEntry, matrixEntries);
    }

    List<MatrixEntry> getMatrixEntriesFromInput(String input) {
        LineNumberReader numberReader = new LineNumberReader(new StringReader(input));
        return numberReader.lines().flatMap(line -> getMatrixEntriesFromLine(line, numberReader.getLineNumber()).stream()).toList();
    }

    List<MatrixEntry> getParts(List<MatrixEntry> matrixEntries) {
        return matrixEntries.stream().filter(matrixEntry -> isPart(matrixEntry, matrixEntries)).toList();
    }

    private boolean thereAreSymbolsBelow(MatrixEntry matrixEntry, List<MatrixEntry> matrixEntries) {
        List<MatrixEntry> s;
        s = getSymbols(matrixEntry.xFrom - 1, matrixEntry.xTo + 1, matrixEntry.y + 1, matrixEntries);
        if (!s.isEmpty()) {
            //log.info("{} are in the row below {}", s, matrixEntry);
            return true;
        }
        return false;
    }

    private boolean thereAreSymbolsInTheSameRow(MatrixEntry matrixEntry, List<MatrixEntry> matrixEntries) {
        var s = getSymbols(matrixEntry.xFrom - 1, matrixEntry.xTo + 1, matrixEntry.y, matrixEntries);
        if (!s.isEmpty()) {
            //log.info("{} are in the same row as {}", s, matrixEntry);
            return true;
        }
        return false;
    }

    private boolean thereAreSymbolsAbove(MatrixEntry matrixEntry, List<MatrixEntry> matrixEntries) {
        var s = getSymbols(matrixEntry.xFrom - 1, matrixEntry.xTo + 1, matrixEntry.y - 1, matrixEntries);
        if (!s.isEmpty()) {
            //log.info("{} are in the row above {}", s, matrixEntry);
            return true;
        }
        return false;
    }

    private List<MatrixEntry> getSymbols(int xFrom, int xTo, int y, List<MatrixEntry> entries) {
        return entries.stream()
                .filter(e -> e.y == y)
                .filter(MatrixEntry::isSymbol)
                .filter(e -> e.xFrom >= xFrom && e.xTo <= xTo)
                .distinct()
                .toList();
    }

    private List<String> splitToNumberAndSymbol(String p) {
        return Arrays.stream(p.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")).toList();
    }

    private Optional<MatrixEntry> getMatrixEntryFromPart(String part, String line, int lineNumber, Map<String, List<Integer>> indexMap) {
        if (Strings.isNullOrEmpty(part)) return Optional.empty();

        int startIndex = -1;
        var indexList = indexMap.get(part);
        if (indexList != null)
            startIndex = indexList.getLast();
        startIndex += 1;
        var xFrom = line.indexOf(part, startIndex);
        indexMap.computeIfAbsent(part, k -> new ArrayList<>()).add(xFrom);
        try {
            var i = Integer.parseInt(part);
            return Optional.of(new MatrixEntry(xFrom, xFrom + part.length() - 1, lineNumber, i, false, part));
        } catch (NumberFormatException e) {
            //ignore
        }
        //not a number
        return Optional.of(new MatrixEntry(xFrom, xFrom + part.length() - 1, lineNumber, -1, true, part));
    }

    record MatrixEntry(int xFrom, int xTo, int y, int number, boolean isSymbol, String rawString) {
    }
}