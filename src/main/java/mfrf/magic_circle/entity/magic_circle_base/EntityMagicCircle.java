package mfrf.magic_circle.entity.magic_circle_base;

import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class EntityMagicCircle extends Entity {
    public static final DataParameter<CompoundNBT> MAGIC_CIRCLE_OBJECT = EntityDataManager.defineId(EntityMagicCircle.class, DataSerializers.COMPOUND_TAG);
    public static final DataParameter<Float> PROGRESS = EntityDataManager.defineId(EntityMagicCircle.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PROGRESS_ADDITION_PER_TICK = EntityDataManager.defineId(EntityMagicCircle.class, DataSerializers.FLOAT);

    public EntityMagicCircle(EntityType<?> entityTypeIn, World p_i48580_2_) {
        super(entityTypeIn, p_i48580_2_);
    }

//    public EntityMagicCircle setMagicCircleObject(MagicCircleRenderBase magicCircleObject) {
//        this.getEntityData().set(MAGIC_CIRCLE_OBJECT, magicCircleObject.serializeNBT());
//        return this;
//    }
    //todo complete it

    @Override
    protected void defineSynchedData() {
        this.entityData.define(MAGIC_CIRCLE_OBJECT, new CompoundNBT());
        this.entityData.define(PROGRESS, 0f);
        this.entityData.define(PROGRESS_ADDITION_PER_TICK, 0.01f);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        CompoundNBT magic_circle = compoundNBT.getCompound("magic_circle");
        this.entityData.set(MAGIC_CIRCLE_OBJECT, magic_circle);
        this.entityData.set(PROGRESS, compoundNBT.getFloat("lifetime"));
        this.entityData.set(PROGRESS_ADDITION_PER_TICK, magic_circle.getFloat("pgpt"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.put("magic_circle", this.entityData.get(MAGIC_CIRCLE_OBJECT));
        compoundNBT.putFloat("lifetime", this.entityData.get(PROGRESS));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        this.entityData.set(PROGRESS, this.entityData.get(PROGRESS) + this.entityData.get(PROGRESS_ADDITION_PER_TICK));
        super.tick();
    }
}
