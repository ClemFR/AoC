package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;

public class Region {

    private String type;

    private List<Plot> plots = new ArrayList<>();

    private int perimeter;

    public Region(String t) {
        type = t;
    }

    public void addPlot(Plot p) {
        plots.add(p);
    }

    public void calculatePerimeter() {
        perimeter = plots.stream().mapToInt(Plot::getNbExposedSides).sum();
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

    public int getPrice() {
        return plots.size() * perimeter;
    }

    @Override
    public String toString() {
        return type + " - " + plots.size() + " * " + perimeter + " = " + getPrice();
    }
}
