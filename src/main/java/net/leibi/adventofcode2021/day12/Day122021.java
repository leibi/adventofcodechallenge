package net.leibi.adventofcode2021.day12;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.leibi.helpers.InputHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Log4j2
public class Day122021 {

  static List<List<String>> getAllPaths(final String inut) {
    List<String> inputList = InputHelper.getRowListFromInput(inut);
    Map<String, List<String>> caveMap = getCaveMap(inputList);
    return findRoutes(caveMap);
  }

  private static List<List<String>> findRoutes(Map<String, List<String>> caveMap) {
    List<String> startNodes = caveMap.get("start");
    var result = new ArrayList<List<String>>();
    for (String node : startNodes) {
      List<String> currentRoute = new ArrayList<>();
      List<String> visitedSmallCaves = new ArrayList<>();
      log.info("Starting with Node: {}", node);
      completeRoute(node, caveMap, currentRoute, visitedSmallCaves);
      if (currentRoute.get(currentRoute.size() - 1).equalsIgnoreCase("end"))
        result.add(currentRoute);
    }
    return result;
  }

  private static void completeRoute(
      String node,
      Map<String, List<String>> caveMap,
      List<String> result,
      List<String> visitedSmallCaves) {
    log.info("completeRoute: {}", node);
    if (visitedSmallCaves.contains(node) || visitedSmallCaves.contains("end")) return;
    if (node.equalsIgnoreCase("end")) {
      result.add(node);
      visitedSmallCaves.add(node);
      return;
    }
    result.add(node);
    if (node.equals(node.toLowerCase())) visitedSmallCaves.add(node);
    if (!caveMap.containsKey(node)) return;
    for (String nextNode : caveMap.get(node)) {
      completeRoute(nextNode, caveMap, result, visitedSmallCaves);
    }
  }

  private static Map<String, List<String>> getCaveMap(List<String> inputList) {
    Map<String, List<String>> resultMap = new HashMap<>();
    for (String input : inputList) {
      String[] rooms = input.split("-");
      assert (rooms.length == 2);
      resultMap.computeIfAbsent(rooms[0], k -> new ArrayList<>()).add(rooms[1]);
      if (rooms[0].equals(rooms[0].toUpperCase()) && !rooms[1].equalsIgnoreCase("end"))
        resultMap.computeIfAbsent(rooms[1], k -> new ArrayList<>()).add(rooms[0]);
    }
    return resultMap;
  }
}
