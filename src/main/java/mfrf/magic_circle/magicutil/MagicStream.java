package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.BiFunction;

public class MagicStream {
    public ArrayList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> functions = new ArrayList<>();

    public final MagicStreamInfo info;

    public MagicStream(MagicStreamInfo info) {
        this.info = info;
        MagicNodePropertyMatrix8By8.initPauliElements(info.data.eigenMatrix);
    }

    public void Matrixtimes(MagicNodePropertyMatrix8By8 matrix) {
        info.data.eigenMatrix = info.data.eigenMatrix.leftTimes(matrix);
    }

    public void apply() {
        for (BiFunction<MagicStream, MagicStreamInfo, MagicStream> function : functions) {
            function.apply(this, info);
        }
    }

    public static class MagicStreamInfo {
        public MagicStreamInfo(@Nullable MagicNodeBase lastNode, DataContain data) {
            this.lastNode = lastNode;
            this.data = data;
        }

        public DataContain data;
        public MagicNodeBase lastNode;

        public MagicStreamInfo copy() {
            return new MagicStreamInfo(null, data);
        }
    }

    public static class DataContain {
        public MagicNodePropertyMatrix8By8 eigenMatrix = MagicNodePropertyMatrix8By8.IDENTITY.copy();
        public Receiver.ReceiverType receiverType = Receiver.ReceiverType.VECTOR;
        public Invoker.InvokerType invokerType = Invoker.InvokerType.ENTITY;
        public Vector3f targetVec = new Vector3f();
        public BlockPos targetPos = new BlockPos(0, 0, 0);
        public Receiver.WeatherType weatherType = new Receiver.WeatherType(0, 0);
        public Receiver.RangeType rangeType = Receiver.RangeType.BOX;
        public World world = null;
        public BlockPos beginPos = new BlockPos(0, 0, 0);
        public ItemStack invokerStack = ItemStack.EMPTY;
        public UUID player = null;
        public UUID entity = null;
        public MagicStream lastMagicStream = null;
        public HashMap<String, Integer> cached_Data;
        public double complexityAddition = 0;

        public DataContain(MagicNodePropertyMatrix8By8 eigenMatrix, Receiver.ReceiverType receiverType, Invoker.InvokerType invokerType, Vector3f targetVec, BlockPos targetPos, Receiver.WeatherType weatherType, Receiver.RangeType rangeType, World world, BlockPos beginPos, ItemStack invokerStack, UUID player, UUID entity, MagicStream lastMagicStream, HashMap<String, Integer> cached_Data, double complexityAddition) {
            this.eigenMatrix = eigenMatrix;
            this.receiverType = receiverType;
            this.invokerType = invokerType;
            this.targetVec = targetVec;
            this.targetPos = targetPos;
            this.weatherType = weatherType;
            this.rangeType = rangeType;
            this.world = world;
            this.beginPos = beginPos;
            this.invokerStack = invokerStack;
            this.player = player;
            this.entity = entity;
            this.lastMagicStream = lastMagicStream;
            this.cached_Data = cached_Data;
            this.complexityAddition = complexityAddition;
        }

        public DataContain() {
        }

        public DataContain(Invoker invoker, Receiver receiver) {
            receiverType = receiver.type;
            invokerType = invoker.type;
            targetVec = receiver.vector3;
            targetPos = receiver.pos;
            rangeType = receiver.rangeType;
            world = receiver.world;
            beginPos = invoker.beginPos;
            invokerStack = invoker.invokerStack;
            player = invoker.entity;
            entity = invoker.entity;
            lastMagicStream = invoker.lastMagicStream;
        }

        public float getRangeX() {
            return (float) ((eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.LIHUO) + eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.KANSHUI)) * eigenMatrix.getRange());
        }

        public float getRangeY() {
            return (float) ((eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.THUNDER) + eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.DUZE)) * eigenMatrix.getRange());
        }

        public float getRangeZ() {
            return (float) ((eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.GENSHAN) + eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.KUNDI)) * eigenMatrix.getRange());
        }

        public float getRadius() {
            return (float) ((eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.DRYSKY) + eigenMatrix.get(MagicNodePropertyMatrix8By8.INDEX.SUNDAE)) * eigenMatrix.getRange());
        }
    }

    //multiThread rendering
}
