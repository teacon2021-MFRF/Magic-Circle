package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;

public class LineObject extends MagicCircleComponentBase<LineObject> {
    protected ArrayList<Vector3f> linePoints = new ArrayList<>();

    public LineObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTime) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius, renderTime);
    }

    public LineObject() {
        super();
    }

    public LineObject point(float x, float y, float z) {
        Vector3f copy = positionOffset.copy();
        copy.add(x, y, z);
        if (linePoints.size() != 0) {
            Vector3f lastPoint = linePoints.get(linePoints.size() - 1);
            linePoints.addAll(linearInsert(lastPoint, copy));
        }
        linePoints.add(copy);
        return this;
    }

    public LineObject close() {
        if (linePoints.size() != 0) {
            linePoints.addAll(linearInsert(linePoints.get(linePoints.size() - 1), linePoints.get(0)));
            linePoints.add(linePoints.get(0));
        }
        return this;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        boolean flag = time >= renderTime;

        if (!linePoints.isEmpty()) {
            float xRot = xRotateSpeedRadius == 0 ? 1 : time * xRotateSpeedRadius;
            float yRot = yRotateSpeedRadius == 0 ? 1 : time * yRotateSpeedRadius;
            float zRot = zRotateSpeedRadius == 0 ? 1 : time * zRotateSpeedRadius;
            Quaternion rot = rotation.copy();
            rot.mul(new Quaternion(xRot, yRot, zRot, true));
            float progressPerTick = (((float) linePoints.size() - 1) / (float) renderTime);
            ArrayList<Vector3f> actualPoints = flag ? this.linePoints : new ArrayList<>(linePoints.subList(0, Math.min((int) (progressPerTick * time), linePoints.size())));

            ArrayList<Vector3f> points = new ArrayList<>();

            for (Vector3f point : actualPoints) {
                Vector3f copy = point.copy();
                copy.transform(rot);
                if (rotateWithLookVec) {
                    copy = getLookVecTransform(copy, new Vector3f(lookVec), new Vector3f(verticalVec));
                }
                points.add(copy);
            }


            IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            Matrix4f matrix = matrixStackIn.last().pose();

            matrixStackIn.pushPose();

            Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

            IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_LINES);
            curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, points);

            matrixStackIn.popPose();
//            RenderSystem.disableDepthTest();
            buffer.endBatch(RenderTypes.MAGIC_CIRCLE_LINES);

        }
        return flag;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        boolean flag = time >= renderTime;

        if (!linePoints.isEmpty()) {
            float xRot = xRotateSpeedRadius == 0 ? 1 : time * xRotateSpeedRadius;
            float yRot = yRotateSpeedRadius == 0 ? 1 : time * yRotateSpeedRadius;
            float zRot = zRotateSpeedRadius == 0 ? 1 : time * zRotateSpeedRadius;
            Quaternion rot = rotation.copy();
            rot.mul(new Quaternion(xRot, yRot, zRot, true));
            float progressPerTick = (((float) linePoints.size() - 1) / (float) renderTime);
            ArrayList<Vector3f> actualPoints = flag ? this.linePoints : new ArrayList<>(linePoints.subList(0, Math.min((int) (progressPerTick * time), linePoints.size())));

            ArrayList<Vector3f> points = new ArrayList<>();

            for (Vector3f point : actualPoints) {
                Vector3f copy = point.copy();
                copy.transform(rot);
                if (rotateWithLookVec) {
                    copy = getLookVecTransform(copy, new Vector3f(lookVec), new Vector3f(verticalVec));
                }
                points.add(copy);
            }


            IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
            Matrix4f matrix = matrixStackIn.last().pose();

            matrixStackIn.pushPose();

            Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
            matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);

            IVertexBuilder builder = buffer.getBuffer(RenderTypes.MAGIC_CIRCLE_LINES);
            curve(builder, matrix, actualPosition, getColorsAdd(time).toAWT(), enableRGBGradient, enableAlphaGradient, points);

            matrixStackIn.popPose();
//            RenderSystem.disableDepthTest();
            buffer.endBatch(RenderTypes.MAGIC_CIRCLE_LINES);

        }
        return flag;
    }


}
