package mfrf.magic_circle.rendering;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import icyllis.modernui.math.Vector3;
import mfrf.magic_circle.Config;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;

public abstract class MagicCircleComponentBase {
    protected static final float PRECISION = Config.CURVE_PRECISION.get();
    protected static final float redGradient = 0.031f;
    protected static final float greenGradient = 0.023f;
    protected static final float blueGradient = 0.025f;
    protected static final float alphaGradient = 0.016f;
    protected static final double renderingSpeed = Config.POLYGONS_RENDERING_SPEED.get();

    protected float delay;
    protected float xRotateSpeedRadius;
    protected float yRotateSpeedRadius;
    protected float zRotateSpeedRadius;

    public MagicCircleComponentBase() {
        xRotateSpeedRadius = 0;
        yRotateSpeedRadius = 0;
        zRotateSpeedRadius = 0;
        delay = 0;
    }

    public MagicCircleComponentBase(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius) {

    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("delay", delay);
        compoundNBT.putFloat("xrot", xRotateSpeedRadius);
        compoundNBT.putFloat("yrot", yRotateSpeedRadius);
        compoundNBT.putFloat("zrot", zRotateSpeedRadius);
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.delay = compoundNBT.getFloat("delay");
        this.xRotateSpeedRadius = compoundNBT.getFloat("xrot");
        this.yRotateSpeedRadius = compoundNBT.getFloat("yrot");
        this.zRotateSpeedRadius = compoundNBT.getFloat("zrot");
    }

    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3 actualPosition) {
        if (trueTime + time - delay <= 0) {
            return false;
        } else {
            return renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
        }
    }

    protected abstract boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3 actualPosition);

    protected static void SingleLine(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, Vector3 positionBegin, Vector3 positionEnd, float r, float g, float b, float alpha) {
        builder.vertex(positionMatrix, pos.getX() + positionBegin.x, pos.getY() + positionBegin.y, pos.getZ() + positionBegin.z)
                .color(r, g, b, alpha)
                .endVertex();
        builder.vertex(positionMatrix, pos.getX() + positionEnd.x, pos.getY() + positionEnd.y, pos.getZ() + positionEnd.z)
                .color(r, g, b, alpha)
                .endVertex();
    }

    protected static void curve(IVertexBuilder builder, Matrix4f positionMatrix, Vector3 pos, float r, float g, float b, float alpha, boolean enableGradients, ArrayList<Vector3> nodes) {
        int size = nodes.size();

        if (enableGradients) {
            float gradientR = ((size * redGradient - r) % 1.0f) / size;
            float gradientG = ((size * greenGradient - g) % 1.0f) / size;
            float gradientB = ((size * blueGradient - b) % 1.0f) / size;

            for (int i = 0; i < size; i++) {
                builder.vertex(positionMatrix, pos.x + nodes.get(i).x, pos.y + nodes.get(i).y, pos.z + nodes.get(i).z)
                        .color((r + gradientR * i), (g + gradientG * i), (b + gradientB * i), alpha)
                        .endVertex();
            }

        } else {

            for (Vector3 node : nodes) {
                builder.vertex(positionMatrix, pos.x + node.x, pos.y + node.y, pos.z + node.z)
                        .color((r), (g), (b), alpha)
                        .endVertex();
            }

        }

    }

    protected static void picture() {

    }
}
