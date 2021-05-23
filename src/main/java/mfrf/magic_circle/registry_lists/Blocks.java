package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.BlockBase;
import mfrf.magic_circle.block.projector.BlockProjector;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MagicCircle.MOD_ID);

    public static final RegistryObject<Block> BLOCK_MAGIC_CRYSTAL = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "block_magic_crystal");
    public static final RegistryObject<Block> MAGIC_MEDIUM = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "magic_medium");

    public static final RegistryObject<Block> PROJECTOR = registryObject(new BlockProjector(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "projector");

    public static RegistryObject<Block> registryObject(Block block, Item.Properties itemProperties, String name) {
        Items.registerObject(new BlockItem(block, itemProperties), name);
        return BLOCK_DEFERRED_REGISTER.register(name, () -> block);
    }
}
