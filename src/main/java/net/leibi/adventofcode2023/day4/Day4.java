package net.leibi.adventofcode2023.day4;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
public class Day4 {
    public Integer sumPointsOfPile(String input) {

        return input.lines().map(this::getCardFromLine).mapToInt(this::getPointsFromCard).sum();

    }

    public Integer sumPointsOfPileRecursive(String input) {
        var originalCards = input.lines().map(this::getCardFromLine).toList();
        var copies = originalCards.stream().flatMap(card -> getCopiesFromCard(card, originalCards)).toList();

        // log.info("We got the following original cards: {}", originalCards.stream().map(Card::id).toList());
        // log.info("We got the following copies: {}", copies.stream().map(Card::id).toList());


        //var map = Stream.concat(originalCards.stream(), copies.stream()).collect(groupingBy(Card::id, Collectors.counting()));
        //log.info("{}", map);
        return (int) Stream.concat(originalCards.stream(), copies.stream()).count();
    }

    private Stream<Card> getCopiesFromCard(Card card, List<Card> originalCards) {
        // log.info("Getting copies from card {} (copy: {})", card.id(), card.copyCount());
        var numberOfWins = getNumberOfWinningNumbers(card);
        // log.info("Card {} has {} wins", card.id(), numberOfWins);
        if (numberOfWins == 0) return Stream.empty();
        // from card x we get card x+1...x+numberOfWins
        var from = card.id() + 1;
        var to = card.id() + numberOfWins;

        //log.info("Getting cards from {} to {} from card {} ({})", from, to, card.id(), card.copyCount());
        var copies = getCopies(originalCards, from, (int) to);
        var b = copies.stream().flatMap(c -> getCopiesFromCard(c, originalCards));
        //log.info("We got cards {} ", copies.stream().map(Card::id).toList());;
        return Stream.concat(copies.stream(), b);


    }

    private List<Card> getCopies(List<Card> originalCards, int from, int to) {
        return IntStream.range(from, to + 1)
                .mapToObj(newId -> getCardsWithId(newId, originalCards))
                .flatMap(cards -> cards.stream().map(Card::getCopy))
                .toList();
    }

    private List<Card> getCardsWithId(int newId, List<Card> originalCards) {
        return originalCards.stream().filter(card -> card.id() == newId).toList();
    }

    private Integer getPointsFromCard(Card card) {

        var numberOfWinningNumbers = getNumberOfWinningNumbers(card);
        int result;
        if (numberOfWinningNumbers == 0) {
            result = 0;
        } else if (numberOfWinningNumbers == 1) {
            result = 1;
        } else {
            result = ((Double) Math.pow(2, numberOfWinningNumbers - 1)).intValue();
        }
        //log.info("Getting Points from card: {}: {}", card.id(), result);
        return result;
    }

    private static long getNumberOfWinningNumbers(Card card) {
        return card.numbers().stream().filter(number -> card.winningNumbers().contains(number)).count();
    }

    @NonNull
    private Card getCardFromLine(String line) {
        //Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
        var id = Arrays.stream(line.split(":")[0].split(" ")).filter(s -> !s.isEmpty()).toList().getLast();
        var numbersSplit = line.split(":")[1].split("\\|");
        List<Integer> numbers = Arrays.stream(numbersSplit[0].trim().split(" ")).filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();
        List<Integer> winningNumbers = Arrays.stream(numbersSplit[1].trim().split(" ")).filter(s -> !s.isEmpty()).map(Integer::parseInt).toList();

        return new Card(Integer.parseInt(id), numbers, winningNumbers);
    }
}
