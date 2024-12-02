package xyz.alphaline;


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

    public boolean validate() {
        boolean result;

        if (first.getValue() < first.getNext().getValue()) {
            result = first.recursiveValidate(Report::validationCroissante);
        } else {
            result = first.recursiveValidate(Report::validationDecroissante);
        }

        if (result) {
            System.out.println(toString());
        }
        return result;
    }

    private static boolean validationCroissante(int i1, int i2) {
        int diff = i2 - i1;
        return diff >= 1 && diff <= 3;
    }

    private static boolean validationDecroissante(int i1, int i2) {
        int diff = i1 - i2;
        return diff >= 1 && diff <= 3;
    }
}
