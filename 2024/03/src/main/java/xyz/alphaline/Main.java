package xyz.alphaline;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final String RGX_MULT = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)";

    public static void main(String[] args) throws URISyntaxException, IOException {

        URI path = Main.class.getResource("/aocinput/input").toURI();

        Pattern pattern = Pattern.compile(RGX_MULT);

        int sumAllLines = Files.lines(Path.of(path)).mapToInt(line -> {
            Matcher m = pattern.matcher(line);
            return m.results().mapToInt(matchResult -> Integer.parseInt(matchResult.group(1)) * Integer.parseInt(matchResult.group(2))).sum();
        }).sum();

        System.out.println("La somme des multiplications est de " + sumAllLines);

    }
}