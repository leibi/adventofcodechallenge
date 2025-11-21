package net.leibi.adventofcode2021.day3;

import java.util.ArrayList;
import java.util.List;

import static net.leibi.helpers.InputHelper.getIntegerFromIntArray;
import static net.leibi.helpers.InputHelper.getIntegerFromString;
import static net.leibi.helpers.InputHelper.getRowListFromInput;

public class Day32021 {

  int co2ScrubberRating(String input) {
    return getRating(input, RatingEnum.CO_2);
  }

  public int getLifeSupportRating(final String input) {
    return getOxigenGeneratorRating(input) * co2ScrubberRating(input);
  }

  int getOxigenGeneratorRating(String input) {
    return getRating(input, RatingEnum.OXIGEN);
  }

  int getRating(String input, RatingEnum rating) {
    List<String> rowList = getRowListFromInput(input);
    List<String> columns = getColumnsFromRowList(rowList);

    List<String> filteredRowList = rowList;
    for (int i = 0; i < columns.size(); i++) {
      columns = getColumnsFromRowList(filteredRowList);
      int mostCommonBit =
          rating == RatingEnum.OXIGEN
              ? getMostCommonBinaryInString(columns.get(i))
              : getLeastCommonBinaryInString(columns.get(i));
      filteredRowList = getRowsWithBinaryInPosition(filteredRowList, mostCommonBit, i);
      if (filteredRowList.size() == 1) break;
    }
    assert (filteredRowList.size() == 1);
    return getIntegerFromString(filteredRowList.get(0));
  }

  enum RatingEnum {
    OXIGEN,
    CO_2
  }

  List<String> getColumnsFromInput(final String input) {
    return getColumnsFromRowList(getRowListFromInput(input));
  }



  public int getPowerConsumption(final String input) {

    List<String> columns = getColumnsFromInput(input);
    int gammaValue = getGammaValue(columns);
    int epsilonRate = getEpsilonRate(columns);

    return gammaValue * epsilonRate;
  }

  private List<String> getRowsWithBinaryInPosition(List<String> rowList, int mostCommonBit, int i) {
    List<String> filteredList = new ArrayList<>();
    for (String s : rowList) {
      if(Character.getNumericValue(s.charAt(i)) == mostCommonBit){
        filteredList.add(s);
      }
    }
    return filteredList;
  }

  private List<String> getColumnsFromRowList(List<String> rowlist) {
    int columnSize = rowlist.get(0).length();
    List<String> columns = new ArrayList<>();
    for (String s : rowlist) {
      char[] splittedString = s.toCharArray();
      assert (splittedString.length == columnSize);
      for (int i = 0; i < splittedString.length; i++) {
        if (columns.size() > i) {
          String newString = columns.get(i).concat(String.valueOf(splittedString[i]));
          columns.set(i, newString);
        } else {
          columns.add(i, String.valueOf(splittedString[i]));
        }
      }
    }
    return columns;
  }

  int getMostCommonBinaryInString(final String string) {
    String ones = string.replace("0", "");
    String zeros = string.replace("1", "");
    if (ones.length() >= zeros.length()) return 1;
    else return 0;
  }

  int getLeastCommonBinaryInString(final String string) {
    if (getMostCommonBinaryInString(string) == 1) return 0;
    return 1;
  }

   int getGammaValue(List<String> columns) {
    int[] gamma = new int[columns.size()];
    for (int i = 0; i < columns.size(); i++) {
      gamma[i] = getMostCommonBinaryInString(columns.get(i));
    }
    return getIntegerFromIntArray(gamma);
  }

   int getEpsilonRate(List<String> columns) {
    int[] gamma = new int[columns.size()];
    for (int i = 0; i < columns.size(); i++) {
      gamma[i] = getLeastCommonBinaryInString(columns.get(i));
    }
    return getIntegerFromIntArray(gamma);
  }


}
