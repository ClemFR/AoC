package xyz.alphaline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AmphipodDisk {

    private final DiskFileBlock[] diskArray;
    private final List<DiskFile> filesList;

    private AmphipodDisk(DiskFileBlock[] disk, List<DiskFile> files) {
        diskArray = disk;
        filesList = files;
    }

    public static AmphipodDisk fromString(String in) {

        int[] input = Arrays.stream(in.split("")).mapToInt(Integer::parseInt).toArray();
        System.out.println("Parsing disk info...");

        // Compter le nombre de blocks que contient le disque
        int blocks = Arrays.stream(input).sum();

        List<DiskFile> files = new ArrayList<>();
        List<DiskFileBlock> allBlocks = new ArrayList<>();

        for (int ndx = 0; ndx < input.length; ndx++) {
            int currentVal = input[ndx];
            if (ndx % 2 == 0) {
                // On est sur un fichier
                DiskFile f = new DiskFile(files.size());
                files.add(f);
                for (int i = 0; i < currentVal; i++) {
                    DiskFileBlock fBlock = new DiskFileBlock();
                    allBlocks.add(fBlock);
                    f.addFileBlock(fBlock);
                }
            } else {
                // On est sur un espace
                for (int i = 0; i < currentVal; i++) {
                    DiskFileBlock fBlock = new DiskFileBlock();
                    fBlock.setHole(true);
                    allBlocks.add(fBlock);
                }
            }

            System.out.print("\r" + ndx + " / " + input.length);
        }

        for (int i = 0; i < allBlocks.size(); i++) {
            if (allBlocks.get(i) != null) {
                allBlocks.get(i).setDiskBlockLocation(i);
            }

            System.out.print("\r" + i + " / " + allBlocks.size());
        }

        System.out.println();
        return new AmphipodDisk(allBlocks.toArray(DiskFileBlock[]::new), files);
    }

    public void compact() {
        int cursor = diskArray.length - 1;
        System.out.println("Compacting disk...");

        long remainingHoles;
        do {
            DiskFileBlock toSwap = diskArray[cursor];
            if (!toSwap.isHole()) {
                // swap
                Optional<DiskFileBlock> first = Arrays.stream(diskArray).filter(DiskFileBlock::isHole).findFirst();
                if (first.isEmpty()) {
                    throw new RuntimeException("???");
                }

                DiskFileBlock tmp = first.get();
                int tmpNewLoc = toSwap.getDiskBlockLocation();
                int optimizedBlockNewLoc = tmp.getDiskBlockLocation();
                diskArray[optimizedBlockNewLoc] = toSwap;
                diskArray[tmpNewLoc] = tmp;
                tmp.setDiskBlockLocation(tmpNewLoc);
                toSwap.setDiskBlockLocation(optimizedBlockNewLoc);
            }

            cursor--;
            if (cursor < 0) {
                throw new RuntimeException("Negative cursor");
            }
            remainingHoles = Arrays.stream(diskArray).limit(cursor + 1).filter(DiskFileBlock::isHole).count();
            System.out.print("\r" + remainingHoles + " remaining holes");
        } while (remainingHoles != 0);

        System.out.println();
    }

    /**
     * La valeur de diskArray.
     * @return La valeur de diskArray
     */
    public DiskFileBlock[] getDiskArray() {
        return diskArray;
    }
}
