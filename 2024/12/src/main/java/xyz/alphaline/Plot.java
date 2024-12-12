package xyz.alphaline;

public class Plot {

    private final String type;

    private int xPos;

    private int yPos;

    private Farm farm;

    private Region region;

    public Plot(String t, Farm f) {
        type = t;
        farm = f;
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
}
