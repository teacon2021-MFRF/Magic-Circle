package mfrf.magic_circle.util;

import icyllis.modernui.math.Vector3;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;

public class NBTUtil {
    public static CompoundNBT writeVEC3(Vector3 vector3) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("x", vector3.x);
        compoundNBT.putFloat("y", vector3.y);
        compoundNBT.putFloat("z", vector3.z);
        return compoundNBT;
    }

    public static Vector3f readVec3f(CompoundNBT compoundNBT) {
        return new Vector3f(compoundNBT.getFloat("x"), compoundNBT.getFloat("y"), compoundNBT.getFloat("z"));
    }
}
