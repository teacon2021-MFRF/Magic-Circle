package mfrf.magic_circle;

import mfrf.magic_circle.registry_lists.*;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mfrf.magic_circle.MagicCircle.MOD_ID;

@Mod(MOD_ID)
public class MagicCircle {
    public static final String MOD_ID = "magic_circle";
    public static final ItemGroup MAGIC_CIRCLE_RESOURCES = new CreativeTab();

    public MagicCircle() {
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
