package mfrf.magic_circle.util;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.Rotations;
import net.minecraft.util.math.vector.*;
import org.ejml.ops.CommonOps;

public class MathUtil {
    public static final Matrix4f IDENTITY_MATRIX = Matrix4f.createScaleMatrix(1, 1, 1);

    private static double sin(float a) {
        return Math.sin(a);
    }

    private static double cos(float a) {
        return Math.cos(a);
    }
}
