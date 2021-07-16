package mfrf.magic_circle.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.network.magic_model_sync.RequestMagicModelsData;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InWorldRenderingMagics {
    public static final HashMap<UUID, HashMap<String, Integer>> executeMap = new HashMap<>();

    public static HashMap<String, Integer> getOrCreate(UUID uuid) {
        if (!executeMap.containsKey(uuid)) {
            executeMap.put(uuid, new HashMap<>());
        }
        return executeMap.get(uuid);
    }

    @SubscribeEvent
    public static void onWorldTick(RenderWorldLastEvent event) {
        MatrixStack matrixStack = event.getMatrixStack();
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        for (Map.Entry<UUID, HashMap<String, Integer>> uuidHashMapEntry : executeMap.entrySet()) {
            for (Map.Entry<String, Integer> stringIntegerEntry : uuidHashMapEntry.getValue().entrySet()) {
                ClientWorld level = Minecraft.getInstance().level;
                if (level != null) {
                    PlayerEntity playerByUUID = level.getPlayerByUUID(uuidHashMapEntry.getKey());
                    if (playerByUUID != null) {
                        boolean rendering = RequestMagicModelsData.getRender(uuidHashMapEntry.getKey(), stringIntegerEntry.getKey()).rendering(stringIntegerEntry.getValue(), matrixStack, buffer, playerByUUID.getLookAngle(), playerByUUID.getUpVector(1.0f), new Vector3f(playerByUUID.position()), Minecraft.getInstance().getEntityRenderDispatcher());
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
