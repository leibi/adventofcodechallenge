package net.leibi.adventofcode2021.day8;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Getter
@Log4j2
public class Display {

  List<String> patterns;
  List<String> outputValues;

  /**
   *
   *
   * <pre>
   *    aaaa
   *   b    c
   *   b    c
   *    dddd
   *   e    f
   *   e    f
   *    gggg
   * </pre>
   *
   * <p>this is the default and the config would be a,b,c,d,e,f,g
   *
   * <p>So for a mixed up ine
   *
   * <pre>
   *   dddd
   *  e    a
   *  e    a
   *   ffff
   *  g    b
   *  g    b
   *   cccc
   * </pre>
   *
   * <p>The list would be d,e,a,f,g,b,c
   */
  Map<Integer, Integer> mapUniqueLengthToDigit = Map.of(2, 1, 3, 7, 4, 4, 7, 8);

  Map<Integer, String> mapDigitToPattern = new HashMap<>();
  String[] currentConfiguration = new String[7];
  Map<String, String> mapPatternToDefault = new HashMap<>();


  String[] zeroPattern = new String[]{"a","b","c","e","f","g"}; //
  String[] onePattern = new String[]{"c","f"}; //
  String[] twoPattern = new String[]{"a","c","d","e","g"}; //
  String[] threePattern = new String[]{"a","c","d","f","g"}; //
  String[] fourPattern = new String[]{"b","c","d","f"}; //
  String[] fivePattern = new String[]{"a","b","d","f","g"}; //
  String[] sixPattern = new String[]{"a","b","d","e","f","g"}; //
  String[] sevenPattern = new String[]{"a","c","f"}; //
  String[] eightPattern = new String[]{"a","b","c","d","e","f","g"}; //

  String[][] allPattern = new String[][]{zeroPattern, onePattern, twoPattern, threePattern, fourPattern, fivePattern, sixPattern, sevenPattern, eightPattern};

  public Display(String line) {
    patterns = Day82021.parsePattern(line);
    outputValues = Day82021.parseOutputValues(line);
    fillMapPatternToDisplay();
    buildMapPatternToDefault();
  }

  public List<Integer> getOutputDigits() {
    List<Integer>  result= new ArrayList<>();
    for (String pattern : outputValues) {
      result.add(getDigitForPattern(pattern));
    }

    return result;
  }

  void fillMapPatternToDisplay() {
    for (String pattern : patterns) {
      if (mapUniqueLengthToDigit.containsKey(pattern.length())) {
        mapDigitToPattern.put(mapUniqueLengthToDigit.get(pattern.length()), pattern);
      }
    }
  }

  boolean isNotNullOrEmpty(String s){
    return !isNull(s) && !s.isEmpty();
  }

  private Integer getDigitForPattern(String pattern) {
       String[] targetPattern = new String[pattern.length()];
    String[] inputPattern = pattern.split("");
    for (int i = 0; i < inputPattern.length; i++) {
      targetPattern[i] = mapPatternToDefault.get(inputPattern[i]);
    }

    List<String[]> possibleMatches = Arrays.stream(allPattern)
        .filter(p -> p.length == targetPattern.length)
        .filter(p -> targetPatternContainsAllFromPattern(targetPattern,p))
        .collect(Collectors.toList());

    if(possibleMatches.size() == 1){
      for (int i = 0; i < allPattern.length; i++) {
        if (allPattern[i] == possibleMatches.get(0)){
          return i;
        }
      }
    }

    return -1;
  }

  private boolean targetPatternContainsAllFromPattern(String[] targetPattern, String[] p) {
    boolean contains = true;
    for (String currentPatternChar : targetPattern) {
      if (isNotNullOrEmpty(currentPatternChar)) {
        contains = contains && Arrays.asList(p).contains(currentPatternChar);
      }
    }
    return contains;
  }

  private void buildMapPatternToDefault() {
      for (Entry<Integer, String> mapEntry : mapDigitToPattern.entrySet()) {
        String[] patternArray = mapEntry.getValue().split("");
        if (mapEntry.getKey() == 1) { // 1
          currentConfiguration[2] =  patternArray[0];
          currentConfiguration[5] = patternArray[1];
        } else if (mapEntry.getKey() == 7) { // 7
          currentConfiguration[0] = patternArray[0];
          currentConfiguration[2] = patternArray[1];
          currentConfiguration[5] = patternArray[2];
        } else if (mapEntry.getKey() == 4) { // 4
          currentConfiguration[1] = patternArray[0];
          currentConfiguration[2] = patternArray[1];
          currentConfiguration[3] = patternArray[2];
          currentConfiguration[5] = patternArray[3];
        }
      }

    for (int i = currentConfiguration.length - 1; i >= 0; i--) {
      if(isNotNullOrEmpty(currentConfiguration[i])){
        mapPatternToDefault.put(currentConfiguration[i], getCharForIndex(i));
      }
    }
    for (String pattern : patterns) {
      if(mapPatternToDefault.size() == 7) break;
      String[] patternArray = pattern.split("");
      for (String[] globalPattern : allPattern) {
        String[] targetPattern = convertPattern(globalPattern);
        if(patternMatch(patternArray,targetPattern)){
            // match found
            log.info(" {} and {} match! which means it should be {}", patternArray, targetPattern, globalPattern);
           for (int j = 0; j < patternArray.length; j++) {
             if(!mapPatternToDefault.containsKey(patternArray[j])){
               mapPatternToDefault.put(patternArray[j],globalPattern[j]);
             }
           }
          }
        }
      }
    assert(mapPatternToDefault.size() == 7);

  }

  private boolean patternMatch(String[] patternArray, String[] targetPattern) {
    boolean res = patternArray.length == targetPattern.length;
    if(res){
      // does the patternArray Contain all non-null values from targetPattern?
      for (int i = 0; i < targetPattern.length; i++) {
        int finalI = i;
        res = res && (isNull(targetPattern[i]) || Arrays.stream(patternArray).anyMatch(p -> Objects.equals(
            p, targetPattern[finalI])));
        if(!res) return false;
      }
    }
    return res;
  }

  private String[] convertPattern(String[] inputPattern) {
    String[] result = new String[inputPattern.length];
    for (int i = 0; i < inputPattern.length; i++) {
      int finalI = i;
      result[i]= mapPatternToDefault.entrySet().stream().filter(entry -> entry.getValue().equals(inputPattern[finalI])).map(Map.Entry::getKey).findFirst().orElse(null);
    }
    return result;
  }

  private String getCharForIndex(int i) {
    return switch (i) {
      case 0 -> "a";
      case 1 -> "b";
      case 2 -> "c";
      case 3 -> "d";
      case 4 -> "e";
      case 5 -> "f";
      case 6 -> "g";
      default -> "";
    };
  }
}
