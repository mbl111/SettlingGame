
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTTagByte extends NBTTag {

    public byte value;

    public NBTTagByte(String name) {
        super(name);
    }

    public NBTTagByte(String name, byte base) {
        this(name);

        this.value = base;
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.value = input.readByte();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeByte(value);
    }

}
