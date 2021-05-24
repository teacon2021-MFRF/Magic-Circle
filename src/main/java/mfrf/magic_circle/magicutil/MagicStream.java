package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;

import java.util.ArrayList;
import java.util.function.BiFunction;

public class MagicStream {
    public ArrayList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> functions = new ArrayList<>();
    public ArrayList<MagicCircleRenderBase> renderingProgress = new ArrayList<>();

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

    public static class MagicStreamInfo {
        public Invoker invoker;
        public MagicNodeBase lastNode;
        public Receiver receiver;
    }

    //multiThread rendering

    public enum Priority {
        FIRST, MIDDLE, LAST;
    }
}
