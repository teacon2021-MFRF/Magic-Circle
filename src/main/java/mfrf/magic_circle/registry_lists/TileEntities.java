package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.block.projector.TileProjector;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntities {
    public static final DeferredRegister<TileEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MagicCircle.MOD_ID);
    public static final RegistryObject<TileEntityType<TileProjector>> PROJECTOR = REGISTER.register("projector",()->TileEntityType.Builder.create(TileProjector::new,Blocks.PROJECTOR.get()).build(null));
}