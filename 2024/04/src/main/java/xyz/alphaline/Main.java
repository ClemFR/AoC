package xyz.alphaline;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static final String RGX_XMAS = "XMAS";

    public static void main(String[] args) throws Exception {

        URL input = Main.class.getResource("/aocinput/input");

        String[][] array = Files.lines(Path.of(input.toURI()))
                .map(s -> s.split(""))
                .toList().toArray(String[][]::new);

        int sum = 0;
        Pattern pattern = Pattern.compile(RGX_XMAS);
        sum += Arrays.stream(mapLtoR(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapRtoL(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapTtoB(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapBtoT(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapLBtoRT(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapLTtoRB(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapRBtoLT(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();
        sum += Arrays.stream(mapRTtoLB(array)).mapToInt(s -> Math.toIntExact(pattern.matcher(s).results().count())).sum();

        System.out.println("XMAS trouvés : " + sum);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : gauche à droite
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapLtoR(String[][] input) {
        List<String> newLines = new ArrayList<>();

        for (String[] line : input) {
            newLines.add(String.join("", line));
        }

        return newLines.toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : droite à gauche
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapRtoL(String[][] input) {
        List<String> newLines = new ArrayList<>();

        for (String[] line : input) {
            List<String> lineList = Arrays.asList(line);
            Collections.reverse(lineList);
            newLines.add(String.join("", lineList));
        }

        return newLines.toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : haut en bas
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapTtoB(String[][] input) {
        String[] newLines = new String[input[0].length];
        Arrays.fill(newLines, "");

        for (String[] line : input) {
            for (int charPos = 0; charPos < line.length; charPos++) {
                newLines[charPos] += line[charPos];
            }
        }
        return newLines;
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : bas en haut
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapBtoT(String[][] input) {
        String[] linesTtoB = mapTtoB(input);

        List<String> reversedLines = new ArrayList<>();
        return Arrays.stream(linesTtoB).map(s -> {
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.reverse();
            return sb.toString();
        }).toList().toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : haut gauche vers bas droite (diagonale)
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapLBtoRT(String[][] input) { //RTtoLB

        // Lecture de chaque ligne haut en bas, de droite à gauche
        List<String> tampon = new ArrayList<>();
        List<String> lignesDiags = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < input.length; lineIndex++) {
            String[] line = input[lineIndex];

            List<String> lineReverse = new ArrayList<>(List.of(line));
            Collections.reverse(lineReverse);

            // cas spécial : première ligne
            if (lineIndex == 0) {
                tampon.addAll(lineReverse);
            } else {
                tampon.add("");
                for (int i = 0; i < lineReverse.size(); i++) {
                    tampon.set(i, tampon.get(i) + lineReverse.get(i));
                }
            }

            lignesDiags.add(tampon.removeFirst());
        }

        lignesDiags.addAll(tampon);
        return lignesDiags.toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : bas gauche vers haut droite (diagonale)
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapLTtoRB(String[][] input) {
        String[] linesLTtoRB = mapRBtoLT(input);
        List<String> reversed = new ArrayList<>();

        for (String s : linesLTtoRB) {
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.reverse();
            reversed.add(sb.toString());
        }

        return reversed.toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : haut droite vers bas gauche (diagonale)
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapRBtoLT(String[][] input) {

        // Lecture de chaque ligne haut en bas, de droite à gauche
        List<String> tampon = new ArrayList<>();
        List<String> lignesDiags = new ArrayList<>();

        for (int lineIndex = 0; lineIndex < input.length; lineIndex++) {
            List<String> line = List.of(input[lineIndex]);

            // cas spécial : première ligne
            if (lineIndex == 0) {
                tampon.addAll(line);
            } else {
                tampon.add("");
                for (int i = 0; i < line.size(); i++) {
                    tampon.set(i, tampon.get(i) + line.get(i));
                }
            }

            lignesDiags.add(tampon.removeFirst());
        }

        lignesDiags.addAll(tampon);
        return lignesDiags.toArray(new String[0]);
    }

    /**
     * Transforme la matrice de caractères en liste de lignes.
     * Lecture de la matrice : bas droite vers haut gauche (diagonale)
     * @param input la matrice
     * @return la liste de lignes
     */
    public static String[] mapRTtoLB(String[][] input) {
        String[] linesRTtoLB = mapLBtoRT(input);
        List<String> reversed = new ArrayList<>();

        for (String s : linesRTtoLB) {
            StringBuilder sb = new StringBuilder();
            sb.append(s);
            sb.reverse();
            reversed.add(sb.toString());
        }

        return reversed.toArray(new String[0]);
    }
}