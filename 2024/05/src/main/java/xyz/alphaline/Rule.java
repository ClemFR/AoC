package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    private final int pageThatMustBeBefore;

    private final int pageAfter;

    private final List<Rule> dependsOf;

    public Rule(int before, int after) {
        pageThatMustBeBefore = before;
        pageAfter = after;
        dependsOf = new ArrayList<>();
    }

    /**
     * Crée un objet règle à partir de sa représentation en chaine de caractères.
     * @param rule La règle à créer
     * @return La régle crée
     */
    public static Rule mapToRule(String rule) {
        String[] split = rule.split("\\|");
        return new Rule(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    public int getBeforePage() {
        return pageThatMustBeBefore;
    }

    public boolean applyToManual(Manual man) {
        Integer[] manualPages = man.getManualPages();

        for (Integer manualPage : manualPages) {
            if (manualPage.equals(pageThatMustBeBefore)) {
                return true;
            }
            if (manualPage.equals(pageAfter)) {
                return false;
            }
        }

        throw new RuntimeException("Page " + pageThatMustBeBefore + " not found in the manual " + man);
    }

    public static List<Rule> generateOrderedRulesSet(List<Rule> unordered) {
        unordered = new ArrayList<>(unordered);
        List<Rule> ordered = new ArrayList<>();

        for (Rule rule : unordered) {
            for (Rule rule1 : unordered) {
                if (rule.pageAfter == rule1.pageThatMustBeBefore) {
                    rule1.dependsOf.add(rule);
                }
            }
        }

        boolean end = false;
        while (!end) {
            List<Rule> noDeps = unordered.stream()
                    .filter(rule -> rule.dependsOf.isEmpty())
                    .toList();

            unordered.removeAll(noDeps);
            if (!unordered.isEmpty() && noDeps.isEmpty()) {
                throw new IllegalStateException("Boucle infinie !");
            }
            for (Rule rule : unordered) {
                rule.dependsOf.removeAll(noDeps);
            }

            ordered.addAll(noDeps);
            end = unordered.isEmpty();
        }
        return ordered;
    }

    public static boolean validateRuleOrder(List<Rule> orderedRules) {
        for (int i = 0; i < orderedRules.size(); i++) {
            for (int j = i + 1; j < orderedRules.size(); j++) {
                if (orderedRules.get(j).pageAfter == orderedRules.get(i).pageThatMustBeBefore) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return pageThatMustBeBefore + "|" + pageAfter;
    }
}
