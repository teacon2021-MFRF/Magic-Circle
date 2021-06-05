package mfrf.magic_circle.util;

import icyllis.modernui.math.Vector3;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class MathUtil {
    public static final Matrix4f IDENTITY_MATRIX = Matrix4f.createScaleMatrix(1, 1, 1);

    public static Vector3 castVec3f(Vector3f vector3f) {
        return new Vector3(vector3f.x(), vector3f.y(), vector3f.z());
    }

    public static Vector3 castVec3d(Vector3d vector3d) {
        return new Vector3(((float) vector3d.x), ((float) vector3d.y), ((float) vector3d.z));
    }
}
