package mfrf.magic_circle.network.magic_model_request;



import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import mfrf.magic_circle.world_saved_data.StoredMagicModels;
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
        hasModel = buffer.readBoolean();
    }

    public SendPack(String modelName, CompoundNBT modelNBT, UUID playerUUID, boolean hasModel) {
        this.modelName = modelName;
        this.playerUUID = playerUUID;
        this.modelNBT = modelNBT;
        this.hasModel = hasModel;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUtf(modelName);
        buf.writeUUID(playerUUID);
        buf.writeNbt(modelNBT);
        buf.writeBoolean(hasModel);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();

            ServerPlayerEntity sender = ctx.get().getSender();
            if (direction == NetworkDirection.PLAY_TO_CLIENT) {
                if (hasModel) {
                    HashMap<String, MagicModelBase> orCreateModels = CachedEveryThingForClient.getOrCreateModels(playerUUID);
                    MagicNodeBase nodeBase = MagicNodeBase.deserializeNBT(modelNBT);
                    orCreateModels.put(modelName, (MagicModelBase) nodeBase);
                }
            } else if (direction == NetworkDirection.PLAY_TO_SERVER) {

                MagicModelBase modelCache = StoredMagicModels.getOrCreate(sender.getLevel()).request(playerUUID, modelName);
                CompoundNBT modelNbt = new CompoundNBT();
                if (modelCache != null) {
                    CompoundNBT compoundNBT = modelCache.serializeNBT();
                    modelNBT = compoundNBT;
                    hasModel = true;
                }

                RequestMagicModelsData.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendPack(modelName, modelNbt, playerUUID, hasModel));

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
