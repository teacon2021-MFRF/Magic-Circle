package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class BezierCurveObject extends MagicCircleComponentBase {
    private final List<Vector3f> points;

    public BezierCurveObject(float delay, Vector3f... points) {
        super(delay);
        this.points = List.of(points);
    }

    public BezierCurveObject(Vector3f... points) {
        super();
        this.points = Arrays.asList(points);
    }

    /**
     * It just work,I don't know why.
     * Copy from other website, and I modified some place.
     * Very poor readability.
     *
     * @param time current time of rendering
     * @return a list of point in bezier curve
     */
    public ArrayList<Vector3f> getBezierPoints(float time) {
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
            p[j][0] = q.getX();
            p[j][1] = q.getY();
            p[j][2] = q.getZ();
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
            bezierPointList.add(new Vector3f(x0, y0, z0));
            x0 = x1;
            y0 = y1;
            z0 = z1;
        }

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
            point.putFloat("x", vector3f.getX());
            point.putFloat("y", vector3f.getY());
            point.putFloat("z", vector3f.getZ());
            pointList.add(i, point);
        }
        compoundNBT.put("points", pointList);

        return compoundNBT;
    }

    public static BezierCurveObject deserializeNBT(CompoundNBT nbt) {
        if (nbt.contains("count") && nbt.contains("points") && nbt.contains("delay")) {
            int count = nbt.getInt("count");
            float delay = nbt.getFloat("delay");
            Vector3f[] vector3fArray = new Vector3f[count];
            ListNBT points = nbt.getList("points", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < count; i++) {
                CompoundNBT compoundPoint = (CompoundNBT) points.get(i);
                vector3fArray[i] = new Vector3f(compoundPoint.getFloat("x"), compoundPoint.getFloat("y"), compoundPoint.getFloat("z"));
            }
            return new BezierCurveObject(delay, vector3fArray);
        }
        return new BezierCurveObject();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition, Matrix4f transformMatrix) {
//        float timeStep = (time + trueTime) % 1.0f;
        float timePassed = 1.0f;
        ArrayList<Vector3f> bezierPoints = getBezierPoints(timePassed >= 1 ? 1 : timePassed);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        Matrix4f matrix = matrixStackIn.getLast().getMatrix();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_LINES);

        matrixStackIn.push();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

//            curve(builder, matrix, actualPosition, (float) (time * redGradient), (float) (time * greenGradient), (float) (time * blueGradient), (float) (time * alphaGradient), false, bezierPoints);
        curve(builder, matrix, actualPosition, (float) (time * redGradient), (float) (time * greenGradient), (float) (time * blueGradient), 1, false, bezierPoints);

        matrixStackIn.pop();


        RenderSystem.disableDepthTest();
        buffer.finish(RenderTypes.MAGIC_CIRCLE_LINES);

        if (timePassed >= 1) {
            return true;
        }

        return false;
    }
}
