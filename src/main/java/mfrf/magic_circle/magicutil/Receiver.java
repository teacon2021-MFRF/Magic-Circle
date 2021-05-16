package mfrf.magic_circle.magicutil;

import net.minecraft.entity.Entity;
import net.minecraft.loot.conditions.WeatherCheck;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

public class Receiver {
    public Vector3f vector3f;
    public BlockPos pos;
    public ResourceLocation dimension;
    public WeatherType weatherType;
    public RangeType rangeType;
    public float rangeX;
    public float rangeY;
    public float rangeZ;
    public float radius;
    public World world;

    public Receiver(Vector3f vector3f, BlockPos pos, ResourceLocation dimension, WeatherType weatherType, RangeType rangeType, float rangeX, float rangeY, float rangeZ, float radius, World world) {
        this.vector3f = vector3f;
        this.pos = pos;
        this.dimension = dimension;
        this.weatherType = weatherType;
        this.rangeType = rangeType;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.radius = radius;
        this.world = world;
    }

    public record WeatherType(int rainLevel, int thunderLevel) {
    }

    public enum RangeType {
        BOX, BALL, CIRCLE, CYLINDER, DOME;
    }

    public enum ReceiverType {
        VECTOR, BLOCK, DIMENSION, WEATHER, SPACE, ENTITY, DROP_ENTITY, TIME
    }
}
