package mfrf.magic_circle.magicutil;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class Invoker {
    public BlockPos beginPos;
    public DimensionType dimension;
    public ItemStack invokerStack;
    public UUID player;
    public MagicStream lastMagicStream;
    public World world;
    public InvokerType type;

    public Invoker(BlockPos beginPos, DimensionType dimension, ItemStack invokerStack, UUID player, MagicStream lastMagicStream, World world, InvokerType type) {
        this.beginPos = beginPos;
        this.dimension = dimension;
        this.invokerStack = invokerStack;
        this.player = player;
        this.lastMagicStream = lastMagicStream;
        this.world = world;
        this.type = type;
    }

    public enum InvokerType {
        PLAYER, BLOCK, EQUIPMENT, CHAIN, AUTO;
    }
}
