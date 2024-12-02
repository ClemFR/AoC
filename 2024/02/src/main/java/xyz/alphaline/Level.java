package xyz.alphaline;

import lombok.Getter;
import lombok.Setter;

import java.util.function.BiPredicate;

@Getter
@Setter
public class Level {

    public Level() {
        value = Integer.MIN_VALUE;
    }

    private int value;

    private Level next;
    private Level previous;

    public boolean recursiveValidateV1(BiPredicate<Integer, Integer> condition) {
        if (next == null) {
            return true;
        }

        if (value == Integer.MIN_VALUE) {
            throw new RuntimeException("Erreur MIN_VALUE pour un level");
        }

        int localValue = value;
        boolean value = condition.test(localValue, next.value);
        if (!value) {
            return false;
        } else {
            return next.recursiveValidateV1(condition);
        }
    }

    public Report.ReportResult recursiveValidateDampener(TriFunction<Integer, Integer, Boolean, Report.ReportResult> eval) {
        if (next == null) {
            return new Report.ReportResult(true, true);
        }

        if (value == Integer.MIN_VALUE) {
            throw new RuntimeException("Erreur MIN_VALUE pour un level");
        }

        int localValue = value;
        Report.ReportResult result = eval.apply(localValue, next.value, next.recursiveValidateDampener(eval).dampenerAvailable);
        if (!result.evalOk) {
            if (previous == null) {
                return new Report.ReportResult(true, false);
            } else if (next.next == null) {
                return eval.apply(previous.previous.value, previous.value, result.dampenerAvailable);
            } else {
                return eval.apply(previous.value, next.value, result.dampenerAvailable);
            }
        }

        return result;
    }
}
