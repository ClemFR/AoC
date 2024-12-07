package xyz.alphaline;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Map map = Map.getFromInput("/aocinput/input");
        Guard g = Guard.getFromInput("/aocinput/input", map);
        map.guard = g;

        g.generatePath();
        long visitedTiles = Arrays.stream(map.tilesMap).mapToLong(tiles -> Arrays.stream(tiles).filter(Tile::getVisitedState).count()).sum();

        System.out.println(map);
        System.out.println("Cases visitées par le garde : " + visitedTiles);
    }
}