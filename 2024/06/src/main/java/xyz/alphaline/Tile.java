package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Tile {

    private final boolean isCrate;

    private boolean guardVisited;

    private List<Guard.Facing> hittingSides;

    private boolean obstructedGenerateLoop = false;

    private int xPos;
    private int yPox;

    private Tile(boolean crate) {
        isCrate = crate;
        guardVisited = false;
        hittingSides = new ArrayList<>();
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

    public void resetVisitedFlag() {
        guardVisited = false;
    }

    public boolean getVisitedState() {
        return guardVisited;
    }

    /**
     * Modifie la valeur de xPos.
     * @param newxPos xPos
     */
    public void setxPos(final int newxPos) {
        xPos = newxPos;
    }

    /**
     * Modifie la valeur de yPox.
     * @param newyPox yPox
     */
    public void setyPox(final int newyPox) {
        yPox = newyPox;
    }

    public void addHittingSide(Guard.Facing side) {
        hittingSides.add(side);
    }

    /**
     * La valeur de hittingSides.
     * @return La valeur de hittingSides
     */
    public List<Guard.Facing> getHittingSides() {
        return hittingSides;
    }

    /**
     * La valeur de xPos.
     * @return La valeur de xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * La valeur de yPox.
     * @return La valeur de yPox
     */
    public int getyPos() {
        return yPox;
    }

    /**
     * La valeur de obstructedGenerateLoop.
     * @return La valeur de obstructedGenerateLoop
     */
    public boolean isObstructedGenerateLoop() {
        return obstructedGenerateLoop;
    }

    /**
     * Modifie la valeur de obstructedGenerateLoop.
     */
    public void setObstructedGenerateLoop() {
        obstructedGenerateLoop = true;
    }

    public void unsetObstructedGenerateLoop() {
        obstructedGenerateLoop = false;
        hittingSides.clear();
    }
}
