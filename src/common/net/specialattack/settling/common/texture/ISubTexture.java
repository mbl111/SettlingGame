
package net.specialattack.settling.common.texture;

public interface ISubTexture {
    public abstract int getFullStartU();

    public abstract int getFullStartV();

    public abstract int getFullEndU();

    public abstract int getFullEndV();

    public abstract float getStartU();

    public abstract float getStartV();

    public abstract float getEndU();

    public abstract float getEndV();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract IStitchedTexture getParent();

    public abstract int bindTexture();
}
