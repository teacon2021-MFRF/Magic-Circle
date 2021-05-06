package mfrf.magic_circle.rendering;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class CircleObject {
    private final float radius;
    private final float xRotateSpeedRadius;
    private final float yRotateSpeedRadius;
    private final float zRotateSpeedRadius;

    public CircleObject(float radius, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, float selfRotateSpeed) {
        this.radius = radius;
        this.xRotateSpeedRadius = xRotateSpeedRadius;
        this.yRotateSpeedRadius = yRotateSpeedRadius;
        this.zRotateSpeedRadius = zRotateSpeedRadius;
    }

    public List<Vector3f> getCircle(float timePassed) {
        float length = (float) (timePassed * Config.POLYGONS_RENDERING_SPEED.get() * radius * Math.PI * 2);
        ArrayList<Vector3f> points = new ArrayList<>();
        float v = length / Config.CURVE_PRECISION.get();
        for (float i = 0; i <= length; i += v) {
            points.add(MathUtil.RotationPoint(new Vector3f((float) (radius * Math.cos(i)), (float) (radius * Math.sin(i)), 0), xRotateSpeedRadius * timePassed, yRotateSpeedRadius * timePassed, zRotateSpeedRadius * timePassed));
        }
        return points;
    }

}
