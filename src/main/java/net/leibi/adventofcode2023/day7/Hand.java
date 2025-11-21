package net.leibi.adventofcode2023.day7;


import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

import static net.leibi.adventofcode2023.day7.Day7.isCardHigher;
import static net.leibi.adventofcode2023.day7.Day7.sortCards;

public record Hand(Cards cards, Long Bid) {

    public int isBetter(Hand o2) {
        var compare = getCompare(o2);
        if (0 != compare) {
            return compare;
        }
        // determine highest card of the winning bunch
        return compareWinningHandIfBothHaveSameWinEnum(o2);


    }

    private int compareWinningHandIfBothHaveSameWinEnum(Hand o2) {
        var sorted = new ArrayList<>(sortCards(cards.getWinningCards()));
        var otherSorted = new ArrayList<>(sortCards(o2.cards.getWinningCards()));
        assert (sorted.size() == otherSorted.size());
        while (sorted.getFirst().equals(otherSorted.getFirst())) {
            sorted.removeFirst();
            otherSorted.removeFirst();
            if (sorted.isEmpty()) {
                return compareHighestNonWinningCard(o2);
            }
        }

        return isCardHigher(sorted.getFirst(), otherSorted.getFirst());
    }

    private int compareHighestNonWinningCard(Hand o2) {
        // the next highest non-winning card counts
        var c1 = new ArrayList<>(cards);
        c1.removeAll(cards.getWinningCards());


        var c2 = new ArrayList<>(o2.cards);
        c2.removeAll(o2.cards.getWinningCards());
        if (c1.isEmpty()) {
            return compareFullHouseScenario(o2);

        }
        return isCardHigher(sortCards(c1).getFirst(), sortCards(c2).getFirst());
    }

    private int compareFullHouseScenario(Hand o2) {
        /// full house.. which one was the triple?
        var card1 = getHighestCard(this);
        var card2 = getHighestCard(o2);
        return isCardHigher(card1, card2);
    }

    private Character getHighestCard(@Nonnull Hand hand) {
        var collectionMap = hand.cards().stream().collect(Collectors.groupingBy(Character::charValue, Collectors.counting()));
        var list1 = collectionMap.entrySet().stream()
                .filter(e -> cards.getWinningCards().contains(e.getKey()))
                .sorted(Map.Entry.comparingByValue())
                .toList();
        return list1.getLast().getKey();

    }

    private int getCompare(Hand o2) {
        var myRank = cards.getWin().ordinal();
        var otherRank = o2.cards.getWin().ordinal();
        return Integer.compare(otherRank, myRank);
    }


}