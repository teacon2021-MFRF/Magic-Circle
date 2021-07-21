package mfrf.magic_circle.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import mfrf.magic_circle.util.RenderCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InWorldRenderingMagics {


    @SubscribeEvent
    public static void onWorldTick(RenderWorldLastEvent event) {
        MatrixStack matrixStack = event.getMatrixStack();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        for (Map.Entry<UUID, HashMap<String, Integer>> uuidHashMapEntry : CachedEveryThingForClient.getExecuteMap().entrySet()) {
            for (Map.Entry<String, Integer> stringIntegerEntry : uuidHashMapEntry.getValue().entrySet()) {
                ClientWorld level = Minecraft.getInstance().level;
                if (level != null) {
                    PlayerEntity playerByUUID = level.getPlayerByUUID(uuidHashMapEntry.getKey());
                    if (playerByUUID != null) {
                        boolean rendering = RenderCache.getRender(uuidHashMapEntry.getKey(), stringIntegerEntry.getKey()).rendering(stringIntegerEntry.getValue(), matrixStack, buffer, playerByUUID.getLookAngle(), playerByUUID.getUpVector(1.0f), new Vector3f(playerByUUID.position()), Minecraft.getInstance().getEntityRenderDispatcher());
                        if (rendering) {
                            uuidHashMapEntry.getValue().remove(stringIntegerEntry.getKey());
                        }
                    }
                }
                stringIntegerEntry.setValue(stringIntegerEntry.getValue() + 1);
            }
        }
    }
}
