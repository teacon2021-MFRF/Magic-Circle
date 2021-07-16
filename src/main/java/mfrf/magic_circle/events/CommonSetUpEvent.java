package mfrf.magic_circle.events;

import mfrf.magic_circle.interfaces.IComfortableCapabilityStorage;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.network.magic_model_sync.RequestMagicModelsData;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetUpEvent {

    @SubscribeEvent
    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IMagicalItem.class, new IComfortableCapabilityStorage<IMagicalItem>(), () -> null);
        });

        RequestMagicModelsData.registerMessage();
    }
}