package mfrf.magic_circle.magicutil;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.UUID;

public class Invoker {
    public BlockPos beginPos;
    public ResourceLocation dimension;
    public ItemStack invokerStack;
    public UUID player;
    public MagicStream lastMagicStream;
    public World world;
    public InvokerType type;

    public Invoker(BlockPos beginPos, ResourceLocation dimension, ItemStack invokerStack, UUID player, MagicStream lastMagicStream, World world, InvokerType type) {
        this.beginPos = beginPos;
        this.dimension = dimension;
        this.invokerStack = invokerStack;
        this.player = player;
        this.lastMagicStream = lastMagicStream;
        this.world = world;
        this.type = type;
    }

    public enum InvokerType {
        PLAYER, BLOCK, EQUIPMENT, CHAIN;
    }
}
