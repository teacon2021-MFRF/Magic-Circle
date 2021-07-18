package mfrf.magic_circle.events;


import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.Features;
import mfrf.magic_circle.vein.EndCrystalVein;
import mfrf.magic_circle.vein.NetherCrystalVein;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BiomeLoadingEventHandler {

    @SubscribeEvent
    public static void onLoad(BiomeLoadingEvent event) {
        BiomeGenerationSettingsBuilder generation = event.getGeneration();

        generation.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.configured(
                new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ORE_CRYSTAL.get().defaultBlockState(), 16))
                .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(4, 0, 32)))
                .countRandom(2)
        );

        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.NETHER_CRYSTAL_VEIN.get().configured(
                new OreFeatureConfig(NetherCrystalVein.TEST, Blocks.ORE_CRYSTAL.get().defaultBlockState(), 25))
                .decorated(Placement.CHANCE.configured(new ChanceConfig(1)))
                .countRandom(1)
        );

        generation.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.END_CRYSTAL_VEIN.get().configured(
                new OreFeatureConfig(EndCrystalVein.TEST, Blocks.ORE_CRYSTAL.get().defaultBlockState(), 1))
                .decorated(Placement.CHANCE.configured(new ChanceConfig(1)))
                .countRandom(1)
        );
    }
}
