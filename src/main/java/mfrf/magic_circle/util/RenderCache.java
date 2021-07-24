package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.rendering.CircleObject;
import mfrf.magic_circle.rendering.LineObject;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
                new MagicCircleRenderBase().appendNextParallelComponents(
                        new CircleObject(0, 0, 0, 0, 20, 7).setAlpha(255).setColor(Colors.DRYSKY)
                                .appendNextParallelComponents(
                                        new LineObject(0, 0, 0, 0, 20)
                                                .point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close(),
                                        new LineObject(0, 0, 0, 0, 20)
                                                .point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close()
                                                .setRotation(t -> new Quaternion(0, 180, 0, true))
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
                                .setRotation(t -> new Quaternion(0, 180, 0, true))
                ));
        //===============================================================================================================================
//        Vector3f offsetBase = new Vector3f(0, 0, 0);
//
//        Vector3f o1 = offsetBase.copy();
//        o1.add(16, 0, 0);
//        Vector3f o2 = offsetBase.copy();
//        o2.add(0, 0, 24);
//        Vector3f o3 = offsetBase.copy();
//        o3.add(-32, 0, 0);
//        Vector3f o4 = offsetBase.copy();
//        o4.add(0, 0, -40);
//        Vector3f o5 = offsetBase.copy();
//        o5.add(48, 0, 0);
//        Vector3f o6 = offsetBase.copy();
//        o6.add(0, 0, -56);
//        Vector3f o7 = offsetBase.copy();
//        o7.add(-64, 0, 0);
//        Vector3f o8 = offsetBase.copy();
//        o8.add(0, 0, 72);
//
//        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(1, 0, 0, 0, 5040);
//        magicCircleRenderBase.appendNextParallelComponents(
//                new CoordinatesObject(0, 0, 0, 0, 8, new MagicCircleComponentBase.Coordinates(
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.X, new MagicCircleComponentBase.Line(-72, 72), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Y, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Z, new MagicCircleComponentBase.Line(-72, 72), new MagicCircleComponentBase.BasicArrowHead(1), 1)
////                        new PositionExpression("t", "math.pow(t,2) + 1", null, 0.05f, 100, 0),
//                )).setPositionOffset(offsetBase),
//                new CircleObject(0, 8, 0, 0, 200, 8).setPositionOffset(offsetBase),
//                new CircleObject(0, 8, 0, 8, 200, 8).setPositionOffset(offsetBase),
//                new CircleObject(0, 4, 0, 0, 200, 7).setPositionOffset(offsetBase),
//                new CircleObject(0, 4, 0, 4, 200, 7).setPositionOffset(offsetBase),
//
//                new CircleObject(0, 0, 0, 0, 200, 16).setPositionOffset(offsetBase).setColor(Colors.KUNDI),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o1).setRotation(t -> new Quaternion(0, t % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o1).setRotation(t -> new Quaternion(0, t % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 24).setPositionOffset(offsetBase).setColor(Colors.DUIZE),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o2).setRotation(t -> new Quaternion(0, t / 2 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o2).setRotation(t -> new Quaternion(0, t / 2 % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 32).setPositionOffset(offsetBase).setColor(Colors.LIHUO),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o3).setRotation(t -> new Quaternion(0, (t / 4) % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o3).setRotation(t -> new Quaternion(0, (t / 4) % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 40).setPositionOffset(offsetBase).setColor(Colors.GENSHAN),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o4).setRotation(t -> new Quaternion(0, t / 6 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o4).setRotation(t -> new Quaternion(0, t / 6 % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 48).setPositionOffset(offsetBase).setColor(Colors.SUNDAE),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o5).setRotation(t -> new Quaternion(0, t / 8 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o5).setRotation(t -> new Quaternion(0, t / 8 % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 56).setPositionOffset(offsetBase).setColor(Colors.KANSHUI),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o6).setRotation(t -> new Quaternion(0, t / 10 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o6).setRotation(t -> new Quaternion(0, t / 10 % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 64).setPositionOffset(offsetBase).setColor(Colors.THUNDER),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o7).setRotation(t -> new Quaternion(0, t / 12 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o7).setRotation(t -> new Quaternion(0, t / 12 % 360, 0, true)),
//
//                new CircleObject(0, 0, 0, 0, 200, 72).setPositionOffset(offsetBase).setColor(Colors.DRYSKY),
//                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o8).setRotation(t -> new Quaternion(0, t / 14 % 360, 0, true)),
//                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o8).setRotation(t -> new Quaternion(0, t / 14 % 360, 0, true))
//        );
////                new CircleObject(0, 8, 0, 8, 200, 8).setPositionOffset(offsetBase)
//        //===============================================================================================================================
//        cachedNodeMaps.put(ThrowBehaviorNode.class, magicCircleRenderBase);

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
//            RequestMagicModelsData.INSTANCE.send(PacketDistributor.SERVER.with(() -> null),
//                    new SendPack(name, new CompoundNBT(), uuid, false));

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
