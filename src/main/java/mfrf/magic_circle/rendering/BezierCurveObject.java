package mfrf.magic_circle.rendering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mfrf.magic_circle.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

public class BezierCurveObject extends MagicCircleComponentBase<BezierCurveObject> {
    private ArrayList<Vector3f> points;

    public BezierCurveObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, Vector3f... points) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.points = new ArrayList<>(Arrays.asList(points));
    }

    public BezierCurveObject() {
        super();
        this.points = new ArrayList<>();
    }

    /**
     * It just work,I don't know why.
     * Copy from other website, and I modified some place.
     * Very poor readability.
     *
     * @param time current time of rendering
     * @return a list of point in bezier curve
     */
    public ArrayList<Vector3f> getBezierPoints(float time, Vector3f lookVec, Vector3f verticalVec) {
        ArrayList<Vector3f> bezierPointList = new ArrayList<>();
        Iterator<Vector3f> it = points.iterator();
        Float precision = Config.CURVE_PRECISION.get();

        int si = points.size();
        int s = si - 1;
        float[] f = sf(si);
        float[][] p = new float[si][3];
        float v = precision <= 0 ? 0.05f : precision;

        for (int j = 0; it.hasNext(); j++) {
            Vector3f q = it.next();
            p[j][0] = q.x();
            p[j][1] = q.y();
            p[j][2] = q.z();
        }

        float x0 = p[0][0];
        float y0 = p[0][1];
        float z0 = p[0][2];
        float x1, y1, z1;

        for (float t = 0; t <= time; t += v) {


            double xt = 0;
            double yt = 0;
            double zt = 0;

            for (int i = 0; i < si; i++) {
                double m = f[i] * Math.pow(t, i) * Math.pow(1 - t, s - i) * p[i][0];
                xt = xt + m;
            }

            for (int i = 0; i < si; i++) {
                double m = f[i] * Math.pow(t, i) * Math.pow(1 - t, s - i) * p[i][1];
                yt = yt + m;
            }

            for (int i = 0; i < si; i++) {
                double m = f[i] * Math.pow(t, i) * Math.pow(1 - t, s - i) * p[i][2];
                zt = zt + m;
            }

            x1 = (float) xt;
            y1 = (float) yt;
            z1 = (float) zt;

            Vector3f vector3f = new Vector3f(x0, y0, z0);
            if (rotateWithLookVec) {
                vector3f = getLookVecTransform(vector3f, lookVec, verticalVec);
            }
            vector3f.add(positionOffset);
            bezierPointList.add(vector3f);
            x0 = x1;
            y0 = y1;
            z0 = z1;
        }
        bezierPointList.add(points.get(points.size() - 1));

        return bezierPointList;
    }


    public static float[] sf(int nu) {
        int n = nu - 1;
        float[] list = new float[n + 1];
        list[0] = 1;
        for (int i = 1; i < n; i++) {
            float add = 1;
            for (int j = n, k = 1; k <= i; j--, k++) {
                add = add * j;
                add = add / k;
            }

            list[i] = add;
        }

        for (int i = 0; i < nu; i++) {
            if (list[i] == 0)
                list[i] = 1;
        }

        return list;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        ListNBT pointList = new ListNBT();
        compoundNBT.putInt("count", points.size());
        compoundNBT.putFloat("delay", delay);
        for (int i = 0; i < points.size(); i++) {
            CompoundNBT point = new CompoundNBT();
            Vector3f vector3f = points.get(i);
            point.putFloat("x", vector3f.x());
            point.putFloat("y", vector3f.y());
            point.putFloat("z", vector3f.z());
            pointList.add(i, point);
        }
        compoundNBT.put("points", pointList);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("count") && compoundNBT.contains("points") && compoundNBT.contains("delay")) {
            super.deserializeNBT(compoundNBT);
            int count = compoundNBT.getInt("count");
            ListNBT points = compoundNBT.getList("points", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < count; i++) {
                CompoundNBT compoundPoint = (CompoundNBT) points.get(i);
                this.points.add(new Vector3f(compoundPoint.getFloat("x"), compoundPoint.getFloat("y"), compoundPoint.getFloat("z")));
                //todo maybe we needn't nbt
            }
        }
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        Vector3f lookVecF = new Vector3f(lookVec);
        Vector3f verticalVecF = new Vector3f(verticalVec);
        float globalTime = getBezierPoints(1, lookVecF, verticalVecF).size() / 15f;
        float v = Math.max(0, time + trueTime - delay);
        float timePassed = Math.min(v / globalTime, 1);
        ArrayList<Vector3f> bezierPoints = getBezierPoints(timePassed >= 1 ? 1 : timePassed, lookVecF, verticalVecF);

//        ArrayList<Vector3f> bezierPoints = getBezierPoints(v >= 1 ? 1 : v);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Matrix4f matrix = matrixStackIn.last().pose();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_LINES);

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);


        curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, bezierPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(RenderTypes.MAGIC_CIRCLE_LINES);

        return v >= 1.0f;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        Vector3f lookVecF = new Vector3f(lookVec);
        Vector3f verticalVecF = new Vector3f(verticalVec);
        float globalTime = getBezierPoints(1, lookVecF, verticalVecF).size() / 15f;
        float v = Math.max(0, time + trueTime - delay);
        float timePassed = Math.min(v / globalTime, 1);
        ArrayList<Vector3f> bezierPoints = getBezierPoints(timePassed >= 1 ? 1 : timePassed, lookVecF, verticalVecF);

//        ArrayList<Vector3f> bezierPoints = getBezierPoints(v >= 1 ? 1 : v);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        Matrix4f matrix = matrixStackIn.last().pose();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_LINES);

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);


        curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, bezierPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(RenderTypes.MAGIC_CIRCLE_LINES);

        return v >= 1.0f;
    }


}
