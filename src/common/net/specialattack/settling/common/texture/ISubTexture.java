
package net.specialattack.settling.common.texture;

public interface ISubTexture {
    public abstract int getStartU();

    public abstract int getStartV();

    public abstract int getEndU();

    public abstract int getEndV();

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract IStitchedTexture getParent();

    public abstract int bindTexture();
}
