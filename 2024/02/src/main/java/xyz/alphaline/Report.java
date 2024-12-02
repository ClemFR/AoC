package xyz.alphaline;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class Report {

    private Level first;
    private Level last;

    public void addLevel(int level) {
        Level l = new Level();
        l.setValue(level);

        if (first == null) {
            first = l;
        } else {
            last.setNext(l);
            l.setPrevious(last);
        }

        last = l;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        Level current = first;

        while (current != null) {
            s.append(current.getValue()).append(" ");
            current = current.getNext();
        }

        return s.toString();
    }

    public boolean isCroissant() {
        int sum = 0;
        int count = 0;
        Level current = first;
        while (current != null) {
            sum += current.getValue();
            count++;
            current = current.getNext();
        }

        int avg = sum / count;
        if (avg == first.getValue() && first.getValue() == last.getValue()) {
            System.out.println("Erreur : " + toString());
            throw new ReactorExplodeException("R.I.P.");
        }
        return first.getValue() <= avg && avg <= last.getValue();

    }

    public boolean validate() {
        boolean result;

        try {
            if (isCroissant()) {
                // result = first.recursiveValidateV1(Report::validationCroissante);
                first.recursiveValidateDampener(Report::validationDampenerCroissante);
            } else {
                // result = first.recursiveValidateV1(Report::validationDecroissante);
                first.recursiveValidateDampener(Report::validationDampenerDecroissante);
            }

            System.out.println(toString());
            return true;
        } catch (ReactorExplodeException reactorExplodeException) {
            System.out.println("EXPLOSION : " + toString());
            return false;
        }
    }

    private static boolean validationCroissante(int i1, int i2) {
        int diff = i2 - i1;
        return diff >= 1 && diff <= 3;
    }

    private static boolean validationDecroissante(int i1, int i2) {
        int diff = i1 - i2;
        return diff >= 1 && diff <= 3;
    }

    public static ReportResult validationDampenerCroissante(int i1, int i2, boolean dampenerAvailable) {
        int diff = i2 - i1;
        if (diff >= 1 && diff <= 3) {
            return new ReportResult(true, dampenerAvailable);
        } else {
            if (!dampenerAvailable) {
                throw new ReactorExplodeException("R.I.P.");
            } else {
                return new ReportResult(false, false);
            }
        }
    }

    public static ReportResult validationDampenerDecroissante(int i1, int i2, boolean dampenerAvailable) {
        int diff = i1 - i2;

        if (diff >= 1 && diff <= 3) {
            return new ReportResult(true, dampenerAvailable);
        } else {
            if (!dampenerAvailable) {
                throw new ReactorExplodeException("R.I.P.");
            } else {
                return new ReportResult(false, false);
            }
        }
    }

    @AllArgsConstructor
    @Getter
    public static class ReportResult {
        boolean evalOk;
        boolean dampenerAvailable;
    }
}
