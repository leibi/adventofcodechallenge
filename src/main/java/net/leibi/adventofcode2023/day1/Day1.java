package net.leibi.adventofcode2023.day1;

import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;

@Log4j2
public class Day1 {

    Map<Integer, String> numbersMap = Map.of(1, "one", 2, "two", 3, "three", 4, "four", 5, "five", 6, "six", 7, "seven", 8, "eight", 9, "nine");

    public Integer getSumOfCalibrationValues(String small) {
        return small
                .lines()
                .map(this::getCalibrationValue)
                .flatMapToInt(IntStream::of)
                .sum();
    }

    public Integer getSumOfCalibrationValuesSpelledOut(String input) {
        return input
                .lines()
                .map(this::extractCalibrationValue)
                .flatMapToInt(IntStream::of)
                .sum();
    }

    Integer getCalibrationValueSpelledOut(String string) {
        //two1nine
        var indices = numbersMap.entrySet().stream()
                .map(entry -> new Tupel(string.indexOf(entry.getValue()), entry.getKey())
                )
                .filter(tupel -> tupel.index != -1)
                .collect(groupingBy(Tupel::index));

        var m = IntStream.range(0, string.length())
                .mapToObj(i -> new Tupel(i, (string.charAt(i)) - 48))
                .filter(i -> i.value > 0 && i.value <= 9)
                .collect(groupingBy(Tupel::index));

        //log.info("\n{}\n{} \n{}",string,indices, m);
        indices.putAll(m);
        List<Integer> list = indices.keySet().stream().sorted().toList();
        //log.info("{}", list);
        var first = indices.get(list.getFirst()).getFirst().value();
        var last = indices.get(list.getLast()).getFirst().value();

        int result = first * 10 + last;
        log.info("{} -> {}, {} -> {}", string, first, last, result);
        return result;
    }

    int extractCalibrationValue(String line) {
        var lineDigits = numbersMap
                .entrySet().stream()
                .flatMap(digit -> firstAndLastOccurrence(line, digit.getKey(), digit.getValue()))
                .sorted(comparing(Tupel::index))
                .toList();
        return lineDigits.getFirst().value() * 10 + lineDigits.getLast().value();
    }

    Stream<Tupel> firstAndLastOccurrence(String line, int digit, String digitWord) {
        return Stream.of(
                        new Tupel(line.indexOf(digitWord), digit),
                        new Tupel(line.lastIndexOf(digitWord), digit),
                        new Tupel(line.indexOf("" + digit), digit),
                        new Tupel(line.lastIndexOf("" + digit), digit))
                .filter(occurrence -> occurrence.index >= 0);
    }

    private Integer getCalibrationValue(String line) {
        //1abc2
        var numbers = line.chars()
                .filter(i -> i > 48 && i <= 57) // numbers
                .map(i -> i - 48)
                .toArray();
        log.info("{} -> {}: {}", line, numbers, numbers.length);
        return numbers[0] * 10 + numbers[numbers.length - 1];
    }

    record Tupel(Integer index, Integer value) {
    }

}
