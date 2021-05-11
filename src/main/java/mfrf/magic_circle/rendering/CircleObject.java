package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.*;

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
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        float allLength = (float) (Math.PI * radius * 2);
        double renderingTimeSum = allLength * renderingSpeed;
//        float actualTime = (float) ((time + trueTime) % renderingTimeSum);
//        float actualTime = time + trueTime;
        float actualTime = trueTime + time;
//        float actualTime = (float) Math.min(renderingTimeSum, (time + trueTime) % renderingTimeSum);

//        float allLength = (float) (Math.PI * radius * 2);
//        float currentLength = (float) (actualTime * radius * Math.PI * 2);
//        float currentLength = (float) (actualTime * radius * Math.PI * 2 * renderingSpeed);
//        float actualLength = Math.min(allLength, currentLength);
        float v = (float) (actualTime / renderingTimeSum);
        float actualLength = (v >= 1 ? allLength : (allLength * v));
//        float actualLength = (float) (v >= 1 ? 2 * Math.PI : (2 * Math.PI * v));


        ArrayList<Vector3f> circleArcPoints = getCircle(actualLength, actualTime, xRotateSpeedRadius + yRotateSpeedRadius + zRotateSpeedRadius != 0);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);
        Matrix4f matrix = matrixStackIn.getLast().getMatrix();
//        Matrix4f matrix = Matrix4f.makeScale(1, 1, 1);

        matrixStackIn.push();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getActiveRenderInfo().getProjectedView();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

//        curve(builder, matrix, actualPosition, (float) (time * redGradient), (float) (time * greenGradient), (float) (time * blueGradient), (float) (time * alphaGradient), true, circleArcPoints);
//        curve(builder, matrix, actualPosition, (float) (actualTime * redGradient), (float) (actualTime * greenGradient), (float) (actualTime * blueGradient), 1, false, circleArcPoints);
        //todo fix gradient algorithm
        curve(builder, matrix, actualPosition, (float) (actualTime * redGradient * 10) % 1f, (float) (actualTime * greenGradient * 10) % 1f, (float) (actualTime * blueGradient * 10) % 1f, 1, false, circleArcPoints);

        matrixStackIn.pop();


        RenderSystem.disableDepthTest();
        buffer.finish(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);

        if (actualLength == allLength) {
            return true;
        }
        return false;
    }

    public ArrayList<Vector3f> getCircle(float length, float timePassed, boolean rotate) {
        ArrayList<Vector3f> points = new ArrayList<>();
//        float radiusPerStep = (float) (2 * Math.PI * Config.CURVE_PRECISION.get());
        float v = Config.CURVE_PRECISION.get() / length;

//        for (float i = 0; i <= 2 * Math.PI; i += radiusPerStep) {
//            Vector3f pos = new Vector3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));

        for (float i = 0; i <= length; i += v) {
            Vector3f pos = new Vector3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));

//            if (rotate) {
//                pos = MathUtil.RotationPoint(pos, xRotateSpeedRadius * timePassed, yRotateSpeedRadius * timePassed, zRotateSpeedRadius * timePassed);
            //todo fix rotate
//            }

            points.add(pos);
            //todo improve algorithm
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
