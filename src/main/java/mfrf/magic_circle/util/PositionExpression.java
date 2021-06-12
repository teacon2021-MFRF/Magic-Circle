package mfrf.magic_circle.util;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import mfrf.magic_circle.Config;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.logging.log4j.LogManager;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class PositionExpression {
    public int samplingCount = 100;
    public float samplingAccuracy = Config.CURVE_PRECISION.get();
    public String x = "0.0";
    public String y = "0.0";
    public String z = "0.0";
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
        this.x = Float.toString(vector3f.x());
        this.y = Float.toString(vector3f.y());
        this.z = Float.toString(vector3f.z());
    }

    public Vector3f execute(Double t) throws ClassCastException {
        Expression xCompile = AviatorEvaluator.compile(x, true);
        Expression yCompile = AviatorEvaluator.compile(y, true);
        Expression zCompile = AviatorEvaluator.compile(z, true);
        HashMap<String, Object> env = new HashMap<>();
        env.put("t", t);
        float x = 0;
        float y = 0;
        float z = 0;

        try {
            x = (float) ((double) xCompile.execute(env));
            y = (float) ((double) yCompile.execute(env));
            z = (float) ((double) zCompile.execute(env));
        } catch (ClassCastException e) {
//            LogManager.getLogger().info("type error! your expression result is not a number! check your expression!");
            return null;
        }

        return new Vector3f(x, y, z);
    }

    public PositionExpression() {

    }
}
