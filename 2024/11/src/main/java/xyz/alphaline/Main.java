package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {

        Path input = Path.of(Main.class.getResource("/aocinput/input").toURI());
        List<Long> pebbles = Arrays.stream(Files.readAllLines(input).getFirst().split(" "))
                .map(Long::parseLong).toList();

        Map<Long, Long> pebblesVal = new HashMap<>();
        for (Long pebble : pebbles) {
            pebblesVal.put(pebble, pebblesVal.getOrDefault(pebble, 0L) + 1);
        }

        // System.out.println(pebbles);
        for (int i = 0; i < 75; i++) {
            Map<Long, Long> pebblesTmp = new HashMap<>();
            for (Map.Entry<Long, Long> longLongEntry : pebblesVal.entrySet()) {
                long value = longLongEntry.getKey();
                long weight = longLongEntry.getValue();

                if (value == 0) {
                    pebblesTmp.put(1L, pebblesTmp.getOrDefault(1L, 0L) + weight);
                } else if (String.valueOf(value).length() % 2 == 0) {
                    String convVal = String.valueOf(value);
                    long left = Long.parseLong(convVal.substring(0, String.valueOf(value).length() / 2));
                    long right = Long.parseLong(convVal.substring(String.valueOf(value).length() / 2, String.valueOf(value).length()));

                    pebblesTmp.put(left, pebblesTmp.getOrDefault(left, 0L) + weight);
                    pebblesTmp.put(right, pebblesTmp.getOrDefault(right, 0L) + weight);
                } else {

                    value *= 2024;
                    pebblesTmp.put(value, pebblesTmp.getOrDefault(value, 0L) + weight);
                }
            }
            pebblesVal = pebblesTmp;
            System.out.println(i);
        }

        System.out.println(pebblesVal.values().stream().mapToLong(Long::longValue).sum());
    }
}