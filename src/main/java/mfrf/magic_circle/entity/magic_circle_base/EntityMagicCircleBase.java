package mfrf.magic_circle.entity.magic_circle_base;

import mfrf.magic_circle.rendering.MagicCircleObjectBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityMagicCircleBase extends Entity {
    public static final DataParameter<CompoundNBT> MAGIC_CIRCLE_OBJECT = EntityDataManager.createKey(EntityMagicCircleBase.class, DataSerializers.COMPOUND_NBT);
    public static final DataParameter<Float> PROGRESS = EntityDataManager.createKey(EntityMagicCircleBase.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PROGRESS_ADDITION_PER_TICK = EntityDataManager.createKey(EntityMagicCircleBase.class, DataSerializers.FLOAT);

    public EntityMagicCircleBase(EntityType<?> p_i48580_1_, World p_i48580_2_, MagicCircleObjectBase magicCircleObjectBase) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Override
    protected void registerData() {
        this.dataManager.register(MAGIC_CIRCLE_OBJECT, new CompoundNBT());
        this.dataManager.register(PROGRESS, 0f);
        this.dataManager.register(PROGRESS_ADDITION_PER_TICK, 0.01f);
    }

    @Override
    protected void readAdditional(CompoundNBT compoundNBT) {
        CompoundNBT magic_circle = compoundNBT.getCompound("magic_circle");
        this.dataManager.set(MAGIC_CIRCLE_OBJECT, magic_circle);
        this.dataManager.set(PROGRESS, compoundNBT.getFloat("lifetime"));
        this.dataManager.set(PROGRESS_ADDITION_PER_TICK, magic_circle.getFloat("pgpt"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compoundNBT) {
        compoundNBT.put("magic_circle", this.dataManager.get(MAGIC_CIRCLE_OBJECT));
        compoundNBT.putFloat("lifetime", this.dataManager.get(PROGRESS));
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        this.dataManager.set(PROGRESS, this.dataManager.get(PROGRESS) + this.dataManager.get(PROGRESS_ADDITION_PER_TICK));
        super.tick();
    }
}
