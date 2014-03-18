
package net.specialattack.settling.client.texture;

import java.awt.image.BufferedImage;

import net.specialattack.settling.common.texture.ISubTexture;

public class SubTexture implements ISubTexture {

    protected StitchedTexture parent;
    protected BufferedImage imageData;
    protected int startU;
    protected int startV;
    protected int endU;
    protected int endV;

    public SubTexture(StitchedTexture parent, BufferedImage imageData, int startU, int startV, int endU, int endV) {
        this.parent = parent;
        this.imageData = imageData;
        this.startU = startU;
        this.startV = startV;
        this.endU = endU;
        this.endV = endV;
    }

    @Override
    public int getFullStartU() {
        return this.startU;
    }

    @Override
    public int getFullStartV() {
        return this.startV;
    }

    @Override
    public int getFullEndU() {
        return this.endU;
    }

    @Override
    public int getFullEndV() {
        return this.endV;
    }

    @Override
    public float getStartU() {
        return (float) this.startU / (float) this.parent.getWidth();
    }

    @Override
    public float getStartV() {
        return (float) this.startV / (float) this.parent.getHeight();
    }

    @Override
    public float getEndU() {
        return (float) this.endU / (float) this.parent.getWidth();
    }

    @Override
    public float getEndV() {
        return (float) this.endV / (float) this.parent.getHeight();
    }

    @Override
    public int getWidth() {
        return this.endU - this.startU;
    }

    @Override
    public int getHeight() {
        return this.endV - this.startV;
    }

    @Override
    public StitchedTexture getParent() {
        return this.parent;
    }

    @Override
    public int bindTexture() {
        return this.parent.bindTexture();
    }

}
