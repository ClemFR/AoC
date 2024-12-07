package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Map {

    Tile[][] tilesMap;

    Guard guard;


    public Map(Tile[][] map) {
        tilesMap = map;
    }

    public static Map getFromInput(String resPath) {
        try {
            Path input = Path.of(Map.class.getResource(resPath).toURI());
            Tile[][] tiles = Files.lines(input)
                    .map(s -> s.split(""))
                    .map(strings -> Arrays.stream(strings).map(Tile::getTileFromString).toArray(Tile[]::new))
                    .toArray(Tile[][]::new);

            return new Map(tiles);

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean isTileInbounds(int x, int y) {
        if (x < 0 || x > tilesMap[0].length - 1) {
            return false;
        }

        if (y < 0 || y > tilesMap.length - 1) {
            return false;
        }

        return true;
    }

    public Tile getGuardSittingTile(Guard guard) {

        int x = guard.getxPos();
        int y = guard.getyPos();

        if (x < 0 || x > tilesMap[0].length) {
            throw new IndexOutOfBoundsException("Le garde a dépassé les limites de la carte sur l'axe X : " + x);
        }
        if (y < 0 || y > tilesMap.length) {
            throw new IndexOutOfBoundsException("Le garde a dépassé les limites de la carte sur l'axe Y : " + y);
        }

        return tilesMap[y][x];
    }

    public Tile getGuardNextTile(Guard guard) {
        int x = guard.getxPos();
        int y = guard.getyPos();

        switch (guard.getFacing()) {
            case NORTH -> {
                y--;
            }
            case SOUTH -> {
                y++;
            }
            case EAST -> {
                x++;
            }
            case WEST -> {
                x--;
            }
        }

        if (isTileInbounds(x, y)) {
            return tilesMap[y][x];
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int y = 0;
        int x = 0;
        for (Tile[] line : tilesMap) {

            for (Tile tile : line) {
                boolean drawn = false;

                if (tile.getCrateState()) {
                    if (guard.getxPos() == x && guard.getyPos() == y) {
                        sb.append("=");
                    } else {
                        if (tile.getVisitedState()) {
                            sb.append("!");
                        } else {
                            sb.append("#");
                        }

                    }
                    drawn = true;
                } else {
                    if (guard.getxPos() == x && guard.getyPos() == y) {
                        sb.append(guard.getFacing().getRepresenting());
                    } else {
                        sb.append(tile.getVisitedState() ? "X" : ".");
                    }
                }

                x++;
            }
            sb.append("\n");
            y++;
        }

        return sb.toString();
    }
}
