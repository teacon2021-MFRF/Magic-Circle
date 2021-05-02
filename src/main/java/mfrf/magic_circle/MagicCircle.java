package mfrf.magic_circle;

import mfrf.magic_circle.registry_lists.Items;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static mfrf.magic_circle.MagicCircle.*;

@Mod(MOD_ID)
public class MagicCircle {
    public static final String MOD_ID = "magic_circle";
    public static final ItemGroup MAGIC_CIRCLE_RESOURCES = new CreativeTab();

    public MagicCircle() {
        MinecraftForge.EVENT_BUS.register(this);
        Items.ITEM_DEFERRED_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

}
