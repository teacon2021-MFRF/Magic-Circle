package mfrf.magic_circle.registry_lists;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.vein.EndCrystalVein;
import mfrf.magic_circle.vein.NetherCrystalVein;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Features {

    public static final DeferredRegister<Feature<?>> REGISTER = DeferredRegister.create(ForgeRegistries.FEATURES, MagicCircle.MOD_ID);

    public static final RegistryObject<Feature<OreFeatureConfig>> END_CRYSTAL_VEIN = REGISTER.register("end_crystal_vein", EndCrystalVein::new);
    public static final RegistryObject<Feature<OreFeatureConfig>> NETHER_CRYSTAL_VEIN = REGISTER.register("nether_crystal_vein", NetherCrystalVein::new);
}
