package xyz.alphaline;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        URI rules = Main.class.getResource("/aocinput/rules").toURI();
        URI manuals = Main.class.getResource("/aocinput/manuals").toURI();
        List<Rule> ruleSet = Files.lines(Path.of(rules)).map(Rule::mapToRule).toList();

        int sum = Files.lines(Path.of(manuals))
                .map(Manual::mapToManual)
                .filter(manual -> manual.applyRules(ruleSet))
                .mapToInt(Manual::getMiddlePage)
                .sum();

        System.out.println("La somme des pages du milieu est " + sum);
    }
}