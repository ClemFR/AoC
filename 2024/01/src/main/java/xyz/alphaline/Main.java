package xyz.alphaline;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {

        List<Integer> gauche = new ArrayList<>();
        List<Integer> droite = new ArrayList<>();

        Path fich = Paths.get(Main.class.getResource("/input").toURI());

        Files.lines(fich)
                .map(s -> s.split(" {3}"))
                .map(strings -> List.of(Integer.parseInt(strings[0]), Integer.parseInt(strings[1])))
                .forEach(integers -> {
                    gauche.add(integers.getFirst());
                    droite.add(integers.getLast());
                });

        Collections.sort(gauche);
        Collections.sort(droite);

        int sum = mapToList(gauche, droite)
                .stream()
                .mapToInt(gd -> Math.abs(gd.getGauche() - gd.getDroite())).sum();

        System.out.println("La somme est de " + sum);
    }

    private static List<GaucheDroite> mapToList(
            List<Integer> gauche,
            List<Integer> droite
            ) {

        List<GaucheDroite> lte = new ArrayList<>();

        for (int i = 0; i < gauche.size() ; i++) {
            lte.add(new GaucheDroite(gauche.get(i), droite.get(i)));
        }

        return lte;
    }

    public static class GaucheDroite {

        private int gauche;
        private int droite;

        public GaucheDroite(
                int gaucheCstr,
                int droiteCstr
        ) {
            gauche = gaucheCstr;
            droite = droiteCstr;
        }

        /**
         * La valeur de gauche.
         * @return La valeur de gauche
         */
        public int getGauche() {
            return gauche;
        }

        /**
         * La valeur de droite.
         * @return La valeur de droite
         */
        public int getDroite() {
            return droite;
        }
    }
}