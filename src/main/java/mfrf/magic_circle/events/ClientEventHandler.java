package mfrf.magic_circle.events;

import mfrf.magic_circle.block.magic_assemby_table.TERMagicModelAssemblyTable;
import mfrf.magic_circle.block.projector.TERProjector;
import mfrf.magic_circle.gui.assembly_table.AssemblyTableScreen;
import mfrf.magic_circle.gui.engraver_table.EngraverTableScreen;
import mfrf.magic_circle.gui.research_table.ResearchTableScreen;
import mfrf.magic_circle.registry_lists.Blocks;
import mfrf.magic_circle.registry_lists.GuiContainers;
import mfrf.magic_circle.registry_lists.TileEntities;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onClientEvent(FMLClientSetupEvent event) {
//        RenderingRegistry.registerEntityRenderingHandler(Entities.ENTITY_MAGIC_CIRCLE_BASE.get(), RenderMagicCircle::new);

        event.enqueueWork(() -> {
            ClientRegistry.bindTileEntityRenderer(TileEntities.PROJECTOR.get(), TERProjector::new);
            ClientRegistry.bindTileEntityRenderer(TileEntities.MAGIC_MODEL_ASSEMBLY_TABLE.get(), TERMagicModelAssemblyTable::new);
        });

        event.enqueueWork(() -> {
            ScreenManager.register(GuiContainers.RESEARCH_TABLE_CONTAINER.get(), ResearchTableScreen::new);
            ScreenManager.register(GuiContainers.ASSEMBLY_TABLE_CONTAINER.get(), AssemblyTableScreen::new);
            ScreenManager.register(GuiContainers.ENGRAVER_TABLE_CONTAINER.get(), EngraverTableScreen::new);
        });

        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(Blocks.MAGIC_MODEL_ASSEMBLY_TABLE.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(Blocks.ORE_CRYSTAL.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(Blocks.RESEARCH_TABLE.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(Blocks.PROJECTOR.get(),RenderType.translucent());
        });

//        RenderingRegistry.registerEntityRenderingHandler(Entities.DANMAKU_ENTITY.get(), RenderDanmakuEntity::new);
    }

}
