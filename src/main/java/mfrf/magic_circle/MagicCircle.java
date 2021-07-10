package mfrf.magic_circle;

import static mfrf.magic_circle.MagicCircle.MOD_ID;

import mfrf.magic_circle.interfaces.IComfortableCapabilityStorage;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.network.magic_model_sync.RequestMagicModelsData;
import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.Entities;
import mfrf.magic_circle.registry_lists.GuiContainers;
import mfrf.magic_circle.registry_lists.Items;
import mfrf.magic_circle.registry_lists.JsonConfigs;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
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
    }

    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IMagicalItem.class, new IComfortableCapabilityStorage<IMagicalItem>(), () -> null);
        });

        RequestMagicModelsData.registerMessage();
    }

}
