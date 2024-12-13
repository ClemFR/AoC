package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Path input = Path.of(Main.class.getResource("/aocinput/input").toURI());
        int sumPt1 = Arrays.stream(Files.readString(input).trim().split("\n\n"))
                .map(ClawMachine::new)
                .filter(ClawMachine::isFaisable)
                .mapToInt(ClawMachine::getTokenCost)
                .sum();

        System.out.println("Part 1 : " + sumPt1);

        // I'm not a mathematician RIP P2.
    }
}