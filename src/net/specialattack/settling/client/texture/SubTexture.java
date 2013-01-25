
package net.specialattack.settling.client.texture;

import java.awt.image.BufferedImage;

import net.specialattack.settling.common.ISubTexture;

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
    public int getStartU() {
        return this.startU;
    }

    @Override
    public int getStartV() {
        return this.startV;
    }

    @Override
    public int getEndU() {
        return this.endU;
    }

    @Override
    public int getEndV() {
        return this.endV;
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
    public void bindTexture() {
        this.parent.bindTexture();
    }

}
