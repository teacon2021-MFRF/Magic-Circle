package mfrf.magic_circle.world_saved_data;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.magicutil.MagicModelBase;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.util.CachedEveryThingForClient;
import mfrf.magic_circle.util.PositionExpression;
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

public class StoredMagicModels extends WorldSavedData {
    private static final String NAME = "StoredMagicModels";
    private static final HashMap<UUID, HashMap<String, MagicModelBase>> Magic_Models = new HashMap<>();

    public StoredMagicModels() {
        super(NAME);
    }

    public HashMap<String, MagicModelBase> getOrCreateModelCache(UUID id) {
        if (!Magic_Models.containsKey(id)) {
            HashMap<String, MagicModelBase> stringMagicModelBaseHashMap = new HashMap<>();
            //todo remove after reval
            //==============================================
            BeginNodeBase beginNodeBase = new BeginNodeBase();
            ThrowBehaviorNode throwBehaviorNode = new ThrowBehaviorNode();
            beginNodeBase.appendNodeL(throwBehaviorNode);
            PositionExpression expression = new PositionExpression("math.sin(t + addition % 10)", "t", "math.cos(t + addition % 10)", null, null, null);
            throwBehaviorNode.setPositionExpression(expression);

            stringMagicModelBaseHashMap.put("fireball", new MagicModelBase(beginNodeBase));
            //==============================================

            Magic_Models.put(id, stringMagicModelBaseHashMap);
        }
        return Magic_Models.get(id);
    }

    public boolean isFull(UUID uuid) {
        return getOrCreateModelCache(uuid).size() >= Config.MAX_MEMORIZED_MODEL_PER_PLAYER.get();
    }

    public boolean add(UUID uuid, String name, MagicModelBase modelBase) {
        if (!isFull(uuid)) {
            getOrCreateModelCache(uuid).put(name, modelBase);
            setDirty();
            return true;

        } else return false;
    }

    public MagicModelBase request(UUID uuid, String name) {
        HashMap<String, MagicModelBase> map = getOrCreateModelCache(uuid);
        MagicModelBase magicModelBase = map.get(name);
        if (magicModelBase != null) {
            CachedEveryThingForClient.getOrCreateModels(uuid).put(name, magicModelBase);
        }
        return magicModelBase;
    }

    public void forgot(UUID uuid, String name) {
        HashMap<String, MagicModelBase> stringMagicModelBaseHashMap = getOrCreateModelCache(uuid);
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

            Magic_Models.put(uuid, stringMagicModelBaseHashMap);
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189551_1_) {

        ListNBT listNBT = new ListNBT();
        CompoundNBT compoundNBT = new CompoundNBT();
        for (Map.Entry<UUID, HashMap<String, MagicModelBase>> uuidHashMapEntry : Magic_Models.entrySet()) {

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

    public static StoredMagicModels getOrCreate(World worldIn) {
        if (worldIn instanceof ServerWorld) {
            ServerWorld world = worldIn.getServer().getLevel(World.OVERWORLD);
            DimensionSavedDataManager storage = world.getDataStorage();
            return storage.computeIfAbsent(StoredMagicModels::new, NAME);
        }
        throw new RuntimeException("Attempted to get the data from a wrong world. This is wrong.");
    }


}
