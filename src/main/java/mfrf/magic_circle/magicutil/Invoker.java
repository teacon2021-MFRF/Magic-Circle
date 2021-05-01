package mfrf.magic_circle.magicutil;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.UUID;

public class Invoker implements INBTSerializable<CompoundNBT> {
    BlockPos pos;
    ResourceLocation dimension;
    ItemStack itemStack;
    UUID player;
    InvokerType invokerType;

    public Invoker(BlockPos pos, ResourceLocation dimension, ItemStack itemStack, UUID player, InvokerType invokerType) {
        this.pos = pos;
        this.dimension = dimension;
        this.itemStack = itemStack;
        this.player = player;
        this.invokerType = invokerType;
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
        if (itemStack != null) {
            compoundNBT.put("itemstack", itemStack.serializeNBT());
        }
        if (player != null) {
            compoundNBT.putUniqueId("uuid", player);
        }
        compoundNBT.putString("invoker_type", invokerType.name());
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
        if (compoundNBT.contains("itemstack")) {
            itemStack = ItemStack.read(compoundNBT.getCompound("itemstack"));
        }
        if (compoundNBT.contains("uuid")) {
            player = compoundNBT.getUniqueId("uuid");
        }
        invokerType = InvokerType.valueOf(compoundNBT.getString("invoker_type"));
    }

    public enum InvokerType {
        PLAYER, BLOCK, ITEMSTACK;
    }
}
