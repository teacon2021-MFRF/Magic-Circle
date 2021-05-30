package mfrf.magic_circle.world_saved_data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import mfrf.magic_circle.knowledges.PlayerKnowledges;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class PlayerKnowledge extends WorldSavedData {
    private static final String NAME = "PlayerKnowledge";
    private static final HashMap<UUID, PlayerKnowledges> KNOWLEDGE_MAP = new HashMap<>();

    public PlayerKnowledge() {
        super(NAME);
    }

    public PlayerKnowledges get(UUID id) {
        return KNOWLEDGE_MAP.get(id);
    }

    @Override
    public void load(CompoundNBT compoundNBT) {
        ListNBT map = (ListNBT) compoundNBT.get("map");
        for (INBT inbt : map) {
            CompoundNBT nbt = (CompoundNBT) inbt;
            UUID name = nbt.getUUID("name");
            PlayerKnowledges knowledge = PlayerKnowledges.deserializeNBT(nbt.getCompound("knowledge"));
            KNOWLEDGE_MAP.put(name, knowledge);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT compoundNBT) {
        ListNBT inbts = new ListNBT();
        for (Map.Entry<UUID, PlayerKnowledges> uuidPlayerKnowledgesEntry : KNOWLEDGE_MAP.entrySet()) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putUUID("name", uuidPlayerKnowledgesEntry.getKey());
            nbt.put("knowledge", uuidPlayerKnowledgesEntry.getValue().serializeNBT());
        }
        compoundNBT.put("map", inbts);
        return compoundNBT;
    }

    public static PlayerKnowledge get(World worldIn) {
        if (!(worldIn instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.computeIfAbsent(PlayerKnowledge::new, NAME);
    }

}
