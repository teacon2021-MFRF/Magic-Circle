package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import net.minecraft.util.math.vector.Vector3f;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.BiFunction;

public class MagicStream {
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_FIRST = new LinkedList<>();
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_MIDDLE = new LinkedList<>();
    public LinkedList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> PRIORITY_LAST = new LinkedList<>();

    public MagicNodePropertyMatrix8By8 eigenMatrix;
    public final MagicStreamInfo info;

    public MagicStream(MagicStreamInfo info) {
        this.info = info;
        eigenMatrix = new MagicNodePropertyMatrix8By8();
        MagicNodePropertyMatrix8By8.initPauliElements(eigenMatrix);
    }

    public void Matrixtimes(MagicNodePropertyMatrix8By8 matrix) {
        eigenMatrix = eigenMatrix.leftTimes(eigenMatrix);
    }

    public static record MagicStreamInfo(Invoker invoker, MagicNodeBase lastNode, Receiver receiver) {
    }

    public enum Priority {
        FIRST, MIDDLE, LAST;
    }
}
