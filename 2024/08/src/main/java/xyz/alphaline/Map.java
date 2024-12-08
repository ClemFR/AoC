package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Map {

    private Location[][] mapArray;

    private int maxX;
    private int maxY;

    private Map(Location[][] arr) {
        mapArray = arr;

        maxX = arr[0].length - 1;
        maxY = arr.length - 1;
    }

    public static Map fromInputPath(Path input) throws Exception {

        String[][] locs = Files.lines(input).map(s -> s.split("")).toArray(String[][]::new);

        Location[][] map = new Location[locs.length][locs[0].length];

        for (int y = 0; y < locs.length; y++) {
            for (int x = 0; x < locs[y].length; x++) {
                map[y][x] = new Location(locs[y][x], x, y);
            }
        }

        return new Map(map);
    }

    public void findAntinodes() {
        List<String> allFreqs = getAllFreqs();
        for (String freq : allFreqs) {
            List<Location> antennasFreq = locationStream()
                    .filter(Location::isAntenna)
                    .filter(location -> location.getAntennaFrequency().equals(freq))
                    .toList();

            for (Location antenna1 : antennasFreq) {
                for (Location antenna2 : antennasFreq) {
                    if (antenna1 == antenna2) continue;
                    int xDst = antenna1.distXBetween(antenna2);
                    int yDst = antenna1.distYBetween(antenna2);

                    int posXAntinode = antenna2.getXPos() + xDst;
                    int posYAntinode = antenna2.getYPos() + yDst;

                    Location antinode = getLocationFromCoordinates(posXAntinode, posYAntinode);
                    if (antinode != null) {
                        antinode.setAntinode(true);
                    }
                }
            }
        }
    }

    public Location getLocationFromCoordinates(int x, int y) {
        if (x < 0 || x > maxX) return null;
        if (y < 0 || y > maxY) return null;

        return mapArray[y][x];
    }

    public Stream<Location> locationStream() {
        return Arrays.stream(mapArray).flatMap(Arrays::stream);
    }

    public List<String> getAllFreqs() {
        return locationStream()
                .filter(Location::isAntenna)
                .map(Location::getAntennaFrequency)
                .distinct()
                .toList();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Location[] locations : mapArray) {
            for (Location location : locations) {
                sb.append(location);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
