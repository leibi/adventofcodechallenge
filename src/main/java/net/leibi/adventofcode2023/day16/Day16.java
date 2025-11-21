package net.leibi.adventofcode2023.day16;

import lombok.extern.log4j.Log4j2;
import net.leibi.helpers.InputHelper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@Log4j2
public class Day16 {

    public static final int ANSWERDAY1SMALL = 46;
    private static final Map<MovingDecisionKey, MovingDirection> movingDirectionsMap = getMovingDirectionsMap();
    private static char[][] charMatrixFromInput;
    private static char[][] energizedCharMatrix;

    private static final Set<DirectedPoint> alreadySeenDirectedPoints = new HashSet<>();

    public static long day1(String input) {

        charMatrixFromInput = InputHelper.getCharMatrixFromInput(input);
        InputHelper.printCharArray(charMatrixFromInput);
        var width = charMatrixFromInput.length;
        var hight = charMatrixFromInput[0].length;

        log.info("The array is {} by {}", width, hight);

        energizedCharMatrix = new char[width][hight];

        final var start = new Point(0, 0);
        followBeam(new DirectedPoint(start, MovingDirection.RIGHT));

        int sum = 0;
        for (char[] charMatrix : energizedCharMatrix) {
            for (char element : charMatrix) {
                if (element == '#') sum++;
            }
        }
        InputHelper.printCharArray(energizedCharMatrix);
        log.info("sum: {}", sum);


        return ANSWERDAY1SMALL;
    }

    static Map<MovingDecisionKey, MovingDirection> getMovingDirectionsMap() {
        Map<MovingDecisionKey, MovingDirection> movingDirectionsMap = new HashMap<>();

        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.RIGHT, '|'), MovingDirection.UPANDDOWN);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.LEFT, '|'), MovingDirection.UPANDDOWN);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.UP, '|'), MovingDirection.UP);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.DOWN, '|'), MovingDirection.DOWN);

        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.RIGHT, '/'), MovingDirection.UP);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.LEFT, '/'), MovingDirection.DOWN);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.UP, '/'), MovingDirection.RIGHT);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.DOWN, '/'), MovingDirection.LEFT);

        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.RIGHT, '\\'), MovingDirection.DOWN);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.LEFT, '\\'), MovingDirection.UP);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.UP, '\\'), MovingDirection.LEFT);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.DOWN, '\\'), MovingDirection.RIGHT);

        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.RIGHT, '-'), MovingDirection.RIGHT);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.LEFT, '-'), MovingDirection.LEFT);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.UP, '-'), MovingDirection.LEFTANDRIGHT);
        movingDirectionsMap.put(new MovingDecisionKey(MovingDirection.DOWN, '-'), MovingDirection.LEFTANDRIGHT);
        return movingDirectionsMap;
    }

    static MovingDirection getMovingDirection(MovingDirection direction, Character device) {
        if (device == '.') return direction;
        return movingDirectionsMap.get(new MovingDecisionKey(direction, device));
    }

    private static void followBeam(DirectedPoint directedPoint) {
        if (directedPoint == null || directedPoint.point == null) return;
        if(alreadySeenDirectedPoints.contains(directedPoint)) return;
        alreadySeenDirectedPoints.add(directedPoint);
        log.info("Following beam: {}", directedPoint);
        var nextMovingDirection = getNewMovingDirection(directedPoint);
        final var nextPoints = directedPoint.point.getNextPoint(nextMovingDirection);

        followBeam(nextPoints.p1);
        followBeam(nextPoints.p2);

    }

    private static MovingDirection getNewMovingDirection(DirectedPoint directedPoint) {
        if (directedPoint.point == null)
        {
            log.info("Dore");
            return directedPoint.movingDirection;
        }
        var device = getDevice(directedPoint);
        return getMovingDirection(directedPoint.movingDirection, device);
    }

    private static char getDevice(DirectedPoint directedPoint) {
        energizedCharMatrix[directedPoint.point.x][directedPoint.point.y] = '#';
        return charMatrixFromInput[directedPoint.point.x][directedPoint.point.y];
    }

    enum MovingDirection {
        RIGHT,
        LEFT,
        UP,
        DOWN,
        LEFTANDRIGHT,
        UPANDDOWN
    }

    record MovingDecisionKey(MovingDirection movingDirection, Character Device) {
    }

    record Point(int x, int y) {
        Point {
            if (x < 0 || y < 0 || x >= charMatrixFromInput.length || y >= charMatrixFromInput[0].length) {
                log.info("Not a good point {},{}",x,y);
                throw new IllegalArgumentException();
            }
        }

        NextDirections getNextPoint(MovingDirection movingDirection) {
            return switch (movingDirection) {
                case RIGHT -> new NextDirections(new DirectedPoint(x, y + 1, MovingDirection.RIGHT), null);
                case LEFT -> new NextDirections(new DirectedPoint(x, y - 1, MovingDirection.LEFT), null);
                case UP -> new NextDirections(new DirectedPoint(x - 1, y, MovingDirection.UP), null);
                case DOWN -> new NextDirections(new DirectedPoint(x + 1, y, MovingDirection.DOWN), null);
                case UPANDDOWN ->
                        new NextDirections(new DirectedPoint(x + 1, y, MovingDirection.DOWN), new DirectedPoint(x - 1, y, MovingDirection.UP));
                case LEFTANDRIGHT ->
                        new NextDirections(new DirectedPoint(x, y + 1, MovingDirection.LEFT), new DirectedPoint(x, y + 1, MovingDirection.RIGHT));
            };
        }


    }

    record NextDirections(DirectedPoint p1, DirectedPoint p2) {
    }

    record DirectedPoint(Point point, MovingDirection movingDirection) {
        public DirectedPoint(int x, int y, MovingDirection movingDirection) {
            Point p = null;
            try {
                p = new Point(x, y);
            } catch (IllegalArgumentException e) {
                log.info("Point hitting a wall: {},{}", x, y);
            }
            this(p, movingDirection);
        }
    }

}



