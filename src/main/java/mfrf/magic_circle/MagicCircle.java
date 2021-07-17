package mfrf.magic_circle;

import static mfrf.magic_circle.MagicCircle.MOD_ID;

import mfrf.magic_circle.interfaces.IComfortableCapabilityStorage;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.network.magic_model_sync.RequestMagicModelsData;
import mfrf.magic_circle.registry_lists.*;
import mfrf.magic_circle.vein.EndCrystalVein;
import mfrf.magic_circle.vein.NetherCrystalVein;
import net.minecraft.item.ItemGroup;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(MOD_ID)
public class MagicCircle {
    public static final String MOD_ID = "magic_circle";
    public static final ItemGroup MAGIC_CIRCLE_RESOURCES = new CreativeTab();

    public MagicCircle() {
        MinecraftForge.EVENT_BUS.register(this);
        Items.ITEM_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Blocks.BLOCK_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        TileEntities.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Entities.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        JsonConfigs.JSONCONFIG_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        GuiContainers.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Features.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        Sounds.REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
