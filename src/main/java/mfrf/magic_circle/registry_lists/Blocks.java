package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.BlockBase;
import mfrf.magic_circle.block.TestBlock;
import mfrf.magic_circle.block.TestSender;
import mfrf.magic_circle.block.VerifyButton;
import mfrf.magic_circle.block.magic_assemby_table.BlockMagicModelAssemblyTable;
import mfrf.magic_circle.block.magic_engraver.BlockMagicEngraver;
import mfrf.magic_circle.block.projector.BlockProjector;
import mfrf.magic_circle.block.research_table.ResearchTable;
import mfrf.magic_circle.block.resources.MagicCrystalOre;
import mfrf.magic_circle.block.technical_blocks.BlockRune;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {
    public static final DeferredRegister<Block> BLOCK_DEFERRED_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, MagicCircle.MOD_ID);

    // decorate blocks
    public static final RegistryObject<Block> BLOCK_MAGIC_CRYSTAL = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "block_magic_crystal");
    public static final RegistryObject<Block> MAGIC_CRYSTAL_TUNNEL = registryObject(new BlockBase(AbstractBlock.Properties.of(Material.METAL)), Items.DEFAULT_ITEM_PROPERTY, "magic_crystal_tunnel");

    // tileentities
    public static final RegistryObject<Block> PROJECTOR = registryObject(new BlockProjector(AbstractBlock.Properties.of(Material.METAL).noOcclusion().isViewBlocking((blockState, iBlockReader, blockPos) -> true)), Items.DEFAULT_ITEM_PROPERTY, "projector");
    public static final RegistryObject<Block> RESEARCH_TABLE = registryObject(new ResearchTable(AbstractBlock.Properties.of(Material.WOOD).noOcclusion().harvestTool(ToolType.AXE).strength(3f, 1.3f)), Items.DEFAULT_ITEM_PROPERTY, "research_table");
    public static final RegistryObject<Block> MAGIC_MODEL_ASSEMBLY_TABLE = registryObject(new BlockMagicModelAssemblyTable(AbstractBlock.Properties.of(Material.METAL).dynamicShape().noOcclusion().strength(4f, 6f).harvestTool(ToolType.PICKAXE).harvestLevel(2)), Items.DEFAULT_ITEM_PROPERTY, "assembly_table");
    public static final RegistryObject<Block> MAGIC_ENGRAVER_TABLE = registryObject(new BlockMagicEngraver(AbstractBlock.Properties.of(Material.METAL).dynamicShape().noOcclusion().strength(4f, 6f).harvestTool(ToolType.PICKAXE).harvestLevel(2)), Items.DEFAULT_ITEM_PROPERTY, "magic_engraver");

    //technical block
    public static final RegistryObject<Block> TEST_BLOCK = registryObject(new TestBlock(AbstractBlock.Properties.of(Material.METAL).noOcclusion()), Items.DEFAULT_ITEM_PROPERTY, "test_block");
    public static final RegistryObject<Block> RUNE = BLOCK_DEFERRED_REGISTER.register("rune", () -> new BlockRune(AbstractBlock.Properties.of(Material.METAL).noOcclusion().harvestLevel(4)));
    public static final RegistryObject<Block> ORE_CRYSTAL = registryObject(new MagicCrystalOre(AbstractBlock.Properties.of(Material.GLASS).harvestTool(ToolType.PICKAXE).harvestLevel(2).strength(30, 30).noOcclusion()), Items.DEFAULT_ITEM_PROPERTY, "magic_crystal_ore");
    public static final RegistryObject<Block> TEST_SENDER = registryObject(new TestSender(AbstractBlock.Properties.of(Material.METAL).strength(30, 30).harvestLevel(2).noOcclusion()), Items.DEFAULT_ITEM_PROPERTY, "test_sender");

    //stuff
    public static final RegistryObject<Block> VERIFY_BUTTON = registryObject(new VerifyButton(AbstractBlock.Properties.of(Material.WOOD).dynamicShape().harvestLevel(1).strength(30, 30)), Items.DEFAULT_ITEM_PROPERTY, "verify_button");

    public static RegistryObject<Block> registryObject(Block block, Item.Properties itemProperties, String name) {
        Items.registerObject(new BlockItem(block, itemProperties), name);
        return BLOCK_DEFERRED_REGISTER.register(name, () -> block);
    }
}
