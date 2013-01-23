
package net.specialattack.settling.texture;

import java.awt.image.BufferedImage;

public class SubTexture {

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

    public int getStartU() {
        return this.startU;
    }

    public int getStartV() {
        return this.startV;
    }

    public int getEndU() {
        return this.endU;
    }

    public int getEndV() {
        return this.endV;
    }

    public int getWidth() {
        return this.endU - this.startU;
    }

    public int getHeight() {
        return this.endV - this.startV;
    }

    public StitchedTexture getParent() {
        return this.parent;
    }

    public void bindTexture() {
        this.parent.bindTexture();
    }

}
