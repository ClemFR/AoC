package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Path input = Path.of(Main.class.getResource("/aocinput/input").toURI());
        int[][] heatMap = Files.lines(input)
                .map(s -> s.split(""))
                .map(strings -> Arrays.stream(strings).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        System.out.println("Part 1 : " + solvePart1(heatMap));
    }

    public static int solvePart1(int[][] map) {

        int sum = 0;

        for (int ndxLine = 0; ndxLine < map.length; ndxLine++) {
            for (int i = 0; i < map[ndxLine].length; i++) {
                if (map[ndxLine][i] == 0) {
                    int find = findNextLayerPart1(map, ndxLine, i, new ArrayList<>());
                    sum += find;
//                    System.out.println(find);
                }
            }
        }

        return sum;
    }

    public static int findNextLayerPart1(int[][] map, int curLine, int curCol, List<Integer[]> founds) {
        int sum = 0;
        int curHeight = map[curLine][curCol];
        if (curHeight == 9) {
            for (Integer[] found : founds) {
                if (found[0] == curLine && found[1] == curCol) {
                    return 0;
                }
            }

            founds.add(new Integer[]{curLine, curCol});
            return 1;
        }

        int goal = curHeight + 1;
        if (arrayCoordsEqualsTo(map, curLine + 1, curCol, goal)) {
            sum += findNextLayerPart1(map, curLine + 1, curCol, founds);
        }
        if (arrayCoordsEqualsTo(map, curLine - 1, curCol, goal)) {
            sum += findNextLayerPart1(map, curLine - 1, curCol, founds);
        }
        if (arrayCoordsEqualsTo(map, curLine, curCol + 1, goal)) {
            sum += findNextLayerPart1(map, curLine, curCol + 1, founds);
        }
        if (arrayCoordsEqualsTo(map, curLine, curCol - 1, goal)) {
            sum += findNextLayerPart1(map, curLine, curCol - 1, founds);
        }

        return sum;

    }

    public static boolean arrayCoordsEqualsTo(int[][] map, int y, int x, int check) {
        // inbound check
        if (y < 0 || y >= map.length) {
            return false;
        }
        if (x < 0 || x >= map[0].length) {
            return false;
        }

        return map[y][x] == check;
    }
}