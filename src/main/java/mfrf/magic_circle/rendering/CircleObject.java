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
import org.ejml.ops.CommonOps;

public class CircleObject extends MagicCircleComponentBase<CircleObject> {
    private float radius;
    private boolean layDown = false;


    public CircleObject(float radius, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, float delay) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.radius = radius;
    }

    public CircleObject() {
        super();
        radius = 0;
    }

    public CircleObject setLayDown() {
        layDown = true;
        return this;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        float allLength = (float) (Math.PI * radius * 2);
        double renderingTimeSum = allLength * renderingSpeed;
        float actualTime = trueTime + time;
        float v = (float) (actualTime / renderingTimeSum);
        float actualLength = (v >= 1 ? allLength : (allLength * v));


        ArrayList<Vector3f> circleArcPoints = getCircle(actualLength, actualTime, xRotateSpeedRadius + yRotateSpeedRadius + zRotateSpeedRadius != 0, new Vector3f(lookVec));

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);
        Matrix4f matrix = matrixStackIn.last().pose();

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        //todo fix gradient algorithm

        curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, circleArcPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(RenderTypes.MAGIC_CIRCLE_CLOSE_LINES);

        if (actualLength == allLength) {
            return true;
        }
        return false;
    }

    public ArrayList<Vector3f> getCircle(float length, float timePassed, boolean rotate, Vector3f direction) {
        ArrayList<Vector3f> points = new ArrayList<>();
        float v = Config.CURVE_PRECISION.get() / length;

        Vector3f beta_1 = direction.copy();
        beta_1.add(beta_1.x(), 2 * beta_1.y(), 3 * beta_1.z());
        Vector3f proj = direction.copy();
        proj.mul(beta_1.dot(direction) / direction.dot(direction));
        beta_1.sub(proj);
        beta_1.normalize();
        //this cound get the "error" of projection
        Vector3f beta_2 = direction.copy();
        beta_2.cross(beta_1);
        beta_2.normalize();
        //get another perpendicular vector


        for (float i = 0; i <= length; i += v) {

            float x = layDown ? (float) (radius * Math.cos(i)) : positionOffset.x() + (float) (radius * Math.cos(i)) * beta_1.x() + (float) (radius * Math.sin(i)) * beta_2.x();
            float y = layDown ? 0 : positionOffset.y() + (float) (radius * Math.cos(i)) * beta_1.y() + (float) (radius * Math.sin(i)) * beta_2.y();
            float z = layDown ? (float) (radius * Math.sin(i)) : positionOffset.z() + (float) (radius * Math.cos(i)) * beta_1.z() + (float) (radius * Math.sin(i)) * beta_2.z();

            Vector3f pos = new Vector3f(x, y, z);

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
        compoundNBT.putBoolean("lay_down", layDown);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("delay") && compoundNBT.contains("radius") && compoundNBT.contains("xrot") && compoundNBT.contains("yrot") && compoundNBT.contains("zrot") && compoundNBT.contains("lay_down")) {
            super.deserializeNBT(compoundNBT);
            this.radius = compoundNBT.getFloat("radius");
            layDown = compoundNBT.getBoolean("lay_down");
        }
    }

}
