package xyz.alphaline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Equation {

    private final long goal;
    private final List<Long> numbers;
    private final List<OperatorsEnum> operators = new ArrayList<>();

    private boolean equationOk;

    private Equation(long goal, ArrayList<Long> numbers) {
        this.goal = goal;
        this.numbers = numbers;
        equationOk = false;

        resolve();
    }

    public static Equation fromString(String input) {
        String[] goalNumbers = input.split(":");
        List<Long> numbers = Arrays.stream(goalNumbers[1].split(" "))
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();

        return new Equation(Long.parseLong(goalNumbers[0]), new ArrayList<>(numbers));
    }

    private void resolve() {
        List<List<OperatorsEnum>> allCombinaisons = recursiveGenerateOpsList(null, numbers.size() - 1);
        for (List<OperatorsEnum> ops : allCombinaisons) {
            if (goal == eval(ops)) {
                operators.addAll(ops);
                equationOk = true;
                return;
            }
        }
    }

    private long eval(List<OperatorsEnum> combinaison) {
        List<Long> nums = new ArrayList<>(numbers);
        List<OperatorsEnum> ops = new ArrayList<>(combinaison);

        long sum = nums.removeFirst();
        while (!nums.isEmpty()) {
            switch (ops.removeFirst()) {
                case ADD -> {
                    sum = sum + nums.removeFirst();
                }
                case MULTIPLY -> {
                    sum = sum * nums.removeFirst();
                }
            }
        }

        return sum;
    }

    public static List<List<OperatorsEnum>> recursiveGenerateOpsList(List<List<OperatorsEnum>> toFill, int opsSize) {
        if (opsSize == 0) {
            return toFill;
        }
        if (toFill == null) {
            List<OperatorsEnum> lte1 = new ArrayList<>(List.of(OperatorsEnum.ADD));
            List<OperatorsEnum> lte2 = new ArrayList<>(List.of(OperatorsEnum.MULTIPLY));

            ArrayList<List<OperatorsEnum>> lists = new ArrayList<>(new ArrayList<>(List.of(lte1, lte2)));
            return recursiveGenerateOpsList(lists, opsSize - 1);
        } else {
            ArrayList<List<OperatorsEnum>> newOps = new ArrayList<>();

            for (OperatorsEnum value : OperatorsEnum.values()) {
                for (List<OperatorsEnum> operatorsEnums : toFill) {
                    ArrayList<OperatorsEnum> clone = (ArrayList<OperatorsEnum>) ((ArrayList<OperatorsEnum>) operatorsEnums).clone();
                    clone.add(value);
                    newOps.add(clone);
                }
            }

            return recursiveGenerateOpsList(newOps, opsSize - 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        if (!equationOk) {
            sb.append("❌ - ");
            sb.append(goal).append(" = ");
            numbers.forEach(integer -> sb.append(integer).append(" "));

        } else {
            sb.append("☑️ - ");
            sb.append(goal).append(" = ");
            for (int i = 0; i < numbers.size(); i++) {
                sb.append(numbers.get(i)).append(" ");
                if (i < numbers.size() - 1) {
                    sb.append(operators.get(i).str).append(" ");
                }
            }
        }
        return sb.toString();
    }

    public enum OperatorsEnum {
        ADD("+"),
        MULTIPLY("*");

        public final String str;

        OperatorsEnum(String str) {
            this.str = str;
        }
    }

    /**
     * La valeur de equationOk.
     * @return La valeur de equationOk
     */
    public boolean isEquationOk() {
        return equationOk;
    }

    /**
     * La valeur de goal.
     * @return La valeur de goal
     */
    public long getGoal() {
        return goal;
    }
}
