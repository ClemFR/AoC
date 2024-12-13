package xyz.alphaline;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class ButtonMachine {

    public static final String BUTTON_RGX = "Button (.): X([-+]{1}[0-9]+), Y([-+]{1}[0-9]+)";

    private String buttonLabel;

    private int tokenCost;

    private int xIncrement;

    private int yIncrement;

    private ButtonMachine(String l, int t, int x, int y) {
        buttonLabel = l;
        tokenCost = t;
        xIncrement = x;
        yIncrement = y;
    }

    public static ButtonMachine fromString(String s) {
        Pattern pattern = Pattern.compile(BUTTON_RGX);
        MatchResult matchResult = pattern.matcher(s).results().toList().getFirst();

        int tokenCost;
        switch (matchResult.group(1)) {
            case "A" -> {
                tokenCost = 3;
            }
            case "B" -> {
                tokenCost = 1;
            }
            case null, default -> {
                throw new RuntimeException("Invalid button type !");
            }
        }

        return new ButtonMachine(matchResult.group(1), tokenCost, Integer.parseInt(matchResult.group(2)), Integer.parseInt(matchResult.group(3)));
    }

    @Override
    public String toString() {
        return "Button " + buttonLabel + " | X: " + xIncrement + " - Y: " + yIncrement;
    }

    public int getXForPress(int press) {
        return xIncrement * press;
    }

    public int getYForPress(int press) {
        return yIncrement * press;
    }

    public int getTokenCostForPress(int press) {
        return tokenCost * press;
    }
}
