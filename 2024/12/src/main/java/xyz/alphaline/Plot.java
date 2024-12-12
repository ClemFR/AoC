package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;

public class Plot {

    private final String type;

    private int xPos;

    private int yPos;

    private Farm farm;

    private Region region;

    private List<Side> exposedPlotSide;

    private List<Side> usedSides;

    public Plot(String t, Farm f) {
        type = t;
        farm = f;
        exposedPlotSide = new ArrayList<>();
        usedSides = new ArrayList<>();
    }

    public void setCoordinates(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public boolean isInRegion() {
        return region != null;
    }

    public Region discoverRegion() {
        Region region = checkNeighbor(new Region(type));
        region.calculatePerimeter();
        region.calculateSides();

        return region;
    }

    private Region checkNeighbor(Region r) {
        r.addPlot(this);
        region = r;

        for (Plot adjacent : getAdjacents()) {
            if (adjacent != null && !adjacent.isInRegion() && adjacent.type.equals(type)) {
                adjacent.checkNeighbor(r);
            }
        }

        if (getTop() == null || !r.containsPlot(getTop())) {
            exposedPlotSide.add(Side.NORTH);
        }

        if (getBottom() == null || !r.containsPlot(getBottom())) {
            exposedPlotSide.add(Side.SOUTH);
        }

        if (getLeft() == null || !r.containsPlot(getLeft())) {
            exposedPlotSide.add(Side.EAST);
        }

        if (getRight() == null || !r.containsPlot(getRight())) {
            exposedPlotSide.add(Side.WEST);
        }

        return r;
    }

    public int getNbExposedSides() {
        int exposed = 0;

        for (Plot adjacent : getAdjacents()) {
            if (adjacent == null || adjacent.region != region) {
                exposed++;
            }
        }

        return exposed;
    }

    private Plot getLeft() {
        return farm.getAt(xPos - 1, yPos);
    }

    private Plot getRight() {
        return farm.getAt(xPos + 1, yPos);
    }

    private Plot getTop() {
        return farm.getAt(xPos, yPos - 1);
    }

    private Plot getBottom() {
        return farm.getAt(xPos, yPos + 1);
    }

    private Plot[] getAdjacents() {
        return new Plot[]{getTop(), getBottom(), getLeft(), getRight()};
    }

    public List<Plot> getAdjacentOnSide(Side s) {
        return recursiveAdjacentOnSameSide(s, new ArrayList<>());
    }

    private List<Plot> recursiveAdjacentOnSameSide(Side s, List<Plot> lte) {
        lte.add(this);
        usedSides.add(s);
        switch (s) {
            case EAST, WEST -> {
                if (getTop() != null && getTop().region == region && getTop().exposedPlotSide.contains(s) && !getTop().usedSides.contains(s)) {
                    getTop().recursiveAdjacentOnSameSide(s, lte);
                }

                if (getBottom() != null && getBottom().region == region && getBottom().exposedPlotSide.contains(s) && !getBottom().usedSides.contains(s)) {
                    getBottom().recursiveAdjacentOnSameSide(s, lte);
                }
            }

            case NORTH, SOUTH -> {
                if (getLeft() != null && getLeft().region == region && getLeft().exposedPlotSide.contains(s) && !getLeft().usedSides.contains(s)) {
                    getLeft().recursiveAdjacentOnSameSide(s, lte);
                }

                if (getRight() != null && getRight().region == region && getRight().exposedPlotSide.contains(s) && !getRight().usedSides.contains(s)) {
                    getRight().recursiveAdjacentOnSameSide(s, lte);
                }
            }
        }

        return lte;
    }

    /**
     * La valeur de exposedPlotSide.
     * @return La valeur de exposedPlotSide
     */
    public List<Side> getExposedPlotSide() {
        return exposedPlotSide;
    }

    /**
     * La valeur de usedSides.
     * @return La valeur de usedSides
     */
    public List<Side> getUsedSides() {
        return usedSides;
    }
}
