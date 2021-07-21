package mfrf.magic_circle.util;

import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.network.gui_model_sync.SyncModelData;
import mfrf.magic_circle.network.knowledge_sync.RequestKnowledges;
import mfrf.magic_circle.network.knowledge_sync.SendPack;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import mfrf.magic_circle.world_saved_data.StoredMagicModels;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import java.util.*;

public class CachedEveryThingForClient {
    private static final HashMap<UUID, HashMap<String, MagicModelBase>> MAGIC_MODELS = new HashMap<>();
    private static final HashSet<UpdateCache> UPDATED_MODELS = new HashSet<>();

    private static final HashMap<UUID, HashMap<String, Integer>> executeMap = new HashMap<>();
    private static final HashMap<UUID, PlayerKnowledges> knowledgeMap = new HashMap<>();


    public static HashMap<String, MagicModelBase> getOrCreateModels(UUID uuid) {
        if (!MAGIC_MODELS.containsKey(uuid)) {
            MAGIC_MODELS.put(uuid, new HashMap<>());
        }
        return MAGIC_MODELS.get(uuid);
    }

    public static HashMap<String, Integer> getOrCreateMap(UUID uuid) {
        if (!executeMap.containsKey(uuid)) {
            executeMap.put(uuid, new HashMap<>());
        }
        return executeMap.get(uuid);
    }

    public static PlayerKnowledges requestKnowledge(World world, UUID playerId) {
        if (world.isClientSide()) {
            RequestKnowledges.INSTANCE.sendToServer(new SendPack(new CompoundNBT(), playerId));
            return knowledgeMap.get(playerId);
        } else {
            return PlayerKnowledge.getOrCreate(world).getOrCreate(playerId);
        }
    }

    public static HashMap<String, MagicModelBase> requestModels(World world, UUID playerId) {
        if (world.isClientSide()) {
            SyncModelData.INSTANCE.sendToServer(new mfrf.magic_circle.network.gui_model_sync.SendPack(new CompoundNBT(), playerId, mfrf.magic_circle.network.gui_model_sync.SendPack.State.REQUEST, ""));
            return getOrCreateModels(playerId);
        } else {
            return StoredMagicModels.getOrCreate(world).getOrCreateModelCache(playerId);
        }
    }

    public static boolean hasUpdate(UUID player, String name) {
        return UPDATED_MODELS.contains(new UpdateCache(player, name));
    }

    public static void setUpdated(UUID player, String name) {
        UPDATED_MODELS.remove(new UpdateCache(player, name));
    }

    public static void updateModel(UUID player, String name, MagicModelBase modelBase) {
        getOrCreateModels(player).put(name, modelBase);
        UPDATED_MODELS.add(new UpdateCache(player, name));
    }

    public static void setExecute(UUID uuid, String name, Integer integer) {
        getOrCreateMap(uuid).put(name, integer);
    }

    public static HashMap<UUID, HashMap<String, MagicModelBase>> getMagicModels() {
        return MAGIC_MODELS;
    }

    public static HashSet<UpdateCache> getUpdatedModels() {
        return UPDATED_MODELS;
    }

    public static HashMap<UUID, HashMap<String, Integer>> getExecuteMap() {
        return executeMap;
    }

    public static HashMap<UUID, PlayerKnowledges> getKnowledgeMap() {
        return knowledgeMap;
    }

    public static class UpdateCache {
        public UUID id;
        public String name;

        public UpdateCache(UUID id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof UpdateCache)) return false;
            UpdateCache that = (UpdateCache) o;
            return id.equals(that.id) && name.equals(that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

}
