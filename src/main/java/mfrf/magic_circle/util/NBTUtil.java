package mfrf.magic_circle.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Vector3f;

public class NBTUtil {

    public static Vector3f readVec3f(CompoundNBT compoundNBT) {
        return new Vector3f(compoundNBT.getFloat("x"), compoundNBT.getFloat("y"), compoundNBT.getFloat("z"));
    }
}
