package xyz.alphaline;

import lombok.val;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {

        URL resource = Main.class.getResource("/aocinput/input");

        val count = Files.lines(Path.of(resource.toURI()))
                .map(s -> {
                            Report r = new Report();

                            Arrays.stream(s.split(" "))
                                    .mapToInt(Integer::parseInt)
                                    .forEachOrdered(r::addLevel);

                            return r;
                        }
                ).filter(Report::validate)
                .count();

        System.out.println("Rapports valides : " + count);
    }
}