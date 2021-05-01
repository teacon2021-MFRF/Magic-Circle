package mfrf.magic_circle.magicutil;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.INBTSerializable;

public class Receiver implements INBTSerializable<CompoundNBT> {
    BlockPos pos;
    ResourceLocation dimension;
    Entity Entity;
    Vector3f vector3f;
    ReceiverType receiverType;

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {

    }

    public enum ReceiverType {
        BLOCK, VECTOR, ENTITY, DIMENSION;
    }
}
