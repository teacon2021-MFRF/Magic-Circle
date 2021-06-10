package mfrf.magic_circle.util;

import net.minecraft.util.math.vector.Vector3f;

public class PositionExpression {
    public String x = "0";
    public String y = "0";
    public String z = "0";
    //parameter is t

    public PositionExpression(String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PositionExpression(Vector3f vector3f) {
        this.x = Float.toString(vector3f.x());
        this.y = Float.toString(vector3f.y());
        this.z = Float.toString(vector3f.z());
    }

    public PositionExpression() {

    }
}
