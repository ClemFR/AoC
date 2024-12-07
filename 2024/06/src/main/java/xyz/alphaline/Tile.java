package xyz.alphaline;

public class Tile {

    private final boolean isCrate;

    private boolean guardVisited;

    private Tile(boolean crate) {
        isCrate = crate;
        guardVisited = false;
    }

    public static Tile getTileFromString(String in) {
        if (in.length() != 1) {
            throw new IllegalArgumentException("La chaine d'une case doit faire exactement 1 caractère de longueur !");
        }
            return new Tile(in.equals("#"));
    }

    public boolean getCrateState() {
        return isCrate;
    }

    public void setVisitedFlag() {
        guardVisited = true;
    }

    public boolean getVisitedState() {
        return guardVisited;
    }

}
