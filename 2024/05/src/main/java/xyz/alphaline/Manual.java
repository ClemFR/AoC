package xyz.alphaline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Manual {

    private final Integer[] manualPages;

    public Manual(Integer[] pages) {
        manualPages = pages;
    }

    public static Manual mapToManual(String manual) {
        return new Manual(
                Arrays.stream(manual.split(","))
                        .map(Integer::parseInt).toArray(Integer[]::new)
        );
    }

    public int getMiddlePage() {
        return manualPages[(manualPages.length - 1) / 2];
    }

    public boolean applyRules(Collection<Rule> rules) {
        List<Integer> pages = List.of(manualPages);
        List<Rule> reducedRules = rules.stream().filter(rule -> pages.contains(rule.getFirstPage())).toList();

        long unmatchedRules = reducedRules.stream().map(rule -> {
            return rule.applyToManual(this);
        }).filter(aBoolean -> !aBoolean).count();

        return unmatchedRules == 0L;
    }

    /**
     * La valeur de manualPages.
     * @return La valeur de manualPages
     */
    public Integer[] getManualPages() {
        return manualPages;
    }

    @Override
    public String toString() {
        return Arrays.toString(manualPages);
    }
}
