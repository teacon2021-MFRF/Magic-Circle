package mfrf.magic_circle.rendering;

import java.awt.*;
import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.util.Colors;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

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
    protected Vector3f positionOffset = new Vector3f(0, 0, 0);
    protected Quaternion rotation = Quaternion.ONE;
    protected boolean enableAlphaGradient = false;
    protected int defaultAlpha = 255;
    protected boolean enableRGBGradient = false;
    protected Colors color = Colors.YANG;

    public <T extends MagicCircleComponentBase> T setPositionOffset(Vector3f positionOffset) {
        this.positionOffset = positionOffset;
        return (T) this;
    }

    public <T extends MagicCircleComponentBase> T setRotation(Quaternion rotation) {
        this.rotation = rotation;
        return (T) this;
    }

    public <T extends MagicCircleComponentBase> T setAlpha(int alpha) {
        this.defaultAlpha = alpha;
        return (T) this;
    }

    public <T extends MagicCircleComponentBase> T enableAlphaGradient() {
        enableAlphaGradient = true;
        return (T) this;
    }

    public <T extends MagicCircleComponentBase> T enableRGBGradient() {
        enableRGBGradient = true;
        return (T) this;
    }

    public <T extends MagicCircleComponentBase> T setColor(Colors color) {
        this.color = color;
        return (T) this;
    }

    public MagicCircleComponentBase() {
        xRotateSpeedRadius = 0;
        yRotateSpeedRadius = 0;
        zRotateSpeedRadius = 0;
        delay = 0;
    }

    public MagicCircleComponentBase(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius) {
        this.delay = delay;
        this.xRotateSpeedRadius = xRotateSpeedRadius;
        this.yRotateSpeedRadius = yRotateSpeedRadius;
        this.zRotateSpeedRadius = zRotateSpeedRadius;
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

    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        if (trueTime + time - delay <= 0) {
            return false;
        } else {
            return renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
        }
    }

    protected abstract boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition);

    protected Colors getColorsAdd(float time) {
        Colors add = color;
        if (enableRGBGradient) {
            add = color.add(
                    (int) ((time * redGradient) % 255f),
                    (int) ((time * greenGradient) % 255f),
                    (int) ((time * blueGradient) % 255f)
            );
        }
        return add;
    }


    protected static void SingleLine(IVertexBuilder builder, Matrix4f positionMatrix, BlockPos pos, Vector3f positionBegin, Vector3f positionEnd, float r, float g, float b, float alpha) {
        builder.vertex(positionMatrix, pos.getX() + positionBegin.x(), pos.getY() + positionBegin.y(), pos.getZ() + positionBegin.z())
                .color(r, g, b, alpha)
                .endVertex();
        builder.vertex(positionMatrix, pos.getX() + positionEnd.x(), pos.getY() + positionEnd.y(), pos.getZ() + positionEnd.z())
                .color(r, g, b, alpha)
                .endVertex();
    }

    protected static void curve(IVertexBuilder builder, Matrix4f positionMatrix, Vector3f pos, Color color, boolean enableRGBGradients, boolean enableAlphaGradients, ArrayList<Vector3f> nodes) {
        int size = nodes.size();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        float gradientR = 0;
        float gradientG = 0;
        float gradientB = 0;
        float alphaGradients = 0;

        if (enableRGBGradients) {
            gradientR = ((size * redGradient - r) % 255f) / size;
            gradientG = ((size * greenGradient - g) % 255f) / size;
            gradientB = ((size * blueGradient - b) % 255f) / size;
        }

        if (enableAlphaGradients) {
            alphaGradients = ((size * alphaGradient - b) % 255f);
        }

        for (int i = 0; i < size; i++) {
            builder.vertex(positionMatrix, pos.x() + nodes.get(i).x(), pos.y() + nodes.get(i).y(), pos.z() + nodes.get(i).z())
                    .color((int) (r + gradientR * i), (int) (g + gradientG * i), (int) (b + gradientB * i), (int) (alpha + alphaGradients * i))
                    .endVertex();
        }

    }

    protected static ArrayList<Vector3f> linearInsert(Vector3f p1, Vector3f p2) {
        Vector3f vecOffset = p2.copy();
        vecOffset.sub(p1);

        vecOffset.mul(PRECISION);
        ArrayList<Vector3f> insertedPoints = new ArrayList<>();

        for (int i = 1; i < 1 / PRECISION; i++) {
            Vector3f copy = p1.copy();
            Vector3f offsetCurrent = vecOffset.copy();
            offsetCurrent.mul(i);
            copy.add(offsetCurrent);
            insertedPoints.add(copy);
        }

        return insertedPoints;
    }

    protected static void picture() {

    }
}
