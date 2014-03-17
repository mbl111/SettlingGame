
package net.specialattack.settling.client.world;

import java.io.File;

import net.specialattack.settling.common.Settling;
import net.specialattack.settling.common.world.Section;
import net.specialattack.settling.common.world.World;
import net.specialattack.settling.common.world.gen.WorldGenLayerFuzzyZoom;
import net.specialattack.settling.common.world.gen.WorldGenLayerIslands;
import net.specialattack.settling.common.world.gen.WorldGenLayerZoom;

public class WorldDemo extends World {

    private Section[] sections;

    //private Chunk[] chunks;

    public WorldDemo(File saveFolder) {
        super(saveFolder);

        this.genLayer = new WorldGenLayerIslands(1L, 10); // Island frequency
        this.genLayer = WorldGenLayerZoom.zoom(2000L, this.genLayer, 1); // Island size
        this.genLayer = WorldGenLayerFuzzyZoom.zoom(1000L, this.genLayer, 1); // Island randomness
        this.genLayer = WorldGenLayerZoom.zoom(2000L, this.genLayer, 4); // Island size

        this.genLayer.initGlobalSeed(100L);

        this.sections = new Section[(this.getMaxChunkXBorder() - this.getMinChunkXBorder()) * (this.getMaxChunkZBorder() - this.getMinChunkZBorder()) / 256];

        int widthX = (this.getMaxChunkXBorder() - this.getMinChunkXBorder()) / 16;
        int widthZ = (this.getMaxChunkZBorder() - this.getMinChunkZBorder()) / 16;
        for (int x = 0; x < widthX; x++) {
            for (int z = 0; z < widthZ; z++) {
                this.sections[x + z * widthX] = new Section(this, x + this.getMinChunkXBorder() / 16, z + this.getMinChunkZBorder() / 16);
                this.sections[x + z * widthX].generateChunks();
            }
        }
    }

    @Override
    public boolean isLimited() {
        return true;
    }

    @Override
    public int getMinChunkXBorder() {
        return -16;
    }

    @Override
    public int getMaxChunkXBorder() {
        return 16;
    }

    @Override
    public int getMinChunkZBorder() {
        return -16;
    }

    @Override
    public int getMaxChunkZBorder() {
        return 16;
    }

    @Override
    public Section getSection(int sectionX, int sectionZ) {
        if (sectionX < this.getMinChunkXBorder() || sectionX > this.getMaxChunkXBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }
        if (sectionZ < this.getMinChunkZBorder() || sectionZ > this.getMaxChunkZBorder()) {
            Settling.log.warning("Request for out of reach section.");
            return null;
        }
        int widthX = (this.getMaxChunkXBorder() - this.getMinChunkXBorder()) / 16;
        sectionX -= this.getMinChunkXBorder() / 16;
        sectionZ -= this.getMinChunkZBorder() / 16;
        return this.sections[sectionX + sectionZ * widthX];
    }

}
