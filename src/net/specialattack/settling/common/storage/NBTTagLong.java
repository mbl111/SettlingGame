
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTTagLong extends NBTTag {

    public long value;

    public NBTTagLong(String name) {
        super(name);
    }

    public NBTTagLong(String name, long base) {
        this(name);

        this.value = base;
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.value = input.readLong();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeLong(this.value);
    }

}
