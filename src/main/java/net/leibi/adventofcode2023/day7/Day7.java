package net.leibi.adventofcode2023.day7;

import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Log4j2
public class Day7 {
    static List<Character> cardOrder = List.of('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2');

    public Long getTotalWinnings(String input) {
        var sortedHands = getSortedHands(input);
        log.info("sorted hands: {}", sortedHands);
        return IntStream.range(0, sortedHands.size())
                .mapToLong(rank -> sortedHands.get(rank).Bid() * (rank + 1))
                .sum();
    }

    public List<Hand> getHands(String input) {
        return input.lines().map(this::getHand).toList();
    }

    static int isCardHigher(Character c1, Character c2) {
        return Integer.compare(cardOrder.indexOf(c2), cardOrder.indexOf(c1));
    }

    List<Hand> getSortedHands(String input) {
        var hands = getHands(input);
        log.info("hands: {}", hands);
        var sortedHands = new ArrayList<>(hands);
        sortedHands.sort(Hand::isBetter);
        return sortedHands;
    }

    static List<Character> sortCards(List<Character> characters) {
        return characters.stream().sorted((c1, c2) -> -1 * Day7.isCardHigher(c1, c2)).toList();
    }


    private Hand getHand(String line) {
        var split = line.split(" ");
        return new Hand(new Cards(split[0]), Long.parseLong(split[1]));
    }


}
