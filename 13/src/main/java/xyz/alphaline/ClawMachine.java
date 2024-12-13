package xyz.alphaline;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Getter
@Setter
public class ClawMachine {

    public static final String PRIZE_RGX = "Prize: X=([0-9]+), Y=([0-9]+)";

    private ButtonMachine aButton;

    private ButtonMachine bButton;

    private int xTarget;

    private int yTarget;

    private int tokenCost;

    public ClawMachine(String input) {
        String[] split = input.split("\n");
        Pattern p = Pattern.compile(PRIZE_RGX);

        MatchResult matchResult = p.matcher(split[2]).results().toList().getFirst();
        xTarget = Integer.parseInt(matchResult.group(1));
        yTarget = Integer.parseInt(matchResult.group(2));

        aButton = ButtonMachine.fromString(split[0]);
        bButton = ButtonMachine.fromString(split[1]);

        tokenCost = -1;
        computeMinToken();
    }

    private void computeMinToken() {

        int cheapBtnPress = 0;
        int costBtnPress = 0;

        ButtonMachine costlyBtn;
        ButtonMachine cheapBtn;

        if (aButton.getTokenCost() > bButton.getTokenCost()) {
            costlyBtn = aButton;
            cheapBtn = bButton;
        } else {
            costlyBtn = bButton;
            cheapBtn = aButton;
        }

        int xPos = 0;
        int yPos = 0;

        do {
            if (cheapBtnPress > 100) {
                cheapBtnPress = 0;
                costBtnPress += 1;
            } else {
                cheapBtnPress += 1;
            }

            if (costBtnPress > 100) {
                return;
            }

            xPos = cheapBtn.getXForPress(cheapBtnPress) + costlyBtn.getXForPress(costBtnPress);
            yPos = cheapBtn.getYForPress(cheapBtnPress) + costlyBtn.getYForPress(costBtnPress);

        } while (xPos != xTarget || yPos != yTarget);

        tokenCost = cheapBtn.getTokenCostForPress(cheapBtnPress) + costlyBtn.getTokenCostForPress(costBtnPress);
    }

    public boolean isFaisable() {
        return tokenCost > -1;
    }

    @Override
    public String toString() {
        return "---- Claw Machine ----\n" + aButton + "\n" + bButton
                + "\n   --> Target | X: " + xTarget + " - Y:" + yTarget + " | Token cost: " + (tokenCost > -1 ? tokenCost : "IMPOSSIBLE !");
    }
}
