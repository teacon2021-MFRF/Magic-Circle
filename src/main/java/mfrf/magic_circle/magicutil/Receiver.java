package mfrf.magic_circle.magicutil;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class Receiver implements INBTSerializable<CompoundNBT> {
    public BlockPos pos;
    public ResourceLocation dimension;
    public Vector3f vector3f;
    public ReceiverType receiverType;

    public Receiver(BlockPos pos, ResourceLocation dimension, Vector3f vector3f, ReceiverType receiverType) {
        this.pos = pos;
        this.dimension = dimension;
        this.vector3f = vector3f;
        this.receiverType = receiverType;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (pos != null) {
            compoundNBT.put("pos", NBTUtil.writeBlockPos(pos));
        }
        if (dimension != null) {
            compoundNBT.putString("dimension", dimension.toString());
        }
        if (vector3f != null) {
            compoundNBT.put("vec", mfrf.magic_circle.util.NBTUtil.writeVEC3f(vector3f));
        }
        compoundNBT.putString("receive_type", receiverType.name());
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("pos")) {
            pos = NBTUtil.readBlockPos(compoundNBT.getCompound("pos"));
        }
        if (compoundNBT.contains("dimension")) {
            dimension = ResourceLocation.tryCreate(compoundNBT.getString("dimension"));
        }
        if (compoundNBT.contains("vec")) {
            vector3f = mfrf.magic_circle.util.NBTUtil.readVec3f(compoundNBT.getCompound("vec"));
        }
        receiverType = ReceiverType.valueOf(compoundNBT.getString("receive_type"));
    }

    public enum ReceiverType {
        BLOCK, VECTOR, ENTITY, DIMENSION;
    }
}
