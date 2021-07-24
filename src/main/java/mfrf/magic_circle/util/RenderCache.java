package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.network.magic_model_request.RequestMagicModelsData;
import mfrf.magic_circle.network.magic_model_request.SendPack;
import mfrf.magic_circle.rendering.CircleObject;
import mfrf.magic_circle.rendering.LineObject;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@OnlyIn(Dist.CLIENT)
public class RenderCache {
    private static final HashMap<UUID, HashMap<String, MagicCircleComponentBase<?>>> CACHED_RENDERING = new HashMap<>();
    private static final HashMap<Class<? extends MagicNodeBase>, MagicCircleComponentBase<?>> cachedNodeMaps = new HashMap<>();

    static {
        cachedNodeMaps.put(ThrowBehaviorNode.class,
                new MagicCircleRenderBase(-1, 0, 0, 0).appendNextParallelComponents(
                        new CircleObject(0, 0, 0, 0, 20, 7).setAlpha(255).setColor(Colors.DRYSKY)
                                .appendNextParallelComponents(
                                        new LineObject(0, 0, 0, 0, 20)
                                                .point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close(),
                                        new LineObject(0, 0, 0, 0, 20)
                                                .point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close()
                                                .setRotation(new Quaternion(0, 180, 0, true))
                                ),
                        new CircleObject(2, 0, 0, 0, 18, 7 * 0.5f)
                                .setPositionOffset(new Vector3f(0, -1.5f, 0))
                                .setAlpha(255)
                                .setColor(Colors.DRYSKY),
                        new LineObject(0, 0, 0, 0, 18)
                                .setPositionOffset(new Vector3f(0, -1.5f, 0))
                                .point(0, 0f, 7 * 0.5f).point(-6.0523f * 0.5f, 0f, -3.517f * 0.5f).point(6.0523f * 0.5f, 0f, -3.517f * 0.5f).close(),
                        new LineObject(0, 0, 0, 0, 18)
                                .setPositionOffset(new Vector3f(0, -1.5f, 0))
                                .point(0, 0f, 7 * 0.5f).point(-6.0523f * 0.5f, 0f, -3.517f * 0.5f).point(6.0523f * 0.5f, 0f, -3.517f * 0.5f).close()
                                .setRotation(new Quaternion(0, 180, 0, true))
                ));

    }

    private static HashMap<String, MagicCircleComponentBase<?>> getOrCreateRenderCache(UUID uuid) {
        if (!CACHED_RENDERING.containsKey(uuid)) {
            CACHED_RENDERING.put(uuid, new HashMap<>());
        }
        return CACHED_RENDERING.get(uuid);
    }

    @Nullable
    public static MagicCircleComponentBase<?> getRender(UUID uuid, String name, World world) {
        HashMap<String, MagicCircleComponentBase<?>> renderCache = getOrCreateRenderCache(uuid);
        boolean flag = CachedEveryThingForClient.hasUpdate(uuid, name);
        if (!renderCache.containsKey(name) || flag) {
            RequestMagicModelsData.INSTANCE.send(PacketDistributor.SERVER.with(() -> null),
                    new SendPack(name, new CompoundNBT(), uuid, false));

            MagicModelBase put = CachedEveryThingForClient.requestModels(world, uuid).get(name);
            if (put != null) {
                ArrayList<MagicNodeBase> magicNodeBases = put.updateNodes();
                MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase();
                for (MagicNodeBase magicNodeBase : magicNodeBases) {
                    MagicCircleComponentBase<?> magicCircleComponentBase = cachedNodeMaps.get(magicNodeBase.getClass());
                    if (magicCircleComponentBase != null) {
                        magicCircleRenderBase.appendNextParallelComponent(magicCircleComponentBase);
                    }
                }
                renderCache.put(name, magicCircleRenderBase);

                if (flag) {
                    CachedEveryThingForClient.setUpdated(uuid, name);
                }

            }
        }
        return renderCache.get(name);
    }
}
