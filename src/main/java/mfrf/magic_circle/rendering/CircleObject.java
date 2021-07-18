package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;

public class CircleObject extends MagicCircleComponentBase<CircleObject> {
    private float radius;


    public CircleObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTime, float radius) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius, renderTime);
        this.radius = radius;
    }

    public CircleObject() {
        super();
        radius = 0;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        boolean flag = time >= renderTime;
        float percent = flag ? 1 : (time / renderTime);
        float timePassed = flag ? renderTime : time;


        ArrayList<Vector3f> circleArcPoints = getCircle(percent, timePassed, xRotateSpeedRadius + yRotateSpeedRadius + zRotateSpeedRadius != 0, new Vector3f(lookVec), new Vector3f(verticalVec));

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderType renderType = RenderTypes.makeCircleLine(lineWidth);
        IVertexBuilder builder = buffer.getBuffer(renderType);
        Matrix4f matrix = matrixStackIn.last().pose();

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, circleArcPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(renderType);

        return flag;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        boolean flag = time >= renderTime;
        float percent = flag ? 1 : (time / renderTime);
        float timePassed = flag ? renderTime : time;


        ArrayList<Vector3f> circleArcPoints = getCircle(percent, timePassed, xRotateSpeedRadius + yRotateSpeedRadius + zRotateSpeedRadius != 0, new Vector3f(lookVec), new Vector3f(verticalVec));

        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderType renderType = RenderTypes.makeCircleLine(lineWidth);
        IVertexBuilder builder = buffer.getBuffer(renderType);
        Matrix4f matrix = matrixStackIn.last().pose();

        matrixStackIn.pushPose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

        curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, circleArcPoints);

        matrixStackIn.popPose();


        RenderSystem.disableDepthTest();
        buffer.endBatch(renderType);

        return flag;
    }

    public ArrayList<Vector3f> getCircle(float percent, float timePassed, boolean rotate, Vector3f direction, Vector3f verticalVec) {
        ArrayList<Vector3f> points = new ArrayList<>();
        float actualAngle = (float) (percent * Math.PI * 2);
        float v = Config.CURVE_PRECISION.get() / actualAngle;


        for (float i = 0; i <= actualAngle; i += v) {

            float x = (float) (radius * Math.cos(i));
            float y = 0;
            float z = (float) (radius * Math.sin(i));

            if (rotateWithLookVec) {
                Vector3f lookVecTransform = getLookVecTransform(x, y, z, direction, verticalVec);
                x = lookVecTransform.x();
                y = lookVecTransform.y();
                z = lookVecTransform.z();
            }
            Vector3f pos = new Vector3f(x, y, z);

            if (rotate) {
                Quaternion copy = rotation.copy();
                copy.mul(new Quaternion(xRotateSpeedRadius * timePassed, yRotateSpeedRadius * timePassed, zRotateSpeedRadius * timePassed, true));
                pos.transform(copy);
            }

            pos.add(positionOffset);
            pos.transform(transform);
            points.add(pos);
        }
        if (percent == 1 && !points.isEmpty()) {
            points.add(points.get(0));
        }
        return points;
    }


}
