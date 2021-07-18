package mfrf.magic_circle.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import mfrf.magic_circle.Config;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.HashMap;

public class PositionExpression {
    public int samplingCount = 100;
    public float samplingAccuracy = Config.CURVE_PRECISION.get();
    public String x = "0.0";
    public String y = "0.0";
    public String z = "0.0";
    public boolean hasTarget = false;
    public Vector3f targetVec = new Vector3f();
    //parameter is t

    public PositionExpression(@Nullable String x, @Nullable String y, @Nullable String z, @Nullable Float samplingAccuracy, @Nullable Integer samplingCount) {
        if (x != null) {
            this.x = "double(" + x + ")";
        }
        if (y != null) {
            this.y = "double(" + y + ")";
        }
        if (z != null) {
            this.z = "double(" + z + ")";
        }
        if (samplingAccuracy != null) {
            this.samplingAccuracy = samplingAccuracy;
        }
        if (samplingCount != null) {
            this.samplingCount = samplingCount;
        }
    }

    public PositionExpression(Vector3f vector3f) {
        this.x = Float.toString(vector3f.x() > Config.MAX_VELOCITY_OF_DANMAKU.get() ? 20f : vector3f.x());
        this.y = Float.toString(vector3f.y() > Config.MAX_VELOCITY_OF_DANMAKU.get() ? 20f : vector3f.y());
        this.z = Float.toString(vector3f.z() > Config.MAX_VELOCITY_OF_DANMAKU.get() ? 20f : vector3f.z());
    }

    public void setTargetVec(Vector3f vector3f) {
        targetVec = vector3f;
        hasTarget = true;
    }

    public Vector3f execute(Double t) throws ClassCastException {
        Expression xCompile = AviatorEvaluator.compile(x, true);
        Expression yCompile = AviatorEvaluator.compile(y, true);
        Expression zCompile = AviatorEvaluator.compile(z, true);
        HashMap<String, Object> env = new HashMap<>();
        env.put("t", t);
        env.put("hasTarget", hasTarget);
        env.put("targetX", targetVec.x());
        env.put("targetY", targetVec.y());
        env.put("targetZ", targetVec.z());
        float x = 0;
        float y = 0;
        float z = 0;

        try {
            x = (float) ((double) xCompile.execute(env));
            y = (float) ((double) yCompile.execute(env));
            z = (float) ((double) zCompile.execute(env));
            Float max = Config.MAX_VELOCITY_OF_DANMAKU.get();
            if (x > max) {
                x = max;
            }
            if (y > max) {
                y = max;
            }
            if (z > max) {
                z = max;
            }
        } catch (ClassCastException e) {
//            LogManager.getLogger().info("type error! your expression result is not a number! check your expression!");
            return null;
        }


        return new Vector3f(x, y, z);
    }

    public PositionExpression() {

    }
}
