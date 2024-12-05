package xyz.alphaline;

public class Rule {

    private final int pageMustBeBefore;

    private final int pageAfter;

    public Rule(int before, int after) {
        pageMustBeBefore = before;
        pageAfter = after;
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

    public int getFirstPage() {
        return pageMustBeBefore;
    }

    public boolean applyToManual(Manual man) {
        Integer[] manualPages = man.getManualPages();

        for (Integer manualPage : manualPages) {
            if (manualPage.equals(pageMustBeBefore)) {
                return true;
            }
            if (manualPage.equals(pageAfter)) {
                return false;
            }
        }

        throw new RuntimeException("Page " + pageMustBeBefore + " not found in the manual " + man);
    }
}
