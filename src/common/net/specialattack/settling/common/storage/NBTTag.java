
package net.specialattack.settling.common.storage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;

public abstract class NBTTag {

    public String name;

    public NBTTag(String name) {
        this.name = name;
    }

    public abstract void read(DataInputStream input) throws IOException;

    public abstract void write(DataOutputStream output) throws IOException;

    private static HashMap<Class<? extends NBTTag>, Byte> classToIdMap = new HashMap<Class<? extends NBTTag>, Byte>();
    private static HashMap<Byte, Class<? extends NBTTag>> idToClassMap = new HashMap<Byte, Class<? extends NBTTag>>();

    public static NBTTag getNewTag(int type) {
        Class<? extends NBTTag> clazz = idToClassMap.get(Integer.valueOf(type));

        if (clazz == null) {
            return null;
        }

        try {
            Constructor<? extends NBTTag> constructor = clazz.getConstructor(String.class);

            return constructor.newInstance((String) null);
        }
        catch (Exception e) {}

        return null;
    }

    public static byte getTypeId(Class<? extends NBTTag> clazz) {
        return classToIdMap.get(clazz);
    }

    public static void registerTag(Class<? extends NBTTag> clazz, byte type) {
        classToIdMap.put(clazz, type);
        idToClassMap.put(type, clazz);
    }

    static {
        registerTag(NBTTagByte.class, (byte) 1);
        registerTag(NBTTagShort.class, (byte) 2);
        registerTag(NBTTagInteger.class, (byte) 3);
        registerTag(NBTTagLong.class, (byte) 4);
        registerTag(NBTTagString.class, (byte) 5);
        registerTag(NBTTagCompound.class, (byte) 6);
    }

}
