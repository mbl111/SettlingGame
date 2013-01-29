
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.HashMap;

public abstract class NBTTag {

    public abstract void read(DataInputStream input);

    public abstract void write(DataOutputStream output);

    private static HashMap<Class<? extends NBTTag>, Byte> classToIdMap = new HashMap<Class<? extends NBTTag>, Byte>();
    private static HashMap<Byte, Class<? extends NBTTag>> idToClassMap = new HashMap<Byte, Class<? extends NBTTag>>();

    public static void registerTag(Class<? extends NBTTag> clazz, byte type) {
        classToIdMap.put(clazz, type);
        idToClassMap.put(type, clazz);
    }

}
