package mfrf.magic_circle.magicutil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

public class Invoker implements INBTSerializable<CompoundNBT> {
    BlockPos pos;
    ResourceLocation dimension;
    ItemStack itemStack;
    PlayerEntity player;
    InvokerType invokerType;

    @Override
    public CompoundNBT serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {

    }

    public enum InvokerType {
        PLAYER, BLOCK, ITEMSTACK;
    }
}
