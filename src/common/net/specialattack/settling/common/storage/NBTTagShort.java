
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTTagShort extends NBTTag {

    public short value;

    public NBTTagShort(String name) {
        super(name);
    }

    public NBTTagShort(String name, short base) {
        this(name);

        this.value = base;
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.value = input.readShort();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeShort(this.value);
    }

}
