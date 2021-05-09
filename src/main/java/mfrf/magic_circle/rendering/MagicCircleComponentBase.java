package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;

public abstract class MagicCircleComponentBase {
    protected static final float PRECISION = Config.CURVE_PRECISION.get();
    protected static final double redGradient = Config.RGB_RED_STEP.get();
    protected static final double greenGradient = Config.RGB_GREEN_STEP.get();
    protected static final double blueGradient = Config.RGB_BLUE_STEP.get();
    protected static final double alphaGradient = Config.RGB_ALPHA_STEP.get();
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

    protected static void SingleLine(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, Vector3f positionBegin, Vector3f positionEnd, float r, float g, float b, float alpha) {
        builder.pos(positionMatrix, pos.getX() + positionBegin.getX(), pos.getY() + positionBegin.getY(), pos.getZ() + positionBegin.getZ())
                .color(r, g, b, alpha)
                .endVertex();
        builder.pos(positionMatrix, pos.getX() + positionEnd.getX(), pos.getY() + positionEnd.getY(), pos.getZ() + positionEnd.getZ())
                .color(r, g, b, alpha)
                .endVertex();
    }

    protected static void curve(IVertexBuilder builder, Matrix4f positionMatrix, Vector3f pos, float r, float g, float b, float alpha, boolean enableGradients, ArrayList<Vector3f> nodes) {
        float gradientR = 0;
        float gradientG = 0;
        float gradientB = 0;
        float gradientAlpha = 0;
        for (int i = 0; i < nodes.size(); i++) {
            builder.pos(positionMatrix, pos.getX() + nodes.get(i).getX(), pos.getY() + nodes.get(i).getY(), pos.getZ() + nodes.get(i).getZ())
                    .color(r + gradientR, g + gradientG, b + gradientB, alpha + gradientAlpha)
                    .endVertex();
            if (enableGradients && (i + 1) % 2 == 0) {
                gradientR += (float) redGradient;
                gradientG += (float) greenGradient;
                gradientB += (float) blueGradient;
                gradientAlpha += (float) alphaGradient;
            }
        }
    }
}
