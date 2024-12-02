package xyz.alphaline;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

@Getter
@Setter
public class Level {

    public Level() {
        value = Integer.MIN_VALUE;
    }

    private int value;

    private Level next;
    private Level previous;

    public boolean recursiveValidate(BiPredicate<Integer, Integer> condition) {
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
            return next.recursiveValidate(condition);
        }
    }
}
