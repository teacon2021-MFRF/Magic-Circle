package mfrf.magic_circle.magicutil;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

public class Receiver {
    public ReceiverType type;
    public Vector3f vector3;
    public BlockPos pos;
    public DimensionType dimension;
    public WeatherType weatherType;
    public RangeType rangeType;
    public float rangeX;
    public float rangeY;
    public float rangeZ;
    public float radius;
    public World world;

    public Receiver(Vector3f vector3f, BlockPos pos, DimensionType dimension, WeatherType weatherType, RangeType rangeType, float rangeX, float rangeY, float rangeZ, float radius, World world,ReceiverType type) {
        this.vector3 = vector3f;
        this.pos = pos;
        this.dimension = dimension;
        this.weatherType = weatherType;
        this.rangeType = rangeType;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.radius = radius;
        this.world = world;
        this.type = type;
    }

    public static class WeatherType {
        public WeatherType(float rainLevel, float thunderLevel) {
            this.rainLevel = rainLevel;
            this.thunderLevel = thunderLevel;
        }

        public float rainLevel;
        public float thunderLevel;
    }

    public enum RangeType {
        BOX, BALL, CIRCLE, CYLINDER, DOME;
    }

    public enum ReceiverType {
        VECTOR, BLOCK, DIMENSION, WEATHER, SPACE, ENTITY, DROP_ENTITY, TIME
    }
}
