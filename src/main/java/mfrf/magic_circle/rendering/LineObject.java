package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class LineObject extends MagicCircleComponentBase<LineObject> {
    private float actualRenderTick = 0;
    protected ArrayList<Vector3f> linepoints = new ArrayList<>();
    protected int renderTick = 0;

    public LineObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTick) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.renderTick = renderTick;
        this.actualRenderTick = renderTick + delay;
    }

    public LineObject() {
        super();
    }

    public LineObject point(float x, float y, float z) {
        Vector3f copy = positionOffset.copy();
        copy.add(x, y, z);
        if (linepoints.size() != 0) {
            Vector3f lastPoint = linepoints.get(linepoints.size() - 1);
            linepoints.addAll(linearInsert(lastPoint, copy));
        }
        linepoints.add(copy);
        return this;
    }

    public LineObject close() {
        if (linepoints.size() != 0) {
            linepoints.addAll(linearInsert(linepoints.get(linepoints.size() - 1), linepoints.get(0)));
            linepoints.add(linepoints.get(0));
        }
        return this;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();

        ListNBT listNBT = new ListNBT();

        for (int i = 0; i < linepoints.size(); i++) {
            CompoundNBT point = new CompoundNBT();
            Vector3f vector3f = linepoints.get(i);
            point.putFloat("x", vector3f.x());
            point.putFloat("y", vector3f.y());
            point.putFloat("z", vector3f.z());
            listNBT.add(i, point);
        }
        compoundNBT.putInt("count", linepoints.size());
        compoundNBT.put("points", listNBT);
        compoundNBT.putInt("render_tick", renderTick);
        compoundNBT.putFloat("actual_render_tick", actualRenderTick);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        super.deserializeNBT(compoundNBT);
        ListNBT listNBT = compoundNBT.getList("points", Constants.NBT.TAG_COMPOUND);
        int count = compoundNBT.getInt("count");
        for (int i = 0; i < count; i++) {
            CompoundNBT nbt = (CompoundNBT) listNBT.get(i);
            this.linepoints.add(new Vector3f(nbt.getFloat("x"), nbt.getFloat("y"), nbt.getFloat("z")));
        }
        renderTick = compoundNBT.getInt("render_tick");
        actualRenderTick = compoundNBT.getFloat("actual_render_tick");
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        float timePassed = Math.max(time + trueTime - delay, 0);
        boolean flag = timePassed >= actualRenderTick;
        if (!linepoints.isEmpty()) {
            float xRot = xRotateSpeedRadius == 0 ? 1 : timePassed * xRotateSpeedRadius;
            float yRot = yRotateSpeedRadius == 0 ? 1 : timePassed * yRotateSpeedRadius;
            float zRot = zRotateSpeedRadius == 0 ? 1 : timePassed * zRotateSpeedRadius;
            Quaternion rot = rotation.copy();
            rot.mul(new Quaternion(xRot, yRot, zRot, true));
            float progressPerTick = (((float) linepoints.size() - 1) / (float) renderTick);
            ArrayList<Vector3f> actualPoints = flag ? this.linepoints : new ArrayList<>(linepoints.subList(0, Math.min((int) (progressPerTick * timePassed), linepoints.size())));

            ArrayList<Vector3f> points = new ArrayList<>();

            for (Vector3f point : actualPoints) {
                Vector3f copy = point.copy();
                copy.transform(rot);
                if (rotateWithLookVec) {
                    copy = getLookVecTransform(copy, new Vector3f(lookVec));
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
