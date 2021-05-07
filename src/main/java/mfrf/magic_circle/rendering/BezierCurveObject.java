package mfrf.magic_circle.rendering;

import mfrf.magic_circle.Config;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BezierCurveObject {
    private final List<Vector3f> points;

    public BezierCurveObject(Vector3f... points) {
        this.points = List.of(points);
    }

    /**
     * It just work,I don't know why.
     * Copy from other website, and I modified some place.
     * Very poor readability.
     *
     * @param time current time of rendering
     * @return a list of point in bezier curve
     */
    public ArrayList<Vector3f> bezierPoints(float time) {
        ArrayList<Vector3f> bezierPointList = new ArrayList<>();
        Iterator<Vector3f> it = points.iterator();
        Float precision = Config.CURVE_PRECISION.get();
        int si = points.size();
        int s = si - 1;
        float[] f = sf(si);
        float[][] p = new float[si][3];

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

        for (float t = 0; t <= time; t += 1.0 / precision <= 0 ? 1 : precision) {


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
        if (nbt.contains("count") && nbt.contains("points")) {
            int count = nbt.getInt("count");
            Vector3f[] vector3fArray = new Vector3f[count];
            ListNBT points = nbt.getList("points", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < count; i++) {
                CompoundNBT compoundPoint = (CompoundNBT) points.get(i);
                vector3fArray[i] = new Vector3f(compoundPoint.getFloat("x"), compoundPoint.getFloat("y"), compoundPoint.getFloat("z"));
            }
            return new BezierCurveObject(vector3fArray);
        }
        return new BezierCurveObject();
    }
}
