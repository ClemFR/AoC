package xyz.alphaline;

import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Path input = Path.of(Main.class.getResource("/aocinput/input").toURI());
        Farm farm = new Farm(input);
        List<Region> regions = farm.getRegions();

        int sum = regions.stream().mapToInt(Region::getPrice).sum();
        System.out.println("Part 1 : " + sum);

    }
}