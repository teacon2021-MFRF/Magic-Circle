package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;

public abstract class MagicCircleComponentBase {
    protected static final int redGradient = Config.RGB_RED_STEP.get();
    protected static final int greenGradient = Config.RGB_GREEN_STEP.get();
    protected static final int blueGradient = Config.RGB_BLUE_STEP.get();
    protected static final int alphaGradient = Config.RGB_ALPHA_STEP.get();
    protected float delay;

    public MagicCircleComponentBase(float delay) {
        this.delay = delay;
    }

    public MagicCircleComponentBase() {
        delay = 0;
    }

    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition, Matrix4f translate) {
        if (trueTime + time - delay <= 0) {
            return false;
        } else {
            return renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition, translate);
        }
    }

    protected abstract boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition, Matrix4f transformMatrix);

    protected void drawLine(Matrix4f matrixPos, IVertexBuilder renderBuffer, Color color, Vector3f startVertex, Vector3f endVertex) {
        renderBuffer.pos(matrixPos, startVertex.getX(), startVertex.getY(), startVertex.getZ())
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
                .endVertex();
        renderBuffer.pos(matrixPos, endVertex.getX(), endVertex.getY(), endVertex.getZ())
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())   // there is also a version for floats (0 -> 1)
                .endVertex();
        //todo fix it
    }
}
