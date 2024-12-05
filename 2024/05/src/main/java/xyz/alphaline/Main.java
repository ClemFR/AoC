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

        System.out.println("Part 1 : La somme des pages du milieu est " + sum);

        List<Manual> incorrectReordonne = Files.lines(Path.of(manuals))
                .map(Manual::mapToManual)
                .filter(manual -> !manual.applyRules(ruleSet))
                .map(manual -> manual.rectifyManualOrder(ruleSet))
                .toList();

        long incorrectApresRemappage = incorrectReordonne.stream().filter(manual -> !manual.applyRules(ruleSet)).count();

        if (incorrectApresRemappage != 0L) {
            System.out.println("ATTENTION " + incorrectApresRemappage + " MANUELS INCORRECTS !");
        }

        int sumPt2 = incorrectReordonne.stream().mapToInt(Manual::getMiddlePage).sum();

        System.out.println("Part 2 : La somme des pages du milieu est " + sumPt2);
    }
}