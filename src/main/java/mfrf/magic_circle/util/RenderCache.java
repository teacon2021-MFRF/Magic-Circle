package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.network.magic_model_sync.RequestMagicModelsData;
import mfrf.magic_circle.network.magic_model_sync.SendPack;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class RenderCache {
    private static final HashMap<UUID, HashMap<String, MagicCircleComponentBase<?>>> CACHED_RENDERING = new HashMap<>();


    private static HashMap<String, MagicCircleComponentBase<?>> getOrCreateRenderCache(UUID uuid) {
        if (!CACHED_RENDERING.containsKey(uuid)) {
            CACHED_RENDERING.put(uuid, new HashMap<>());
        }
        return CACHED_RENDERING.get(uuid);
    }

    @Nullable
    public static MagicCircleComponentBase<?> getRender(UUID uuid, String name) {
        HashMap<String, MagicCircleComponentBase<?>> renderCache = getOrCreateRenderCache(uuid);
        if (!renderCache.containsKey(name)) {
            RequestMagicModelsData.INSTANCE.send(PacketDistributor.SERVER.with(() -> null),
                    new SendPack(name, new CompoundNBT(), uuid, false));

            renderCache.put(name, CachedEveryThingForClient.getOrCreateModels(uuid).getOrDefault(name, new MagicModelBase(null)).getRender());
        }
        return renderCache.get(name);
    }
}
