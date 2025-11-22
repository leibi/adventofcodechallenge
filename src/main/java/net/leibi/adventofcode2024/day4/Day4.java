package net.leibi.adventofcode2024.day4;

import net.leibi.helpers.InputHelper;

import java.util.List;

public class Day4 {


    public static int solveDay4Part1(String input) {
        return countHorizontal(input) + countVertical(input) + countDiag(input);
    }

    public static int countHorizontal(String input) {
        return getSubStringCounts(InputHelper.getRowListFromInput(input));
    }

    public static int countVertical(String clean) {
        return getSubStringCounts(InputHelper.getColumnListFromInput(clean));
    }

    public static int countDiag(String input) {
        return getSubStringCounts(InputHelper.getDiagonalListFromInput(input));
    }

    private static Integer countSubstrings(String fullString, String seachString) {
        var charsWithOutSearch = fullString.replace(seachString, "");
        return (fullString.length() - charsWithOutSearch.length())
                / seachString.length();
    }

    private static int getSubStringCounts(List<String> strings) {
        return strings.stream()
                .map(s -> countSubstrings(s, "SAMX") + countSubstrings(s, "XMAS"))
                .reduce(0, Integer::sum);


    }


}
