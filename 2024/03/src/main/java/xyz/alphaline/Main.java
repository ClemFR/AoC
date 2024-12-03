package xyz.alphaline;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    public static final String RGX_FILTER_DO = "(.*?)(?:(?:don't\\(\\)|\\Z).*?(?:do\\(\\)|\\Z)(.*?))+";
    public static final String RGX_MULT = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)";

    public static void main(String[] args) throws URISyntaxException, IOException {

        URI path = Main.class.getResource("/aocinput/input").toURI();

        Pattern patternDo = Pattern.compile(RGX_FILTER_DO);
        Pattern patternMult = Pattern.compile(RGX_MULT);
        // Soluce 1 : 174336360

        String corruptedMemory = Files.lines(Path.of(path)).collect(Collectors.joining());
        Matcher matchDo = patternDo.matcher(corruptedMemory);

        int sum = matchDo.results().mapToInt(matcherDo -> patternMult.matcher(matcherDo.group(1))
                .results().mapToInt(matcherMul -> Integer.parseInt(matcherMul.group(1)) * Integer.parseInt(matcherMul.group(2))).sum()).sum();
        System.out.println("La somme des multiplications est de " + sum);

    }
}