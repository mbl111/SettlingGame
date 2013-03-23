
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class NBTTagString extends NBTTag {

    public String value;

    public NBTTagString(String name) {
        this(name, "");
    }

    public NBTTagString(String name, String base) {
        super(name);

        this.value = base;
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.value = input.readUTF();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeUTF(this.value);
    }

}
