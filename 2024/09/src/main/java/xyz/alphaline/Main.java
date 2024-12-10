package xyz.alphaline;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Path inPath = Path.of(Main.class.getResource("/aocinput/input2").toURI());
        AmphipodDisk disk = AmphipodDisk.fromString(Files.readAllLines(inPath).getFirst());
        long diskChecksum;

//        disk.compact();
//        diskChecksum = Arrays.stream(disk.getDiskArray())
//                .filter(dfb -> !dfb.isHole())
//                .mapToLong(dfb -> (long) dfb.getDiskBlockLocation() * dfb.getParentFile().getFileId())
//                .sum();
//        System.out.println("Part 1 : " + diskChecksum);

        // Part 2
        AmphipodDisk diskPt2 = AmphipodDisk.fromString(Files.readAllLines(inPath).getFirst());
        diskPt2.compactWithoutFragmenting();

        List<BigInteger> chksumLte = Arrays.stream(diskPt2.getDiskArray())
                .filter(dfb -> !dfb.isHole())
                .map(dfb -> BigInteger.valueOf((long) dfb.getDiskBlockLocation() * dfb.getParentFile().getFileId()))
                .toList();

        // 15599488510200
        // 15599488510200
        BigInteger sum = BigInteger.ZERO;
        for (BigInteger bigInteger : chksumLte) {
            sum = sum.add(bigInteger);
        }
        System.out.println("Part 2 : " + sum);
    }
}