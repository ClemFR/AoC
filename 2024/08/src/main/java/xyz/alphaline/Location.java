package xyz.alphaline;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private boolean isAntenna;

    private String antennaFrequency;

    private boolean isAntinode;

    private int xPos;

    private int yPos;

    public Location(String freq, int x, int y) {
        if (freq == null || freq.equals(".")) {
            isAntenna = false;
            antennaFrequency = null;
        } else {
            isAntenna = true;
            antennaFrequency = freq;
        }

        isAntinode = false;


        xPos = x;
        yPos = y;
    }

    @Override
    public String toString() {
        if (!isAntenna) {
            return isAntinode ? "." + "\u20df" : ".";
        } else {
            return isAntinode ? antennaFrequency + "\u20df" : antennaFrequency;
        }
    }

    public int distXBetween(Location a2) {
        return a2.xPos - this.xPos;
    }

    public int distYBetween(Location a2) {
        return a2.yPos - this.yPos;
    }
}
