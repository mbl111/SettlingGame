
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTTagInteger extends NBTTag {

    public int value;

    public NBTTagInteger(String name) {
        super(name);
    }

    public NBTTagInteger(String name, int base) {
        this(name);

        this.value = base;
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.value = input.readInt();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeInt(value);
    }

}
