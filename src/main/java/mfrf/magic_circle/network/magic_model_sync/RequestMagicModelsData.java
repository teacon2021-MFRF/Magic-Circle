package mfrf.magic_circle.network.magic_model_sync;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.UUID;

public class RequestMagicModelsData {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1.0";
    private static int ID = 0;
    @OnlyIn(Dist.CLIENT)
    private static final HashMap<UUID, HashMap<String, MagicCircleComponentBase<?>>> CACHED_RENDERING = new HashMap<>();


    public static HashMap<String, MagicCircleComponentBase<?>> getOrCreateRenderCache(UUID uuid) {
        if (!CACHED_RENDERING.containsKey(uuid)) {
            CACHED_RENDERING.put(uuid, new HashMap<>());
        }
        return CACHED_RENDERING.get(uuid);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public MagicCircleComponentBase<?> getRender(UUID uuid, String name) {
        HashMap<String, MagicCircleComponentBase<?>> renderCache = getOrCreateRenderCache(uuid);
        if (!renderCache.containsKey(name)) {
            RequestMagicModelsData.INSTANCE.send(PacketDistributor.SERVER.with(() -> null),
                    new SendPack(name, new CompoundNBT(), uuid));
        }
        return renderCache.get(name);
    }

    public static void registerMessage() {
        INSTANCE = NetworkRegistry.newSimpleChannel(
                new ResourceLocation(MagicCircle.MOD_ID, "request_magic_model"),
                () -> VERSION,
                (version) -> version.equals(VERSION),
                (version) -> version.equals(VERSION));
        INSTANCE.registerMessage(
                nextID(),
                SendPack.class,
                SendPack::toBytes,
                SendPack::new,
                SendPack::handler
        );
    }

    public static int nextID() {
        return ID++;
    }


}
