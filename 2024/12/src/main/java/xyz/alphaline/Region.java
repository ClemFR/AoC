package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private String type;

    private List<Plot> plots = new ArrayList<>();

    private int perimeter;

    private int sides;

    public Region(String t) {
        type = t;
    }

    public void addPlot(Plot p) {
        plots.add(p);
    }

    public void calculatePerimeter() {
        perimeter = plots.stream().mapToInt(Plot::getNbExposedSides).sum();
    }

    public void calculateSides() {
        sides = 0;
        List<Plot> ext = new ArrayList<>(plots.stream().filter(p -> p.getNbExposedSides() > 0).toList());
        while (!ext.isEmpty()) {
            Plot plot = ext.getFirst();

            List<Side> remainingSides = new ArrayList<>(plot.getExposedPlotSide());
            remainingSides.removeAll(plot.getUsedSides());
            if (remainingSides.isEmpty()) {
                throw new RuntimeException("DAFUQ ???");
            }

            List<Plot> adjacentOnSide = plot.getAdjacentOnSide(remainingSides.getFirst());
            sides += 1;
            ext.removeAll(adjacentOnSide.stream().filter(plot1 -> plot1.getExposedPlotSide().size() == plot1.getUsedSides().size()).toList());
        }

    }

    public boolean containsPlot(Plot p) {
        return plots.contains(p);
    }

    /**
     * La valeur de perimeter.
     * @return La valeur de perimeter
     */
    public int getPerimeter() {
        return perimeter;
    }

    public int getArea() {
        return plots.size();
    }

    public int getFullPrice() {
        return plots.size() * perimeter;
    }

    public int getDiscountPrice() {
        return plots.size() * sides;
    }

    @Override
    public String toString() {
        return type + " - " + sides + " --> " + getDiscountPrice();
    }
}
