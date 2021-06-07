package mfrf.magic_circle.rendering;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import mfrf.magic_circle.Config;
import mfrf.magic_circle.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class CircleObject extends MagicCircleComponentBase {
    private float radius;


    public CircleObject(float radius, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, float delay) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.radius = radius;
    }

    public CircleObject() {
        super();
        radius = 0;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        float allLength = (float) (Math.PI * radius * 2);
        double renderingTimeSum = allLength * renderingSpeed;
        float actualTime = trueTime + time;
        float v = (float) (actualTime / renderingTimeSum);
        float actualLength = (v >= 1 ? allLength : (allLength * v));


        ArrayList<Vector3f> circleArcPoints = getCircle(actualLength, actualTime, xRotateSpeedRadius + yRotateSpeedRadius + zRotateSpeedRadius != 0);

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);
        Matrix4f matrix = matrixStackIn.last().pose();

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        //todo fix gradient algorithm

        Colors add = color;
        if (enableRGBGradient) {
            add = color.add(
                    (int) ((time * redGradient) % 255f),
                    (int) ((time * greenGradient) % 255f),
                    (int) ((time * blueGradient) % 255f)
            );
        }

        curve(builder, matrix, actualPosition, add.toAWT(), enableRGBGradient, enableAlphaGradient, circleArcPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);

        if (actualLength == allLength) {
            return true;
        }
        return false;
    }

    public ArrayList<Vector3f> getCircle(float length, float timePassed, boolean rotate) {
        ArrayList<Vector3f> points = new ArrayList<>();
        float v = Config.CURVE_PRECISION.get() / length;


        for (float i = 0; i <= length; i += v) {
            Vector3f pos = new Vector3f((float) (radius * Math.cos(i)), 0, (float) (radius * Math.sin(i)));

            if (rotate) {
                Quaternion copy = rotation.copy();
                copy.mul(new Quaternion(xRotateSpeedRadius * timePassed, yRotateSpeedRadius * timePassed, zRotateSpeedRadius * timePassed, true));
                pos.transform(copy);
            }

            pos.add(positionOffset);
            points.add(pos);
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

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("delay") && compoundNBT.contains("radius") && compoundNBT.contains("xrot") && compoundNBT.contains("yrot") && compoundNBT.contains("zrot")) {
            super.deserializeNBT(compoundNBT);
            this.radius = compoundNBT.getFloat("radius");
        }
    }

}
