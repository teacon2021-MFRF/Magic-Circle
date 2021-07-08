package mfrf.magic_circle.network.magic_model_sync;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.world_saved_data.CachedMagicModels;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Supplier;

public class SendPack {
    private UUID playerUUID;
    private String modelName;
    private CompoundNBT modelNBT;
    private boolean hasModel = false;

    public SendPack(PacketBuffer buffer) {
        modelName = buffer.readUtf(Short.MAX_VALUE);
        playerUUID = buffer.readUUID();
        modelNBT = buffer.readAnySizeNbt();
    }

    public SendPack(String modelName, CompoundNBT modelNBT, UUID playerUUID) {
        this.modelName = modelName;
        this.playerUUID = playerUUID;
        this.modelNBT = modelNBT;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeCharSequence(modelName, StandardCharsets.UTF_8);
        buf.writeUUID(playerUUID);
        buf.writeNbt(modelNBT);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();

            ServerPlayerEntity sender = ctx.get().getSender();
            if (direction == NetworkDirection.PLAY_TO_CLIENT) {
                if (hasModel) {
                    CachedMagicModels cachedMagicModels = CachedMagicModels.getOrCreate(sender.getLevel());
                    HashMap<String, MagicCircleComponentBase<?>> orCreateRenderCache = RequestMagicModelsData.getOrCreateRenderCache(playerUUID);
                    HashMap<String, MagicModelBase> orCreateModelCache = cachedMagicModels.getOrCreateModelCache(playerUUID);
                    MagicNodeBase nodeBase = MagicNodeBase.deserializeNBT(modelNBT);
                    orCreateModelCache.put(modelName, (MagicModelBase) nodeBase);
                    orCreateRenderCache.put(modelName, nodeBase.getRender());
                }
            } else if (direction == NetworkDirection.PLAY_TO_SERVER) {

                HashMap<String, MagicModelBase> modelCache = CachedMagicModels.getOrCreate(sender.getLevel()).getOrCreateModelCache(playerUUID);
                CompoundNBT modelNbt = new CompoundNBT();
                if (modelCache.containsKey(modelName)) {
                    MagicModelBase magicModelBase = modelCache.get(modelName);
                    CompoundNBT compoundNBT = magicModelBase.serializeNBT();
                    modelNBT = compoundNBT;
                    hasModel = true;
                }

                RequestMagicModelsData.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendPack(modelName, modelNbt, playerUUID));

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
