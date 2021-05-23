package mfrf.magic_circle.util;

import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;

public class MathUtil {
    public static final Matrix4f IDENTITY_MATRIX = Matrix4f.createScaleMatrix(1, 1, 1);

    public static Vector3f RotationPoint(Vector3f point, float angleX, float angleY, float angleZ) {
        return new Vector3f(
                (float) (point.x() * cos(angleX) * cos(angleY) + cos(angleX) * sin(angleY) * sin(angleZ) - sin(angleX) * cos(angleZ) + cos(angleX) * cos(angleZ) * sin(angleY) + sin(angleX) * sin(angleZ)),
                (float) (point.y() * sin(angleX) * cos(angleY) + sin(angleX) * sin(angleY) * sin(angleZ) + cos(angleX) * cos(angleZ) + sin(angleX) * sin(angleY) * cos(angleZ) - cos(angleX) * sin(angleZ)),
                (float) (point.z() * -sin(angleY) + cos(angleY) * sin(angleZ) + cos(angleY) * cos(angleZ))
        );
    }

    private static double sin(float a) {
        return Math.sin(a);
    }

    private static double cos(float a) {
        return Math.cos(a);
    }
}
