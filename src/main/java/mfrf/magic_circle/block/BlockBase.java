package mfrf.magic_circle.block;

import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistry;

public class BlockBase extends Block {
    public BlockBase(Properties p_i48440_1_, String name) {
        super(p_i48440_1_);
        setRegistryName(name);
    }
}
