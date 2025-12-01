package net.leibi.adventofcode2025.day1;

import java.util.concurrent.atomic.AtomicInteger;

public class Day1 {
    public static int day1Part1(String input) {

        var currentPosition = 50;
        var zeros = 0;

        for (var line : input.split("\\r?\\n")) {
            String direction = line.substring(0, 1);
            int distance = Integer.parseInt(line.substring(1));

            if (direction.equals("L")) {
                currentPosition = (currentPosition - distance) % 100;
            } else {
                currentPosition = (currentPosition + distance) % 100;
            }

            if (currentPosition == 0) {
                zeros++;
            }
        }
        return zeros;
    }
}
