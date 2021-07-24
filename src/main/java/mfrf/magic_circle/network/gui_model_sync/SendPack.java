package mfrf.magic_circle.network.gui_model_sync;

import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import mfrf.magic_circle.world_saved_data.StoredMagicModels;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public class SendPack {
    private UUID playerUUID;
    private State state;
    private String name;
    private CompoundNBT modelData;

    public SendPack(CompoundNBT nbt, UUID playerUUID, State state, String modelName) {
        this.playerUUID = playerUUID;
        this.modelData = nbt;
        this.state = state;
        this.name = modelName;
    }

    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(playerUUID);
        buf.writeShort(state.ordinal());
        buf.writeUtf(name);
        buf.writeNbt(modelData);
    }

    public SendPack(PacketBuffer buffer) {
        playerUUID = buffer.readUUID();
        state = State.values()[buffer.readShort()];
        name = buffer.readUtf(32767);
        modelData = buffer.readAnySizeNbt();
    }

    public void handler(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            NetworkDirection direction = ctx.get().getDirection();
            ServerPlayerEntity sender = ctx.get().getSender();

            if (direction == NetworkDirection.PLAY_TO_SERVER) {
                ServerWorld level = sender.getLevel();
                StoredMagicModels orCreate = StoredMagicModels.getOrCreate(level);

//                if (playerUUID == sender.getUUID()) {
                    switch (state) {
                        case DELETE: {
                            orCreate.forgot(playerUUID, name);
                            CachedEveryThingForClient.getOrCreateModels(playerUUID).remove(name);

                            SyncModelData.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendPack(new CompoundNBT(), playerUUID, State.DELETE, name));

                            break;
                        }
                        case UPDATE: {
                            MagicModelBase magicModelBase = MagicModelBase.deserializeNBT(modelData);
                            boolean success = orCreate.add(playerUUID, name, magicModelBase);
                            if (success) {
                                CachedEveryThingForClient.getOrCreateModels(playerUUID).put(name, magicModelBase);
                                for (ServerPlayerEntity player : level.players()) {
                                    SyncModelData.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SendPack(modelData, playerUUID, State.UPDATE, name));
                                }
                            }
                            break;
                        }
                        case REQUEST: {
                            HashMap<String, MagicModelBase> orCreateModelCache = orCreate.getOrCreateModelCache(sender.getUUID());
                            CompoundNBT compoundNBT = new CompoundNBT();
                            for (Map.Entry<String, MagicModelBase> stringMagicModelBaseEntry : orCreateModelCache.entrySet()) {
                                compoundNBT.put(stringMagicModelBaseEntry.getKey(), stringMagicModelBaseEntry.getValue().serializeNBT());
                            }
                            SyncModelData.INSTANCE.send(PacketDistributor.PLAYER.with(() -> sender), new SendPack(compoundNBT, playerUUID, State.REQUEST, ""));
                        }

                    }
//                }

            } else if (direction == NetworkDirection.PLAY_TO_CLIENT) {

                switch (state) {

                    case UPDATE: {
                        CachedEveryThingForClient.updateModel(playerUUID, name, MagicModelBase.deserializeNBT(modelData));
                        break;
                    }
                    case DELETE: {

                        HashMap<String, MagicModelBase> orCreateModels = CachedEveryThingForClient.getOrCreateModels(playerUUID);
                        orCreateModels.remove(name);
                        CachedEveryThingForClient.setUpdated(playerUUID, name);

                        break;
                    }
                    case REQUEST: {
                        for (String key : modelData.getAllKeys()) {
                            CachedEveryThingForClient.updateModel(playerUUID, key, MagicModelBase.deserializeNBT(modelData.getCompound(key)));
                            //todo check
                        }

                        break;
                    }
                }

            }

        });
        ctx.get().setPacketHandled(true);
    }

    public enum State {
        UPDATE, DELETE, REQUEST;
    }
}
