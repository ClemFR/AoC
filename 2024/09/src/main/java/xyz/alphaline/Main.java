package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws Exception {

        Path inPath = Path.of(Main.class.getResource("/aocinput/input").toURI());
        AmphipodDisk disk = AmphipodDisk.fromString(Files.readAllLines(inPath).getFirst());

        disk.compact();
        long diskChecksum = Arrays.stream(disk.getDiskArray())
                .filter(dfb -> !dfb.isHole())
                .mapToLong(dfb -> (long) dfb.getDiskBlockLocation() * dfb.getParentFile().getFileId())
                .sum();
        System.out.println("Part 1 : " + diskChecksum);

    }
}