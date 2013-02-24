
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class NBTTagCompound extends NBTTag {

    private HashMap<String, NBTTag> tags;

    public NBTTagCompound(String name) {
        super(name);

        this.tags = new HashMap<String, NBTTag>();
    }

    public NBTTag getTag(String name) {
        return this.tags.get(name);
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        byte type = (byte) 0;

        while ((type = input.readByte()) != 0) {
            String name = input.readUTF();

            NBTTag tag = NBTTag.getNewTag(type);

            tag.name = name;

            tag.read(input);

            this.tags.put(name, tag);
        }
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        Set<String> names = this.tags.keySet();

        String[] entries = names.toArray(new String[names.size()]);

        for (String name : entries) {
            NBTTag tag = this.tags.get(name);

            output.writeUTF(name);

            output.writeByte(NBTTag.getTypeId(tag.getClass()));

            tag.write(output);
        }
    }

}
