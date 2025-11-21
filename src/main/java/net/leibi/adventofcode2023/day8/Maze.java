package net.leibi.adventofcode2023.day8;


import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class Maze {
    private final Map<String, MazeItem> mazeItemMap = new HashMap<>();
    private final Map<LoopKey, Integer> loopDetection = new HashMap<>();

    public Maze(List<MazeItem> mazeItems) {
        mazeItems.forEach(mazeItem -> mazeItemMap.put(mazeItem.from(), mazeItem));
    }

    public String getNextNode(String currentNode, char direction) {
        var key = new LoopKey(currentNode, direction);
        if (loopDetection.containsKey(key)) {
            //log.info("Loop at {}", key);
            //throw new RuntimeException("loop: %s".formatted( key));
        }
        loopDetection.put(key, 1);
        var mazeItem = mazeItemMap.get(currentNode);
        return switch (direction) {
            case 'R' -> mazeItem.right();
            case 'L' -> mazeItem.left();
            default -> throw new RuntimeException("Unknown direction");
        };
    }

    public List<String> getStartNodes() {
        return mazeItemMap.keySet().stream().filter(node -> node.endsWith("A")).toList();
    }

    record LoopKey(String node, Character direction) {
    }
}
