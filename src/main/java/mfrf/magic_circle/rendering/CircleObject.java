package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CircleObject extends MagicCircleComponentBase {
    private final float radius;
    private final float xRotateSpeedRadius;
    private final float yRotateSpeedRadius;
    private final float zRotateSpeedRadius;

    public CircleObject(float radius, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, float delay) {
        super(delay);
        this.radius = radius;
        this.xRotateSpeedRadius = xRotateSpeedRadius;
        this.yRotateSpeedRadius = yRotateSpeedRadius;
        this.zRotateSpeedRadius = zRotateSpeedRadius;
    }

    public CircleObject() {
        super();
        radius = 0;
        xRotateSpeedRadius = 0;
        yRotateSpeedRadius = 0;
        zRotateSpeedRadius = 0;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition, Matrix4f transformMatrix) {
//        float allLength = (float) (Math.PI * radius * 2);
//        float currentLength = (float) (time * Config.POLYGONS_RENDERING_SPEED.get() * radius * Math.PI * 2);
//        float actualLength = Math.min(currentLength, allLength);
//        List<Vector3f> circleArcPoints = getCircle(actualLength, time, actualPosition);
//
//
//        IVertexBuilder buffer = bufferIn.getBuffer(RenderType.LINES);
//        for (int i = 0; i < circleArcPoints.size() - 1; i++) {
//        matrixStackIn.push();
//            drawLine(Matrix4f.makeTranslate(actualPosition.getX(), actualPosition.getY(), actualPosition.getZ()), buffer, new Color(time * redGradient * i % 256, time * greenGradient * i % 256, time * blueGradient * i % 256, time * alphaGradient * i % 256), circleArcPoints.get(i), circleArcPoints.get(i + 1));
//        matrixStackIn.pop();
//        }
//
//        if (actualLength == allLength) {
//            return true;
//        }
        return false;
    }

    public List<Vector3f> getCircle(float length, float timePassed, Vector3f actualPosition) {
        ArrayList<Vector3f> points = new ArrayList<>();
        float v = length / Config.CURVE_PRECISION.get();
        for (float i = 0; i <= length; i += v) {
            Vector3f pos = new Vector3f((float) (radius * Math.cos(i)), (float) (radius * Math.sin(i)), 0);
            pos.add(actualPosition);
            points.add(MathUtil.RotationPoint(pos, xRotateSpeedRadius * timePassed, yRotateSpeedRadius * timePassed, zRotateSpeedRadius * timePassed));
        }
        return points;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("r", radius);
        compoundNBT.putFloat("xrot", xRotateSpeedRadius);
        compoundNBT.putFloat("yrot", yRotateSpeedRadius);
        compoundNBT.putFloat("zrot", zRotateSpeedRadius);
        return compoundNBT;
    }

    public static CircleObject deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("delay") && compoundNBT.contains("radius") && compoundNBT.contains("xrot") && compoundNBT.contains("yrot") && compoundNBT.contains("zrot")) {
            return new CircleObject(compoundNBT.getFloat("r"), compoundNBT.getFloat("xrot"), compoundNBT.getFloat("yrot"), compoundNBT.getFloat("zrot"), compoundNBT.getFloat("delay"));
        }
        return new CircleObject();
    }

}
