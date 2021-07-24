//package mfrf.magic_circle.entity.barrage;
//
//import mfrf.magic_circle.events.InGameCaches;
//import mfrf.magic_circle.magicutil.RGBA;
//import mfrf.magic_circle.util.PositionExpression;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityType;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.passive.TameableEntity;
//import net.minecraft.entity.projectile.ThrowableEntity;
//import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.network.IPacket;
//import net.minecraft.network.datasync.DataParameter;
//import net.minecraft.network.datasync.DataSerializers;
//import net.minecraft.network.datasync.EntityDataManager;
//import net.minecraft.util.DamageSource;
//import net.minecraft.util.math.BlockRayTraceResult;
//import net.minecraft.util.math.EntityRayTraceResult;
//import net.minecraft.util.math.vector.Vector3f;
//import net.minecraft.world.World;
//import net.minecraftforge.fml.network.NetworkHooks;
//
//import java.util.function.BiConsumer;
//import java.util.function.Consumer;
//
//public class DanmakuEntity extends ThrowableEntity {
//    public static final DataParameter<Integer> DAMAGE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Integer> R = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Integer> G = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Integer> B = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Integer> ALPHA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Float> SPEED_SCALE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
//    public static final DataParameter<Integer> MAX_TIME = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
//    public static final DataParameter<Float> X_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
//    public static final DataParameter<Float> Y_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
//    public static final DataParameter<Float> Z_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
//    public static final DataParameter<CompoundNBT> FORMULA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.COMPOUND_TAG);
//    public static final DataParameter<String> TYPE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.STRING);
//    public static final DataParameter<Boolean> HAS_TICK_CONSUMER = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.BOOLEAN);
//    public static final DataParameter<Boolean> PENETRATE_ABLE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.BOOLEAN);
//
//    public DanmakuEntity(EntityType<? extends ThrowableEntity> p_i48580_1_, World p_i48580_2_) {
//        super(p_i48580_1_, p_i48580_2_);
//
//    }
//
//    public DanmakuEntity setTargetVec(Vector3f targetVec) {
//        this.entityData.set(X_TARGET, targetVec.x());
//        this.entityData.set(Y_TARGET, targetVec.y());
//        this.entityData.set(Z_TARGET, targetVec.z());
//        return this;
//    }
//
//    public DanmakuEntity setPositionExpression(PositionExpression expression) {
//        this.entityData.set(FORMULA, expression.serializeNBT());
//        return this;
//    }
//
//    public DanmakuEntity setRGBA(RGBA rgba) {
//        this.entityData.set(R, rgba.r);
//        this.entityData.set(G, rgba.g);
//        this.entityData.set(B, rgba.b);
//        this.entityData.set(ALPHA, rgba.a);
//        return this;
//    }
//
//    public RGBA getRGBA() {
//        Integer r = this.entityData.get(R);
//        Integer g = this.entityData.get(G);
//        Integer b = this.entityData.get(B);
//        Integer a = this.entityData.get(ALPHA);
//        return new RGBA(r, g, b, a);
//    }
//
//    public void setPenetrateAble(boolean b) {
//        this.entityData.set(PENETRATE_ABLE, b);
//    }
//
//    public boolean penetrateAble() {
//        return this.entityData.get(PENETRATE_ABLE);
//    }
//
//    public DanmakuEntity setRequiredVariables(int damage, int max_time, DanmakuType type) {
//        this.entityData.set(DAMAGE, damage);
//        this.entityData.set(MAX_TIME, max_time);
//        this.entityData.set(TYPE, type.toString());
//        return this;
//    }
//
//    public DanmakuEntity setSpeedScale(float scale) {
//        if (scale >= 20) scale = 20;
//        this.entityData.set(SPEED_SCALE, scale);
//        return this;
//    }
//
//    public void setTickConsumer(Consumer<DanmakuEntity> entityConsumer) {
//        InGameCaches.onDanmakuTick.put(this.getUUID(), entityConsumer);
//        this.entityData.set(HAS_TICK_CONSUMER, true);
//    }
//
//    @Override
//    protected void defineSynchedData() {
//        this.entityData.define(DAMAGE, 0);
//        this.entityData.define(R, 0);
//        this.entityData.define(G, 0);
//        this.entityData.define(B, 0);
//        this.entityData.define(ALPHA, 1);
//        this.entityData.define(MAX_TIME, 20);
//        this.entityData.define(FORMULA, new PositionExpression().serializeNBT());
//        this.entityData.define(SPEED_SCALE, 1f);
//        this.entityData.define(X_TARGET, 0F);
//        this.entityData.define(Y_TARGET, 0F);
//        this.entityData.define(Z_TARGET, 0F);
//        this.entityData.define(TYPE, DanmakuType.NULL.name());
//        this.entityData.define(HAS_TICK_CONSUMER, false);
//        this.entityData.define(PENETRATE_ABLE, false);
//    }
//
//    @Override
//    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
//        this.entityData.set(DAMAGE, compoundNBT.getInt("danmaku_damage"));
//        this.entityData.set(MAX_TIME, compoundNBT.getInt("danmaku_max_existing_time"));
//        this.entityData.set(R, compoundNBT.getInt("danmaku_red"));
//        this.entityData.set(G, compoundNBT.getInt("danmaku_green"));
//        this.entityData.set(B, compoundNBT.getInt("danmaku_blue"));
//        this.entityData.set(ALPHA, compoundNBT.getInt("danmaku_alpha"));
//        this.entityData.set(FORMULA, compoundNBT.getCompound("formula"));
//        this.entityData.set(X_TARGET, compoundNBT.getFloat("x_target"));
//        this.entityData.set(Y_TARGET, compoundNBT.getFloat("y_target"));
//        this.entityData.set(Z_TARGET, compoundNBT.getFloat("z_target"));
//        this.entityData.set(SPEED_SCALE, compoundNBT.getFloat("speed_scale"));
//        this.entityData.set(TYPE, compoundNBT.getString("type"));
//        this.entityData.set(HAS_TICK_CONSUMER, compoundNBT.getBoolean("has_tick_consumer"));
//        this.entityData.set(PENETRATE_ABLE, compoundNBT.getBoolean("penetrate_able"));
//
//    }
//
//    @Override
//    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
//        compoundNBT.putFloat("danmaku_damage", this.entityData.get(DAMAGE));
//        compoundNBT.putFloat("danmaku_max_existing_time", this.entityData.get(MAX_TIME));
//        compoundNBT.putInt("danmaku_red", this.entityData.get(R));
//        compoundNBT.putInt("danmaku_green", this.entityData.get(G));
//        compoundNBT.putInt("danmaku_blue", this.entityData.get(B));
//        compoundNBT.putInt("danmaku_alpha", this.entityData.get(ALPHA));
//        compoundNBT.put("formula", this.entityData.get(FORMULA));
//        compoundNBT.putFloat("x_target", this.entityData.get(X_TARGET));
//        compoundNBT.putFloat("y_target", this.entityData.get(Y_TARGET));
//        compoundNBT.putFloat("z_target", this.entityData.get(Z_TARGET));
//        compoundNBT.putFloat("speed_scale", this.entityData.get(SPEED_SCALE));
//        compoundNBT.putString("type", this.entityData.get(TYPE));
//        compoundNBT.putBoolean("has_tick_consumer", this.entityData.get(HAS_TICK_CONSUMER));
//        compoundNBT.putBoolean("penetrate_able", this.entityData.get(PENETRATE_ABLE));
//    }
//
//    @Override
//    public IPacket<?> getAddEntityPacket() {
//        return NetworkHooks.getEntitySpawningPacket(this);
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        int max_time = this.entityData.get(MAX_TIME);
//        if (tickCount >= max_time) {
//            this.remove();
//        } else {
//            PositionExpression positionExpression = new PositionExpression();
//            positionExpression.deserializeNBT(this.entityData.get(FORMULA));
//            Float speed_scale = this.entityData.get(SPEED_SCALE);
//            if (Math.abs(speed_scale) > 10 || speed_scale.isNaN() || speed_scale.isInfinite())
//                speed_scale = 1f;
//            positionExpression.setTargetVec(new Vector3f(this.entityData.get(X_TARGET), this.entityData.get(Y_TARGET), this.entityData.get(Z_TARGET)));
//            Vector3f execute = positionExpression.execute((double) tickCount);
//            if (execute != null) {
//                float offsetX = execute.x() * speed_scale;
//                float offsetY = execute.y() * speed_scale;
//                float offsetZ = execute.z() * speed_scale;
//                float mean = (Math.abs(offsetX) + Math.abs(offsetY) + Math.abs(offsetZ)) / 3;
//                this.shoot(offsetX, offsetY, offsetZ, mean, 0);
//            }
//        }
//
//    }
//
//    private static boolean hasSameOwner(TameableEntity tameableA, TameableEntity tameableB) {
//        if (tameableA.getOwnerUUID() == null) {
//            return false;
//        }
//        if (tameableB.getOwnerUUID() == null) {
//            return false;
//        }
//        return tameableA.getOwnerUUID() == tameableB.getOwnerUUID();
//    }
//
//
//    @Override
//    protected void onHitEntity(EntityRayTraceResult result) {
//        Entity thrower = getOwner();
//        Entity hit = result.getEntity();
//        boolean needRemove = false;
//
//        if (thrower instanceof TameableEntity) {
//            TameableEntity tameable = (TameableEntity) thrower;
//            if (hit instanceof TameableEntity && hasSameOwner(tameable, (TameableEntity) hit)) {
//                needRemove = true;
//            }
//            if (hit instanceof LivingEntity && tameable.isOwnedBy((LivingEntity) hit)) {
//                needRemove = true;
//            }
//        }
//
//        if (thrower != null && !hit.is(thrower)) {
//            DamageSource source = new EntityDamageSourceDanmaku(this, thrower).setMagic();
//            hit.hurt(source, this.entityData.get(DAMAGE));
//            needRemove = true;
//        }
//
//        if (thrower == null) {
//            DamageSource source = new EntityDamageSourceDanmaku(this, this).setMagic();
//            hit.hurt(source, this.entityData.get(DAMAGE));
//            needRemove = true;
//        }
//        //todo damage type
//
//        if (!this.penetrateAble() && needRemove) {
//            this.remove();
//        }
//    }
//
//    @Override
//    protected void onHitBlock(BlockRayTraceResult p_230299_1_) {
//        BiConsumer<BlockRayTraceResult, DanmakuEntity> blockRayTraceResultDanmakuEntityBiConsumer = InGameCaches.onDanmakuCrash.remove(getUUID());
//        if (blockRayTraceResultDanmakuEntityBiConsumer != null) {
//            blockRayTraceResultDanmakuEntityBiConsumer.accept(p_230299_1_, this);
//        }
//        if (!this.penetrateAble()) {
//            this.remove();
//        }
//    }
//
//    @Override
//    protected float getGravity() {
//        return super.getGravity();
//    }
//
//
//    @Override
//    public void remove() {
//        //todo remove_animation
//        InGameCaches.onDanmakuTick.remove(getUUID());
//        super.remove();
//    }
//
//    public enum DanmakuType {
//        NULL, BEAM, BULLET
//    }
//}
