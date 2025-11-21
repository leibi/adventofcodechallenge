package net.leibi.adventofcode2023.day8;

import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class Day8 {
    public Long getNumberofSteps(String input) {

        var instructions = input.lines().findFirst().orElseThrow();
        log.info("We have {} instructions", instructions.length());
        var maze = getMaze(input);

        String currentNode = "AAA";
        return getStepCountForStartNode(instructions, currentNode, maze);
    }

    public Long getNumberofStepsPart2(String input) {
        var instructions = input.lines().findFirst().orElseThrow();
        log.info("We have {} instructions", instructions.length());
        var maze = getMaze(input);

        List<String> nodes = maze.getStartNodes();

        int idx = 0;
        long counter = 0;
        while (true) {
            if (idx == instructions.length()) {
                idx = 0;
            }
            if (nodes.stream().allMatch(node -> node.endsWith("Z"))) break;
            log.info("{}: Current nodes: {}, Direction {}", counter, nodes, instructions.charAt(idx));
            nodes = getNextNodes(instructions.charAt(idx), nodes, maze);
            counter++;
            idx++;
        }
        return counter;
    }

    private List<String> getNextNodes(Character direction, List<String> nodes, Maze maze) {

        return nodes.stream().map((String currentNode) -> maze.getNextNode(currentNode, direction)).toList();
    }

    private static Long getStepCountForStartNode(String instructions, String currentNode, Maze maze) {
        Long counter = 0L;


        int idx = 0;
        while (true) {
            if (idx == instructions.length()) {
                idx = 0;
            }
            if (currentNode.endsWith("Z")) break;
            var oldNode = currentNode;
            currentNode = maze.getNextNode(currentNode, instructions.charAt(idx));
            log.info("{}: Next Node: {} from {} with direction {}", counter, currentNode, oldNode, instructions.charAt(idx));
            counter++;
            idx++;
        }

        return counter;
    }

    private Maze getMaze(String input) {

        var mazeItems = input.lines().skip(1)
                .filter(s -> !s.isEmpty())
                .map(MazeItem::new).toList();

        return new Maze(mazeItems);
    }
}
