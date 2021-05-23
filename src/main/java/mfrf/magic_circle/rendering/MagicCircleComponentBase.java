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
    protected static final float redGradient = 0.031f;
    protected static final float greenGradient = 0.023f;
    protected static final float blueGradient = 0.025f;
    protected static final float alphaGradient = 0.016f;
    protected static final double renderingSpeed = Config.POLYGONS_RENDERING_SPEED.get();
    protected float delay;

    public MagicCircleComponentBase(float delay) {
        this.delay = delay;
    }

    public MagicCircleComponentBase() {
        delay = 0;
    }

    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        if (trueTime + time - delay <= 0) {
            return false;
        } else {
            return renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
        }
    }

    protected abstract boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition);

    protected static void SingleLine(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, Vector3f positionBegin, Vector3f positionEnd, float r, float g, float b, float alpha) {
        builder.vertex(positionMatrix, pos.getX() + positionBegin.x(), pos.getY() + positionBegin.y(), pos.getZ() + positionBegin.z())
                .color(r, g, b, alpha)
                .endVertex();
        builder.vertex(positionMatrix, pos.getX() + positionEnd.x(), pos.getY() + positionEnd.y(), pos.getZ() + positionEnd.z())
                .color(r, g, b, alpha)
                .endVertex();
    }

    protected static void curve(IVertexBuilder builder, Matrix4f positionMatrix, Vector3f pos, float r, float g, float b, float alpha, boolean enableGradients, ArrayList<Vector3f> nodes) {
        int size = nodes.size();

        if (enableGradients) {
            float gradientR = ((size * redGradient - r) % 1.0f) / size;
            float gradientG = ((size * greenGradient - g) % 1.0f) / size;
            float gradientB = ((size * blueGradient - b) % 1.0f) / size;

            for (int i = 0; i < size; i++) {
                builder.vertex(positionMatrix, pos.x() + nodes.get(i).x(), pos.y() + nodes.get(i).y(), pos.z() + nodes.get(i).z())
                        .color((r + gradientR * i), (g + gradientG * i), (b + gradientB * i), alpha)
                        .endVertex();
            }

        } else {

            for (Vector3f node : nodes) {
                builder.vertex(positionMatrix, pos.x() + node.x(), pos.y() + node.y(), pos.z() + node.z())
                        .color((r), (g), (b), alpha)
                        .endVertex();
            }

        }

    }

    protected static void picture() {

    }
}
