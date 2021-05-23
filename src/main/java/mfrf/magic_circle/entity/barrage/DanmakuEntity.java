package mfrf.magic_circle.entity.barrage;

import com.googlecode.aviator.AviatorEvaluator;
import mfrf.magic_circle.magicutil.RGBA;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DanmakuEntity extends Entity {
    public static final DataParameter<Float> DAMAGE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Integer> R = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> G = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> B = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
    public static final DataParameter<Integer> ALPHA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.INT);
    public static final DataParameter<Float> SPEED_SCALE = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> TIME = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> MAX_TIME = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> X_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> Y_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<Float> Z_TARGET = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.FLOAT);
    public static final DataParameter<String> VELOCITY_X_FORMULA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> VELOCITY_Y_FORMULA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.STRING);
    public static final DataParameter<String> VELOCITY_Z_FORMULA = EntityDataManager.defineId(DanmakuEntity.class, DataSerializers.STRING);

    public DanmakuEntity(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    public DanmakuEntity setTargetVec(Vector3f targetVec) {
        this.entityData.set(X_TARGET, targetVec.x());
        this.entityData.set(Y_TARGET, targetVec.y());
        this.entityData.set(Z_TARGET, targetVec.z());
        return this;
    }

    public DanmakuEntity setPositionExpression(ThrowBehaviorNode.PositionExpression expression) {
        this.entityData.set(VELOCITY_X_FORMULA, expression.x);
        this.entityData.set(VELOCITY_Y_FORMULA, expression.y);
        this.entityData.set(VELOCITY_Z_FORMULA, expression.z);
        return this;
    }

    public DanmakuEntity setRGBA(RGBA rgba) {
        this.entityData.set(R, rgba.r);
        this.entityData.set(G, rgba.g);
        this.entityData.set(B, rgba.b);
        this.entityData.set(ALPHA, rgba.a);
        return this;
    }

    public DanmakuEntity setRequiredVariables(float damage, float max_time) {
        this.entityData.set(DAMAGE, damage);
        return this;
    }

    public DanmakuEntity setSpeedScale(float scale) {
        this.entityData.set(SPEED_SCALE, scale);
        return this;
    }
//todo collision box etc.


    @Override
    protected void defineSynchedData() {
        this.entityData.define(DAMAGE, 0f);
        this.entityData.define(R, 0);
        this.entityData.define(G, 0);
        this.entityData.define(B, 0);
        this.entityData.define(ALPHA, 1);
        this.entityData.define(TIME, 0f);
        this.entityData.define(MAX_TIME, 20f);
        this.entityData.define(VELOCITY_X_FORMULA, "0");
        this.entityData.define(VELOCITY_Y_FORMULA, "0");
        this.entityData.define(VELOCITY_Z_FORMULA, "0");
        this.entityData.define(SPEED_SCALE, 1f);
        this.entityData.define(X_TARGET, 0F);
        this.entityData.define(X_TARGET, 0F);
        this.entityData.define(X_TARGET, 0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {

        this.entityData.set(DAMAGE, compoundNBT.getFloat("danmaku_damage"));
        this.entityData.set(TIME, compoundNBT.getFloat("danmaku_time"));
        this.entityData.set(MAX_TIME, compoundNBT.getFloat("danmaku_max_existing_time"));
        this.entityData.set(R, compoundNBT.getInt("danmaku_red"));
        this.entityData.set(G, compoundNBT.getInt("danmaku_green"));
        this.entityData.set(B, compoundNBT.getInt("danmaku_blue"));
        this.entityData.set(ALPHA, compoundNBT.getInt("danmaku_alpha"));
        this.entityData.set(VELOCITY_X_FORMULA, compoundNBT.getString("x_formula"));
        this.entityData.set(VELOCITY_Y_FORMULA, compoundNBT.getString("y_formula"));
        this.entityData.set(VELOCITY_Z_FORMULA, compoundNBT.getString("z_formula"));
        this.entityData.set(X_TARGET, compoundNBT.getFloat("x_target"));
        this.entityData.set(Y_TARGET, compoundNBT.getFloat("y_target"));
        this.entityData.set(Z_TARGET, compoundNBT.getFloat("z_target"));
        this.entityData.set(SPEED_SCALE, compoundNBT.getFloat("speed_scale"));

    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        compoundNBT.putFloat("danmaku_damage", this.entityData.get(DAMAGE));
        compoundNBT.putFloat("danmaku_time", this.entityData.get(TIME));
        compoundNBT.putFloat("danmaku_max_existing_time", this.entityData.get(MAX_TIME));
        compoundNBT.putInt("danmaku_red", this.entityData.get(R));
        compoundNBT.putInt("danmaku_green", this.entityData.get(G));
        compoundNBT.putInt("danmaku_blue", this.entityData.get(B));
        compoundNBT.putInt("danmaku_alpha", this.entityData.get(ALPHA));
        compoundNBT.putString("x_formula", this.entityData.get(VELOCITY_X_FORMULA));
        compoundNBT.putString("y_formula", this.entityData.get(VELOCITY_Y_FORMULA));
        compoundNBT.putString("z_formula", this.entityData.get(VELOCITY_Z_FORMULA));
        compoundNBT.putFloat("x_target", this.entityData.get(X_TARGET));
        compoundNBT.putFloat("y_target", this.entityData.get(Y_TARGET));
        compoundNBT.putFloat("z_target", this.entityData.get(Z_TARGET));
        compoundNBT.putFloat("speed_scale", this.entityData.get(SPEED_SCALE));
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public void tick() {
        super.tick();
        Float timePassed = this.getEntityData().get(TIME) + 1;
        Float max_time = this.getEntityData().get(MAX_TIME);
        if (timePassed >= max_time) {
            this.remove();
        } else {
            String xFormula = this.entityData.get(VELOCITY_X_FORMULA);
            String yFormula = this.entityData.get(VELOCITY_Y_FORMULA);
            String zFormula = this.entityData.get(VELOCITY_Z_FORMULA);
            Float speed_scale = this.entityData.get(SPEED_SCALE);
            Map<String, Object> env = new HashMap<>();
            env.put("time", timePassed.toString());
            env.put("target_x", this.entityData.get(X_TARGET));
            env.put("target_y", this.entityData.get(Y_TARGET));
            env.put("target_z", this.entityData.get(Z_TARGET));
            env.put("random", RandomUtils.nextFloat(0, 1));
            this.moveTo((Float) AviatorEvaluator.execute(xFormula, env) * speed_scale, (Float) AviatorEvaluator.execute(yFormula, env) * speed_scale, (Float) AviatorEvaluator.execute(zFormula, env) * speed_scale);
        }


    }
}
