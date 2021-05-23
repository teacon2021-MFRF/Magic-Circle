package mfrf.magic_circle.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;

public class NBTUtil {
    public static CompoundNBT writeVEC3f(Vector3f vector3f) {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("x", vector3f.x());
        compoundNBT.putFloat("y", vector3f.y());
        compoundNBT.putFloat("z", vector3f.z());
        return compoundNBT;
    }

    public static Vector3f readVec3f(CompoundNBT compoundNBT) {
        return new Vector3f(compoundNBT.getFloat("x"), compoundNBT.getFloat("y"), compoundNBT.getFloat("z"));
    }
}
