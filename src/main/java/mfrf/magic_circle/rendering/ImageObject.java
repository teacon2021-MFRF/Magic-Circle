package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.MagicCircle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.awt.*;
import java.util.ArrayList;

public class ImageObject extends MagicCircleComponentBase<ImageObject> {
    protected ResourceLocation url;

    public ImageObject() {
        this.url = new ResourceLocation(MagicCircle.MOD_ID, "textures/magic_circle/magic_circle.png");
    }

    public ImageObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTime, ResourceLocation url) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius, renderTime);
        this.url = url;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        RenderType type = RenderTypes.slide(url);
        IVertexBuilder buffer = bufferIn.getBuffer(type);
        Color colorsAdd = getColorsAdd(time).toAWT();
        Matrix4f pose = matrixStackIn.last().pose();
        Quaternion apply = rotation.apply((int) time);

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//        matrixStackIn.translate(positionOffset.x(), positionOffset.y(), positionOffset.z());
        ArrayList<Vector3f> vector3fs = new ArrayList<>();
        vector3fs.add(new Vector3f(-0.5F, 1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(0.5F, 1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(0.5F, 1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(-0.5F, 1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(-0.5F, -1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(0.5F, -1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(0.5F, -1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(-0.5F, -1F / 256F, 0.5F));

        Vector3f look = new Vector3f(lookVec);
        Vector3f vert = new Vector3f(verticalVec);
        ArrayList<Vector3f> vector3fArrayList = new ArrayList<>();

        for (Vector3f f : vector3fs) {
            Vector3f copy = f.copy();

            Quaternion quaternion = makeRotate(time);
            quaternion.mul(apply);
            copy.transform(quaternion);

            copy.add(positionOffset);


            if (rotateWithLookVec) {
                copy = getLookVecTransform(copy, look, vert);
            }

            copy.transform(transform);
            vector3fArrayList.add(copy);
        }

        matrixStackIn.pushPose();
        RenderSystem.enableDepthTest();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        for (int i = 0; i < vector3fArrayList.size(); i += 4) {
            Vector3f vector3f1 = vector3fArrayList.get(i);
            Vector3f vector3f2 = vector3fArrayList.get(i + 1);
            Vector3f vector3f3 = vector3fArrayList.get(i + 2);
            Vector3f vector3f4 = vector3fArrayList.get(i + 3);
            buffer.vertex(pose, vector3f1.x(), vector3f1.y(), vector3f1.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 1F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f2.x(), vector3f2.y(), vector3f2.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 1F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f3.x(), vector3f3.y(), vector3f3.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 0F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f4.x(), vector3f4.y(), vector3f4.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 0F).uv2(maxLight).endVertex();
        }
        matrixStackIn.popPose();

        return time > renderTime;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        RenderType type = RenderTypes.slide(url);
        IVertexBuilder buffer = bufferIn.getBuffer(type);
        Color colorsAdd = getColorsAdd(time).toAWT();
        Matrix4f pose = matrixStackIn.last().pose();
        Quaternion apply = rotation.apply((int) time);

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
//        matrixStackIn.translate(positionOffset.x(), positionOffset.y(), positionOffset.z());
        ArrayList<Vector3f> vector3fs = new ArrayList<>();
        vector3fs.add(new Vector3f(-0.5F, 1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(0.5F, 1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(0.5F, 1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(-0.5F, 1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(-0.5F, -1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(0.5F, -1F / 256F, -0.5F));
        vector3fs.add(new Vector3f(0.5F, -1F / 256F, 0.5F));
        vector3fs.add(new Vector3f(-0.5F, -1F / 256F, 0.5F));

        Vector3f look = new Vector3f(lookVec);
        Vector3f vert = new Vector3f(verticalVec);
        ArrayList<Vector3f> vector3fArrayList = new ArrayList<>();

        for (Vector3f f : vector3fs) {
            Vector3f copy = f.copy();

            Quaternion quaternion = makeRotate(time);
            copy.transform(transform);
            quaternion.mul(apply);
            copy.transform(quaternion);

            copy.add(positionOffset);


            if (rotateWithLookVec) {
                copy = getLookVecTransform(copy, look, vert);
            }

            vector3fArrayList.add(copy);
        }

        matrixStackIn.pushPose();
        RenderSystem.enableDepthTest();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        for (int i = 0; i < vector3fArrayList.size(); i += 4) {
            Vector3f vector3f1 = vector3fArrayList.get(i);
            Vector3f vector3f2 = vector3fArrayList.get(i + 1);
            Vector3f vector3f3 = vector3fArrayList.get(i + 2);
            Vector3f vector3f4 = vector3fArrayList.get(i + 3);
            buffer.vertex(pose, vector3f1.x(), vector3f1.y(), vector3f1.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 1F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f2.x(), vector3f2.y(), vector3f2.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 1F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f3.x(), vector3f3.y(), vector3f3.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 0F).uv2(maxLight).endVertex();
            buffer.vertex(pose, vector3f4.x(), vector3f4.y(), vector3f4.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 0F).uv2(maxLight).endVertex();
        }
        matrixStackIn.popPose();

        return time > renderTime;
    }
}
