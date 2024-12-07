package xyz.alphaline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    static int guardStartX = -1;
    static int guardStartY = -1;
    static Guard.Facing startFacing;

    public static void main(String[] args) {

        Map map = Map.getFromInput("/aocinput/input");
        Guard g = Guard.getFromInput("/aocinput/input", map);

        guardStartX = g.getxPos();
        guardStartY = g.getyPos();
        startFacing = g.getFacing();

        map.guard = g;

        g.generatePath();
        long visitedTiles = Arrays.stream(map.tilesMap).mapToLong(tiles -> Arrays.stream(tiles).filter(Tile::getVisitedState).count()).sum();

        System.out.println(map);
        System.out.println("Cases visitées par le garde : " + visitedTiles);

        // Part 2
        List<Tile> toBeObstructed = map.getTilesStream()
                .filter(tile -> !tile.getCrateState())
                .filter(tile -> !(tile.getxPos() == guardStartX && tile.getyPos() == guardStartY))
                .toList();

        List<Tile> obstructedCreateLoop = new ArrayList<>();
        int i = 0;
        for (Tile tile : toBeObstructed) {
            // On obstructe
            tile.setObstructedGenerateLoop();

            Guard gTmp = new Guard(guardStartX, guardStartY, startFacing, map);
            map.getTilesStream().forEach(Tile::resetVisitedFlag);
            map.getTilesStream().forEach(tile1 -> tile1.getHittingSides().clear());
            map.guard = gTmp;

            boolean b = gTmp.discoverLoopTiles();
            tile.unsetObstructedGenerateLoop();
            if (b) {
                obstructedCreateLoop.add(tile);
                // System.out.println(tile.getxPos() + " " + tile.getyPos());
            }

            i++;
            System.out.print("\rprogress : " + i + " / " + toBeObstructed.size());
        }

        System.out.println();

        for (Tile tile : obstructedCreateLoop) {
            tile.setObstructedGenerateLoop();
        }

        long count = map.getTilesStream().filter(Tile::isObstructedGenerateLoop)
                .count();
        System.out.println(map.displayWithObstructedTiles());
        System.out.println("part 2 : " + count);
    }
}