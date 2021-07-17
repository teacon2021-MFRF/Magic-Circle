package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.magic_assemby_table.TileMagicModelAssemblyTable;
import mfrf.magic_circle.block.magic_engraver.TileMagicEngraver;
import mfrf.magic_circle.block.projector.TileProjector;
import mfrf.magic_circle.block.research_table.TileResearchTable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntities {
    public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MagicCircle.MOD_ID);
    public static final RegistryObject<TileEntityType<TileProjector>> PROJECTOR = REGISTER.register("projector", () -> TileEntityType.Builder.of(TileProjector::new, Blocks.PROJECTOR.get()).build(null));
    public static final RegistryObject<TileEntityType<TileResearchTable>> RESEARCH_TABLE = REGISTER.register("research_table", () -> TileEntityType.Builder.of(TileResearchTable::new, Blocks.RESEARCH_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<TileMagicModelAssemblyTable>> MAGIC_MODEL_ASSEMBLY_TABLE = REGISTER.register("magic_model_assembly_table", () -> TileEntityType.Builder.of(TileMagicModelAssemblyTable::new, Blocks.MAGIC_MODEL_ASSEMBLY_TABLE.get()).build(null));
    public static final RegistryObject<TileEntityType<TileMagicEngraver>> MAGIC_ENGRAVER_TABLE = REGISTER.register("magic_engraver_table", () -> TileEntityType.Builder.of(TileMagicEngraver::new, Blocks.MAGIC_ENGRAVER_TABLE.get()).build(null));
}
