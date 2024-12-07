package xyz.alphaline;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        URI uri = Main.class.getResource("/aocinput/input").toURI();
        List<Equation> equations = Files.lines(Path.of(uri)).map(Equation::fromString).toList();
        // equations.forEach(System.out::println);
        long sum = equations.stream().filter(Equation::isEquationOk).mapToLong(Equation::getGoal).sum();

        System.out.println("La somme des équations ok est de " + sum);
    }
}