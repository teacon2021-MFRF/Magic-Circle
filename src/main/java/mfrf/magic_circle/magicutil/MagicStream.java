package mfrf.magic_circle.magicutil;

import java.util.ArrayList;
import java.util.function.BiFunction;

import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

public class MagicStream {
    public ArrayList<BiFunction<MagicStream, MagicStreamInfo, MagicStream>> functions = new ArrayList<>();
    @OnlyIn(Dist.CLIENT)
    public ArrayList<BiFunction<MagicCircleRenderBase, MagicStream, MagicCircleRenderBase>> renders = new ArrayList<>();

    public MagicNodePropertyMatrix8By8 eigenMatrix;
    @OnlyIn(Dist.CLIENT)
    public MagicCircleRenderBase magic_circle = new MagicCircleRenderBase();
    public final MagicStreamInfo info;

    public MagicStream(MagicStreamInfo info) {
        this.info = info;
        eigenMatrix = new MagicNodePropertyMatrix8By8();
        MagicNodePropertyMatrix8By8.initPauliElements(eigenMatrix);
    }

    public void Matrixtimes(MagicNodePropertyMatrix8By8 matrix) {
        eigenMatrix = eigenMatrix.leftTimes(eigenMatrix);
    }

    @OnlyIn(Dist.CLIENT)
    public void finishRender() {
        MagicCircleRenderBase previous = magic_circle;
        for (BiFunction<MagicCircleRenderBase, MagicStream, MagicCircleRenderBase> render : renders) {
            previous = render.apply(previous, this);
        }
    }

    public static class MagicStreamInfo {
        public Invoker invoker;
        public MagicNodeBase lastNode;
        public Receiver receiver;
    }

    //multiThread rendering
}
