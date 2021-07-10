package mfrf.magic_circle.magicutil;

import java.util.ArrayList;
import java.util.function.BiFunction;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MagicStream {
    public ArrayList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> functions = new ArrayList<>();

    public MagicNodePropertyMatrix8By8 eigenMatrix = MagicNodePropertyMatrix8By8.IDENTITY.copy();
    public final MagicStreamInfo info;

    public MagicStream(MagicStreamInfo info) {
        this.info = info;
        MagicNodePropertyMatrix8By8.initPauliElements(eigenMatrix);
    }

    public void Matrixtimes(MagicNodePropertyMatrix8By8 matrix) {
        eigenMatrix = eigenMatrix.leftTimes(eigenMatrix);
    }

    public void apply() {
        for (BiFunction<MagicStream, MagicStreamInfo, MagicStream> function : functions) {
            function.apply(this, info);
        }
    }

    public static class MagicStreamInfo {
        public MagicStreamInfo(Invoker invoker, @Nullable MagicNodeBase lastNode, Receiver receiver) {
            this.invoker = invoker;
            this.lastNode = lastNode;
            this.receiver = receiver;
        }

        public Invoker invoker;
        public MagicNodeBase lastNode;
        public Receiver receiver;
    }

    //multiThread rendering
}
