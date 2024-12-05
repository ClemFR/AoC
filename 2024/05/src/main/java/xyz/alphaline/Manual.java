package xyz.alphaline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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
        List<Rule> reducedRules = rules.stream().filter(rule -> pages.contains(rule.getBeforePage())).toList();

        long unmatchedRules = reducedRules.stream().map(rule -> {
            return rule.applyToManual(this);
        }).filter(aBoolean -> !aBoolean).count();

        return unmatchedRules == 0L;
    }

    public Manual rectifyManualOrder(List<Rule> rules) {
        List<Integer> pages = new ArrayList<>(List.of(manualPages));
        List<Integer> orderedPages = new ArrayList<>();
        List<Rule> reducedRules = rules.stream().filter(rule -> pages.contains(rule.getBeforePage())).toList();

        List<Rule> orderedRules = Rule.generateOrderedRulesSet(reducedRules);
        if (!Rule.validateRuleOrder(orderedRules)) {
            System.out.println("Probleme : " + orderedRules);
        }

        for (Rule orderedRule : orderedRules) {
            if (pages.contains(orderedRule.getBeforePage())) {
                int ndx = pages.indexOf(orderedRule.getBeforePage());
                orderedPages.add(pages.get(ndx));
                pages.remove(ndx);
            }
        }

        orderedPages.addAll(pages);
        return new Manual(orderedPages.toArray(new Integer[0]));
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
