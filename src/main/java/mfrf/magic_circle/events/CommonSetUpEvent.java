package mfrf.magic_circle.events;

import mfrf.magic_circle.interfaces.IComfortableCapabilityStorage;
import mfrf.magic_circle.interfaces.IMagicalItem;
import mfrf.magic_circle.network.execute_render_data.ExecuteRenderDataSync;
import mfrf.magic_circle.network.gui_model_sync.SyncModelData;
import mfrf.magic_circle.network.knowledge_sync.RequestKnowledges;
import mfrf.magic_circle.network.magic_model_request.RequestMagicModelsData;
import mfrf.magic_circle.network.send_answer.SendAnswer;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetUpEvent {

    @SubscribeEvent
    public static void onSetUpEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(IMagicalItem.class, new IComfortableCapabilityStorage<IMagicalItem>(), () -> null);
        });

        RequestMagicModelsData.registerMessage();
        SendAnswer.registerMessage();
        RequestKnowledges.registerMessage();
        SyncModelData.registerMessage();
        ExecuteRenderDataSync.registerMessage();
    }
}
