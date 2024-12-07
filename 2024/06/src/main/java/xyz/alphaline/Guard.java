package xyz.alphaline;

import java.nio.file.Files;
import java.nio.file.Path;

public class Guard {

    private int xPos;
    private int yPos;
    private final Map map;

    private Facing facing;

    public enum Facing {
        NORTH("^"),
        SOUTH("v"),
        EAST(">"),
        WEST("<");

        Facing(String rep) {
            representing = rep;
        }

        private final String representing;

        /**
         * La valeur de representing.
         * @return La valeur de representing
         */
        public String getRepresenting() {
            return representing;
        }

        public static Facing fromRepresentation(String rep) {
            for (Facing value : Facing.values()) {
                if (value.representing.equals(rep)) {
                    return value;
                }
            }

            return null;
        }
    }

    private Guard(int startX,
                 int startY,
                 Facing startFacing,
                 Map map)  {

        xPos = startX;
        yPos = startY;
        facing = startFacing;
        this.map = map;

        map.getGuardSittingTile(this).setVisitedFlag();
    }

    public static Guard getFromInput(String resPath, Map map) {
        try {
            Path input = Path.of(Map.class.getResource(resPath).toURI());
            String[][] mapArray = Files.lines(input)
                    .map(s -> s.split(""))
                    .toArray(String[][]::new);

            int xGuardPos = -1;
            int yGuardPos = -1;
            Facing facing = Facing.NORTH;

            for (int y = 0; y < mapArray.length; y++) {
                for (int x = 0; x < mapArray[y].length; x++) {
                    if (Facing.fromRepresentation(mapArray[y][x]) != null) {
                        xGuardPos = x;
                        yGuardPos = y;
                        facing = Facing.fromRepresentation(mapArray[y][x]);
                        break;
                    }
                }
                if (xGuardPos != -1) {
                    break;
                }
            }

            if (xGuardPos == -1) {
                throw new IllegalArgumentException("Guard not found in map !");
            }

            return new Guard(xGuardPos, yGuardPos, facing, map);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void generatePath() {
        boolean oob = false;

        while (!oob) {
            oob = !step();
            if (!oob) {
                map.getGuardSittingTile(this).setVisitedFlag();
            }
        }
    }

    private boolean step() {
        System.out.println("====================\n" + map);
        Tile nextTileSameFacing = map.getGuardNextTile(this);
        if (nextTileSameFacing == null) return false;

        switch (facing) {
            case NORTH -> {
                if (!nextTileSameFacing.getCrateState()) {
                    yPos--;
                } else {
                    facing = Facing.EAST;
                }
                break;
            }
            case SOUTH -> {
                if (!nextTileSameFacing.getCrateState()) {
                    yPos++;
                } else {
                    facing = Facing.WEST;
                }
            }
            case EAST -> {
                if (!nextTileSameFacing.getCrateState()) {
                    xPos++;
                } else {
                    facing = Facing.SOUTH;
                }
            }
            case WEST -> {
                if (!nextTileSameFacing.getCrateState()) {
                    xPos--;
                } else {
                    facing = Facing.NORTH;
                }
            }
        }

        return true;
    }

    /**
     * La valeur de facing.
     * @return La valeur de facing
     */
    public Facing getFacing() {
        return facing;
    }

    /**
     * La valeur de xPos.
     * @return La valeur de xPos
     */
    public int getxPos() {
        return xPos;
    }

    /**
     * La valeur de yPos.
     * @return La valeur de yPos
     */
    public int getyPos() {
        return yPos;
    }
}
