package mfrf.magic_circle.rendering;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

import com.googlecode.aviator.AviatorEvaluator;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.magicutil.nodes.behaviornode.ThrowBehaviorNode;
import mfrf.magic_circle.util.Colors;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.*;

import javax.annotation.Nullable;

public abstract class MagicCircleComponentBase<T extends MagicCircleComponentBase> {
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
    protected Colors color = Colors.YIN;
    protected boolean rotateWithLookVec = false;

    public T setPositionOffset(Vector3f positionOffset) {
        this.positionOffset = positionOffset;
        return (T) this;
    }

    public T setRotation(Quaternion rotation) {
        this.rotation = rotation;
        return (T) this;
    }

    public T setAlpha(int alpha) {
        this.defaultAlpha = alpha;
        return (T) this;
    }

    public T enableAlphaGradient() {
        enableAlphaGradient = true;
        return (T) this;
    }

    public T enableRGBGradient() {
        enableRGBGradient = true;
        return (T) this;
    }

    public T setColor(Colors color) {
        this.color = color;
        return (T) this;
    }

    public T setRotateWithLookVec() {
        this.rotateWithLookVec = true;
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
        compoundNBT.putBoolean("lay_down", rotateWithLookVec);
        return compoundNBT;
    }

    public void deserializeNBT(CompoundNBT compoundNBT) {
        this.delay = compoundNBT.getFloat("delay");
        this.xRotateSpeedRadius = compoundNBT.getFloat("xrot");
        this.yRotateSpeedRadius = compoundNBT.getFloat("yrot");
        this.zRotateSpeedRadius = compoundNBT.getFloat("zrot");
        rotateWithLookVec = compoundNBT.getBoolean("lay_down");
    }

    public boolean rendering(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        if (trueTime + time - delay <= 0) {
            return false;
        } else {
            return renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
        }
    }


    public Vector3f getLookVecTransform(float x, float y, float z, Vector3f lookVec) {
        Vector3f beta_1 = lookVec.copy();
        beta_1.add(beta_1.x(), 2 * beta_1.y(), 3 * beta_1.z());
        Vector3f proj = lookVec.copy();
        proj.mul(beta_1.dot(lookVec) / lookVec.dot(lookVec));
        beta_1.sub(proj);
        beta_1.normalize();
        //this cound get the "error" of projection
        Vector3f beta_2 = lookVec.copy();
        beta_2.cross(beta_1);
        beta_2.normalize();
        //get another perpendicular vector


        float vecX = beta_1.x() * x + lookVec.x() * y + beta_2.x() * z;
        float vecY = beta_1.y() * x + lookVec.y() * y + beta_2.y() * z;
        float vecZ = beta_1.z() * x + lookVec.z() * y + beta_2.z() * z;
        return new Vector3f(vecX, vecY, vecZ);

    }

