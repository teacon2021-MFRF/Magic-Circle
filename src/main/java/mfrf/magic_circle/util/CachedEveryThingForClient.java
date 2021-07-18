package mfrf.magic_circle.util;

import mfrf.magic_circle.magicutil.MagicModelBase;

import java.util.HashMap;
import java.util.UUID;

public class CachedEveryThingForClient {
    private static final HashMap<UUID, HashMap<String, MagicModelBase>> MAGIC_MODELS = new HashMap<>();
    public static final HashMap<UUID, HashMap<String, Integer>> executeMap = new HashMap<>();


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

    public static void setExecute(UUID uuid, String name, Integer integer) {
        getOrCreateMap(uuid).put(name, integer);
    }

}
