package mfrf.magic_circle.network.knowledge_sync;

import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.network.magic_model_request.RequestMagicModelsData;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.UUID;
import java.util.function.Supplier;

public class SendPack {
    private UUID playerUUID;
    private CompoundNBT knowledges;

    public SendPack(PacketBuffer buffer) {
        playerUUID = buffer.readUUID();
        knowledges = buffer.readAnySizeNbt();
    }

    public SendPack(CompoundNBT nbt, UUID playerUUID) {
        this.playerUUID = playerUUID;
        this.knowledges = nbt;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(playerUUID);
        buf.writeNbt(knowledges);
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();

            ServerPlayerEntity sender = ctx.get().getSender();
            if (direction == NetworkDirection.PLAY_TO_CLIENT) {

                CachedEveryThingForClient.getKnowledgeMap().put(playerUUID, PlayerKnowledges.deserializeNBT(knowledges));

            } else if (direction == NetworkDirection.PLAY_TO_SERVER) {

                PlayerKnowledges knowledges = PlayerKnowledge.getOrCreate(sender.getLevel()).getOrCreate(playerUUID);

                RequestKnowledges.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendPack(knowledges.serializeNBT(), playerUUID));

            }

        });
        ctx.get().setPacketHandled(true);
    }
}