    public Vector3f getLookVecTransform(Vector3f vector3f, Vector3f lookVec) {
        return getLookVecTransform(vector3f.x(), vector3f.y(), vector3f.z(), lookVec);
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

    public interface ISequenceRenderings {

        ArrayList<Vector3f> getPoints(float percent);

    }

    public static class Line implements ISequenceRenderings {
        private final float from;
        private final float to;

        public Line(float from, float to) {
            this.from = from > 0 ? 0 : from;
            this.to = to < 0 ? 0 : to;
        }

        @Override
        public ArrayList<Vector3f> getPoints(float percent) {
            ArrayList<Vector3f> vector3fs = new ArrayList<>();
            float finalPoint = (percent > 1 ? 1 : percent) * (to - from) + from;
            Vector3f fromVec = new Vector3f(from, 0, 0);
            Vector3f toVec = new Vector3f(finalPoint, 0, 0);
            vector3fs.add(fromVec);
            vector3fs.addAll(linearInsert(fromVec, toVec));
            vector3fs.add(toVec);
            return vector3fs;
        }
    }

    public static class BasicArrowHead implements ISequenceRenderings {
        private final float scale;

        public BasicArrowHead(float scale) {
            this.scale = scale;
        }

        @Override
        public ArrayList<Vector3f> getPoints(float percent) {
            ArrayList<Vector3f> vector3fs = new ArrayList<>();
            vector3fs.add(new Vector3f(scale * -0.5f, scale * -0.5f, 0));
            vector3fs.add(new Vector3f(scale * -0.5f, scale * 0.5f, 0));
            vector3fs.add(new Vector3f(scale, 0, 0));
            vector3fs.add(new Vector3f(scale * -0.5f, scale * -0.5f, 0));
            vector3fs.add(new Vector3f(scale * -0.5f, 0, 0));
            vector3fs.add(new Vector3f(scale * -0.5f, 0, scale * -0.5f));
            vector3fs.add(new Vector3f(scale * -0.5f, 0, scale * 0.5f));
            vector3fs.add(new Vector3f(scale, 0, 0));
            vector3fs.add(new Vector3f(scale * -0.5f, 0, scale * -0.5f));
            return vector3fs;
        }
    }

    public static class Axis implements ISequenceRenderings {
        private final DIRECTION direction;
        private final Line line;
        private final BasicArrowHead arrowHead;
        private final float labelDistance;

        public Axis(DIRECTION direction, Line line, BasicArrowHead arrowHead, float labelDistance) {
            this.direction = direction;
            this.line = line;
            this.arrowHead = arrowHead;
            this.labelDistance = labelDistance;
        }

        @Override
        public ArrayList<Vector3f> getPoints(float percent) {
            ArrayList<Vector3f> points = line.getPoints(percent);
            if (percent >= 1) {
                points.addAll(arrowHead.getPoints(1));
                float fromCount = line.from / labelDistance;
                for (int i = 0; i < fromCount; i++) {
                    points.add(new Vector3f(-labelDistance * i, 0.5f, 0));
                    points.add(new Vector3f(-labelDistance * i, -0.5f, 0));
                    points.add(new Vector3f(-labelDistance * i, 0, 0.5f));
                    points.add(new Vector3f(-labelDistance * i, 0, -0.5f));
                }
                float toCount = line.to / labelDistance;
                for (int i = 0; i < toCount; i++) {
                    points.add(new Vector3f(-labelDistance * i, 0.5f, 0));
                    points.add(new Vector3f(-labelDistance * i, -0.5f, 0));
                    points.add(new Vector3f(-labelDistance * i, 0, 0.5f));
                    points.add(new Vector3f(-labelDistance * i, 0, -0.5f));
                }
            }
            direction.rotateVectors(points);

            return points;
        }
    }

    public static class Coordinates implements ISequenceRenderings {
        private Axis X;
        private Axis Y;
        private Axis Z;
        private PositionExpression function;
        // paramater is t

        public Coordinates(@Nullable Axis x, @Nullable Axis y, @Nullable Axis z, @Nullable PositionExpression function) {
            X = x;
            Y = y;
            Z = z;
            this.function = function;
        }

        @Override
        public ArrayList<Vector3f> getPoints(float percent) {
            ArrayList<Vector3f> points = new ArrayList<>();
            if (X != null) {
                points.addAll(X.getPoints(percent));
            }
            if (Y != null) {
                points.addAll(Y.getPoints(percent));
            }
            if (Z != null) {
                points.addAll(Z.getPoints(percent));
            }

            if (function != null) {
                for (float i = 0; i < percent; i += PRECISION) {
                    HashMap<String, Object> env = new HashMap<>();
                    env.put("t", i);
                    points.add(new Vector3f((Float) AviatorEvaluator.execute(function.x, env), (Float) AviatorEvaluator.execute(function.y, env), (Float) AviatorEvaluator.execute(function.z, env)));
                }
            }
            return points;
        }

    }

    public enum DIRECTION {
        X(Matrix3f.createScaleMatrix(1, 1, 1)), Y(new Matrix3f(new Quaternion(0, 0, 90, true))), Z(new Matrix3f(new Quaternion(0, 90, 0, true)));

        private Matrix3f rotate;

        DIRECTION(Matrix3f rotate) {
            this.rotate = rotate;
        }

        /**
         * @param vector3f will be changed
         * @return
         */
        public Vector3f Rotate(Vector3f vector3f) {
            vector3f.transform(rotate);
            return vector3f;
        }

        public ArrayList<Vector3f> rotateVectors(ArrayList<Vector3f> vector3fs) {
            for (Vector3f vector3f : vector3fs) {
                vector3f.transform(rotate);
            }
            return vector3fs;
        }
    }
}
