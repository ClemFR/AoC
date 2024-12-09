package xyz.alphaline;

public class DiskFileBlock {

    private DiskFile parentFile;

    private int diskBlockLocation;

    private boolean isHole = false;


    public void setParentFile(DiskFile parent) {
        parentFile = parent;
    }

    public void setDiskBlockLocation(int loc) {
        diskBlockLocation = loc;
    }

    public void setHole(boolean hole) {
        isHole = hole;
    }

    /**
     * La valeur de isHole.
     * @return La valeur de isHole
     */
    public boolean isHole() {
        return isHole;
    }

    /**
     * La valeur de diskBlockLocation.
     * @return La valeur de diskBlockLocation
     */
    public int getDiskBlockLocation() {
        return diskBlockLocation;
    }

    public DiskFile getParentFile() {
        return parentFile;
    }
}
