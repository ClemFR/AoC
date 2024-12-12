package xyz.alphaline;

import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Path input = Path.of(Main.class.getResource("/aocinput/input").toURI());
        Farm farm = new Farm(input);
        List<Region> regions = farm.getRegions();

        int sumP1 = regions.stream().mapToInt(Region::getFullPrice).sum();
        System.out.println("Part 1 : " + sumP1);

        // regions.forEach(System.out::println);

        int sumP2 = regions.stream().mapToInt(Region::getDiscountPrice).sum();
        System.out.println("Part 2 : " + sumP2);

    }
}