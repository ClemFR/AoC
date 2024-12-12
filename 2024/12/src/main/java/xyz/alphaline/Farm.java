package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Farm {

    private final Plot[][] plotMap;

    public Farm(Path in) {
        try {
            Plot[][] array = Files.lines(in)
                    .map(s -> s.split(""))
                    .map(strings -> Arrays.stream(strings).map(s -> new Plot(s, this)).toArray(Plot[]::new))
                    .toArray(Plot[][]::new);

            for (int y = 0; y < array.length; y++) {
                for (int x = 0; x < array[y].length; x++) {
                    array[y][x].setCoordinates(x, y);
                }
            }

            plotMap = array;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Region> getRegions() {

        boolean end = false;
        List<Region> regs = new ArrayList<>();

        do {
            Optional<Plot> first = Arrays.stream(plotMap)
                    .flatMap(Arrays::stream)
                    .filter(p -> !p.isInRegion())
                    .findFirst();

            if (first.isPresent()) {
                regs.add(first.get().discoverRegion());
            } else {
                end = true;
            }
        } while (!end);

        return regs;
    }

    public Plot getAt(int x, int y) {
        if (y < 0 || y >= plotMap.length) {
            return null;
        }
        if (x < 0 || x >= plotMap[y].length) {
            return null;
        }

        return plotMap[y][x];
    }

}
