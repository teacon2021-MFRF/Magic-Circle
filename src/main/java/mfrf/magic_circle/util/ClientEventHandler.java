package mfrf.magic_circle.util;

import mfrf.magic_circle.block.projector.TERProjector;
import mfrf.magic_circle.entity.magic_circle_base.RenderMagicCircle;
import mfrf.magic_circle.registry_lists.Entities;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(Entities.ENTITY_MAGIC_CIRCLE_BASE.get(), RenderMagicCircle::new);

        event.enqueueWork(() -> {
            ClientRegistry.bindTileEntityRenderer(TileEntities.PROJECTOR.get(), TERProjector::new);
        });

    }
}
