package xyz.alphaline;

import java.util.ArrayList;
import java.util.List;

public class DiskFile {

    private int fileId;

    private List<DiskFileBlock> blocks;

    public DiskFile(int fid) {
        fileId = fid;
        blocks = new ArrayList<>();
    }

    public void addFileBlock(DiskFileBlock block) {
        blocks.add(block);
        block.setParentFile(this);
    }

    /**
     * La valeur de fileId.
     * @return La valeur de fileId
     */
    public int getFileId() {
        return fileId;
    }
}
