package net.leibi.adventofcode2023.day15;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
public class Day15 {

    private static final Map<Long, List<LabeledEntry>> boxes = new HashMap<>();


    public Long getHash(String string) {
        long currentValue = 0;
        for (char c : string.toCharArray()) {
            currentValue = getNewCurrentValue(c, currentValue);
        }
        return currentValue;
    }

    public long day1(String s) {
        final var split = s.split(",");
        log.info("Input size: {}", split.length);
        boxes.clear();
        return Arrays.stream(split).mapToLong(this::getHash).sum();
    }

    public long day2(String input) {
        boxes.clear();
        final var split = input.split(",");

        var labeledEntries = Arrays.stream(split).map(this::getLabeledEntry).toList();

        for (LabeledEntry labeledEntry : labeledEntries) {
            var box = getHash(labeledEntry.label);
            switch (labeledEntry.operator) {
                case "-" -> removeLabel(labeledEntry.label, box);
                case "=" -> addLabel(labeledEntry, box);
                default -> throw new IllegalStateException("Unexpected operator: " + labeledEntry.operator);
            }
        }

        net.leibi.helpers.MapHelpers.printMap(boxes);

        return boxes.entrySet().stream().mapToLong(this::getBoxEntryValue).sum();
    }

    LabeledEntry getLabeledEntry(String input) {

        if (input.endsWith("-")) {
            return new LabeledEntry(input.split("-")[0], "-", null);
        }
        final var split = input.split("=");
        return new LabeledEntry(split[0], "=", Long.parseLong(split[1]));

    }

    private Long getBoxEntryValue(Map.Entry<Long, List<LabeledEntry>> entry) {
        var boxNumber = entry.getKey() + 1;
        var result = 0L;
        for (int slot = 1; slot <= entry.getValue().size(); slot++) {
            final var focalLength = entry.getValue().get(slot - 1).value;
            final var intermediateResult = boxNumber * slot * focalLength;
            result += intermediateResult;
        }
        log.info("Result for {} is {}", boxNumber, result);
        return result;
    }

    private void addLabel(LabeledEntry labeledEntry, Long box) {
        final var index = getIndex(labeledEntry.label, boxes.get(box));
        if (index == -1) {
            boxes.computeIfAbsent(box, k -> new ArrayList<>()).add(labeledEntry);
        }
        else
        {
            boxes.get(box).remove(index);
            boxes.get(box).add(index, labeledEntry);
        }
    }

    private void removeLabel(String label, Long box) {
        if (boxes.containsKey(box)) {
            var boxEntry = boxes.get(box);
            final var index = getIndex(label, boxEntry);
            if (index >= 0) {
                boxEntry.remove(index);
            }
        }
    }

    private static int getIndex(String label, List<LabeledEntry> boxEntry) {
        var index = -1;
        if (boxEntry != null) {
            for (int i = 0; i < boxEntry.size(); i++) {
                if (boxEntry.get(i).label.equals(label)) {
                    index = i;
                }
            }
        }
        return index;
    }

    private long getNewCurrentValue(char c, long currentValue) {
        currentValue += c;
        currentValue *= 17;
        return currentValue % 256;
    }


    public record LabeledEntry(String label, String operator, Long value) {
        @Override
        public String toString() {
            if (operator.equals("=")) {
                return label + " " + value;
            } else
                return label + operator;
        }
    }


}
