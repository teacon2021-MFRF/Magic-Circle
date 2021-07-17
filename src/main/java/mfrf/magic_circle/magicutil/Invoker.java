package mfrf.magic_circle.magicutil;

import java.util.UUID;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class Invoker {
    public BlockPos beginPos;
    public DimensionType dimension;
    public ItemStack invokerStack;
    public UUID entity;
    public MagicStream lastMagicStream;
    public World world;
    public InvokerType type;

    public Invoker(BlockPos beginPos, DimensionType dimension, ItemStack invokerStack, UUID entity, MagicStream lastMagicStream, World world, InvokerType type) {
        this.beginPos = beginPos;
        this.dimension = dimension;
        this.invokerStack = invokerStack;
        this.entity = entity;
        this.lastMagicStream = lastMagicStream;
        this.world = world;
        this.type = type;
    }

    public Invoker copy() {
//        BlockPos copiedPos = null;
//        DimensionType dimensionType = null;
//        ItemStack stack = ItemStack.EMPTY;
//        UUID uuid = null;
//        MagicStream stream = null;
//        if (beginPos != null) {
//            copiedPos = beginPos;
//        }
//        if(dimension != null){
//            dimensionType = dimension;
//        }
//        if(!invokerStack.isEmpty()){
//            stack = invokerStack;
//        }
//        if(player != null){
//            uuid = player;
//        }
//        if(lastMagicStream != null){
//            stream = lastMagicStream;
//        }
        return new Invoker(beginPos, dimension, invokerStack, entity, lastMagicStream, world, type);
    }

    public enum InvokerType {
        ENTITY, BLOCK, EQUIPMENT, CHAIN, AUTO;
    }
}
