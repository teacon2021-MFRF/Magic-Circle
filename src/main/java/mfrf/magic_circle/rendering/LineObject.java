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

public class LineObject extends MagicCircleComponentBase {
    private float actualRenderTick = 0;
    protected ArrayList<Vector3f> points = new ArrayList<>();
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
        if (points.size() != 0) {
            Vector3f lastPoint = points.get(points.size() - 1);
            points.addAll(linearInsert(lastPoint, copy));
        }
        points.add(copy);
        return this;
    }

    public LineObject close() {
        if (points.size() != 0) {
            points.addAll(linearInsert(points.get(points.size() - 1), points.get(0)));
            points.add(points.get(0));
        }
        return this;
    }


    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();

        ListNBT listNBT = new ListNBT();

        for (int i = 0; i < points.size(); i++) {
            CompoundNBT point = new CompoundNBT();
            Vector3f vector3f = points.get(i);
            point.putFloat("x", vector3f.x());
            point.putFloat("y", vector3f.y());
            point.putFloat("z", vector3f.z());
            listNBT.add(i, point);
        }
        compoundNBT.putInt("count", points.size());
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
            this.points.add(new Vector3f(nbt.getFloat("x"), nbt.getFloat("y"), nbt.getFloat("z")));
        }
        renderTick = compoundNBT.getInt("render_tick");
        actualRenderTick = compoundNBT.getFloat("actual_render_tick");
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        float timePassed = Math.max(time + trueTime - delay, 0);
        boolean flag = timePassed >= actualRenderTick;
        if (!points.isEmpty()) {
            float xRot = xRotateSpeedRadius == 0 ? 1 : timePassed * xRotateSpeedRadius;
            float yRot = yRotateSpeedRadius == 0 ? 1 : timePassed * yRotateSpeedRadius;
            float zRot = zRotateSpeedRadius == 0 ? 1 : timePassed * zRotateSpeedRadius;
            Quaternion rot = rotation.copy();
            rot.mul(new Quaternion(xRot, yRot, zRot, true));
            float progressPerTick = (((float) points.size() - 1) / (float) renderTick);
            ArrayList<Vector3f> actualPoints = flag ? this.points : new ArrayList<>(points.subList(0, Math.min((int) (progressPerTick * timePassed), points.size())));

            ArrayList<Vector3f> points = new ArrayList<>();

            for (Vector3f point : actualPoints) {
                Vector3f copy = point.copy();
                copy.transform(rot);
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
            RenderSystem.disableDepthTest();
            buffer.endBatch(RenderTypes.MAGIC_CIRCLE_LINES);

        }
        return flag;
    }

}
