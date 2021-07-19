package mfrf.magic_circle.util;

import mfrf.magic_circle.knowledges.PlayerKnowledges;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.network.knowledge_sync.RequestKnowledges;
import mfrf.magic_circle.network.knowledge_sync.SendPack;
import mfrf.magic_circle.world_saved_data.PlayerKnowledge;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.UUID;

public class CachedEveryThingForClient {
    private static final HashMap<UUID, HashMap<String, MagicModelBase>> MAGIC_MODELS = new HashMap<>();
    public static final HashMap<UUID, HashMap<String, Integer>> executeMap = new HashMap<>();
    public static final HashMap<UUID, PlayerKnowledges> knowledgeMap = new HashMap<>();


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

    public static void setExecute(UUID uuid, String name, Integer integer) {
        getOrCreateMap(uuid).put(name, integer);
    }

}
