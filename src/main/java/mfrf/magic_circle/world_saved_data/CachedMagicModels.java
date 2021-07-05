package mfrf.magic_circle.world_saved_data;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.magicutil.MagicModelBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CachedMagicModels extends WorldSavedData {
    private static final String NAME = "CachedMagicModels";
    private static final HashMap<UUID, HashMap<String, MagicModelBase>> CACHED_MODELS = new HashMap<>();

    public CachedMagicModels() {
        super(NAME);
    }

    public HashMap<String, MagicModelBase> getOrCreate(UUID id) {
        if (!CACHED_MODELS.containsKey(id)) {
            CACHED_MODELS.put(id, new HashMap<>());
        }
        return CACHED_MODELS.get(id);
    }

    public boolean isFull(UUID uuid) {
        return getOrCreate(uuid).size() >= Config.MAX_MEMORIZED_MODEL_PER_PLAYER.get();
    }

    public boolean add(UUID uuid, String name, MagicModelBase modelBase) {
        if (!isFull(uuid)) {

            CACHED_MODELS.get(uuid).put(name, modelBase);
            setDirty();
            return true;

        } else return false;
    }

    public MagicModelBase request(UUID uuid, String name) {
        HashMap<String, MagicModelBase> map = getOrCreate(uuid);
        return map.get(name);
    }

    public void forgot(UUID uuid, String name) {
        HashMap<String, MagicModelBase> stringMagicModelBaseHashMap = getOrCreate(uuid);
        stringMagicModelBaseHashMap.remove(name);
        setDirty();
    }

    @Override
    public void load(CompoundNBT compoundNBT) {
        ListNBT list = compoundNBT.getList("list", Constants.NBT.TAG_COMPOUND);
        for (INBT inbt : list) {
            CompoundNBT nbt = (CompoundNBT) inbt;
            UUID uuid = nbt.getUUID("uuid");
            ListNBT memorized = nbt.getList("memorized", Constants.NBT.TAG_COMPOUND);

            HashMap<String, MagicModelBase> stringMagicModelBaseHashMap = new HashMap<>();
            for (INBT inbt1 : memorized) {
                CompoundNBT memo = (CompoundNBT) inbt1;
                String name = memo.getString("name");
                MagicModelBase model = MagicModelBase.deserializeNBT(memo.getCompound("model"));
                stringMagicModelBaseHashMap.put(name, model);
            }

            CACHED_MODELS.put(uuid, stringMagicModelBaseHashMap);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189551_1_) {

        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();
        for (Map.Entry<UUID, HashMap<String, MagicModelBase>> uuidHashMapEntry : CACHED_MODELS.entrySet()) {

            UUID uuid = uuidHashMapEntry.getKey();
            HashMap<String, MagicModelBase> value = uuidHashMapEntry.getValue();

            ListNBT memorized = new ListNBT();

            for (Map.Entry<String, MagicModelBase> stringMagicModelBaseEntry : value.entrySet()) {
                CompoundNBT memo = new CompoundNBT();
                String name = stringMagicModelBaseEntry.getKey();
                MagicModelBase model = stringMagicModelBaseEntry.getValue();
                memo.putString("name", name);
                memo.put("model", model.serializeNBT());
                memorized.add(memo);
            }
            compoundNBT.putUUID("uuid", uuid);
            compoundNBT.put("memorized", memorized);
            listNBT.add(compoundNBT);
        }
        CompoundNBT ret = new CompoundNBT();
        ret.put("list", listNBT);
        return ret;
    }

    public static CachedMagicModels getOrCreate(World worldIn) {
        if (!(worldIn instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }
        ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
        DimensionSavedDataManager storage = world.getDataStorage();
        return storage.computeIfAbsent(CachedMagicModels::new, NAME);
    }
}
