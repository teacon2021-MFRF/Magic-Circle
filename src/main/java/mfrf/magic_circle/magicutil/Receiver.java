package mfrf.magic_circle.magicutil;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;

import java.util.logging.Level;

public class Receiver {
    public ReceiverType type;
    public Vector3f vector3;
    public BlockPos pos;
    public WeatherType weatherType;
    public RangeType rangeType;
    public float rangeX;
    public float rangeY;
    public float rangeZ;
    public float radius;
    public World world;

    public Receiver(Vector3f vector3f, BlockPos pos, WeatherType weatherType, RangeType rangeType, float rangeX, float rangeY, float rangeZ, float radius, World world, ReceiverType type) {
        this.vector3 = vector3f;
        this.pos = pos;
        this.weatherType = weatherType;
        this.rangeType = rangeType;
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
        this.radius = radius;
        this.world = world;
        this.type = type;
    }

    public Receiver copy() {
        return new Receiver(vector3, pos, weatherType, rangeType, rangeX, rangeY, rangeZ, radius, world, type);
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
        BOX, BALL, CIRCLE, CYLINDER;
    }

    public enum ReceiverType {
        VECTOR, BLOCK, DIMENSION, WEATHER, SPACE, ENTITY, DROP_ENTITY, TIME
    }
}
