package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.BlockBase;
import mfrf.magic_circle.block.TestBlock;
import mfrf.magic_circle.block.magic_construct_table.BlockMagicModelConstructTable;
import mfrf.magic_circle.block.projector.BlockProjector;
import mfrf.magic_circle.block.research_table.ResearchTable;
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

    // decorate blocks
    public static final RegistryObject<Block> BLOCK_MAGIC_CRYSTAL = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "block_magic_crystal");
    public static final RegistryObject<Block> MAGIC_CRYSTAL_TUNNEL = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "magic_crystal_tunnel");

    // tileentities
    public static final RegistryObject<Block> PROJECTOR = registryObject(new BlockProjector(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "projector");
    public static final RegistryObject<Block> RESEARCH_TABLE = registryObject(new ResearchTable(AbstractBlock.Properties.of(Material.WOOD)), Items.DEFAULT_ITEM_PROPERTY, "research_table");
    public static final RegistryObject<Block> MAGIC_MODEL_CONSTRUCT_TABLE = registryObject(new BlockMagicModelConstructTable(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "magic_model_construct_table");

    //technical block
    public static final RegistryObject<Block> TEST_BLOCK = registryObject(new TestBlock(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "test_block");

    public static RegistryObject<Block> registryObject(Block block, Item.Properties itemProperties, String name) {
        Items.registerObject(new BlockItem(block, itemProperties), name);
        return BLOCK_DEFERRED_REGISTER.register(name, () -> block);
    }
}
