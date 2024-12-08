package xyz.alphaline;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        Map map = Map.fromInputPath(Path.of(Main.class.getResource("/aocinput/input").toURI()));

        map.findAntinodes();
        System.out.println(map);

        long antinodes = map.locationStream().filter(Location::isAntinode).count();
        System.out.println("Part 1 : " + antinodes + " antinodes trouvées !");
    }
}