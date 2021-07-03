package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.util.Colors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
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
        this.url = new ResourceLocation(MagicCircle.MOD_ID, "textures/danmaku/test_danmaku.png");
    }

    public ImageObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTime, ResourceLocation url) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius, renderTime);
        this.url = url;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        return false;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        RenderType type = RenderTypes.slide(url);
        IVertexBuilder buffer = bufferIn.getBuffer(type);
        Color colorsAdd = getColorsAdd(time).toAWT();
        Matrix4f pose = matrixStackIn.last().pose();

        Vector3d projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);
        matrixStackIn.translate(positionOffset.x(), positionOffset.y(), positionOffset.z());
        ArrayList<Vector3f> vector3fs = new ArrayList<>();
        vector3fs.add(new Vector3f(0F, 1F / 256F, 1F));
        vector3fs.add(new Vector3f(1F, 1F / 256F, 1F));
        vector3fs.add(new Vector3f(1F, 1F / 256F, 0F));
        vector3fs.add(new Vector3f(0F, 1F / 256F, 0F));
        vector3fs.add(new Vector3f(0F, -1F / 256F, 0F));
        vector3fs.add(new Vector3f(1F, -1F / 256F, 0F));
        vector3fs.add(new Vector3f(1F, -1F / 256F, 1F));
        vector3fs.add(new Vector3f(0F, -1F / 256F, 1F));
        //todo make it could rotate
        vector3fs.forEach(vector3f -> vector3f.add(0, 2, 0));
        Vector3f look = new Vector3f(lookVec);
        Vector3f vert = new Vector3f(verticalVec);
//        if (rotateWithLookVec) {
//            vector3fs.forEach(vector3f -> getLookVecTransform(vector3f, look, vert));
//        }

        matrixStackIn.pushPose();
        for (Vector3f vector3f : vector3fs) {
            buffer.vertex(pose, vector3f.x(), vector3f.y(), vector3f.z()).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 1F).uv2(maxLight).endVertex();
        }
        matrixStackIn.popPose();

//        buffer.vertex(pose, 0F, 1F / 256F + 2, 1F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 1F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 1F, 1F / 256F + 2, 1F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 1F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 1F, 1F / 256F + 2, 0F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 0F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 0F, 1F / 256F + 2, 0F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 0F).uv2(maxLight).endVertex();
//
//        buffer.vertex(pose, 0F, -1F / 256F + 2, 0F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 0F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 1F, -1F / 256F + 2, 0F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 0F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 1F, -1F / 256F + 2, 1F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(1F, 1F).uv2(maxLight).endVertex();
//        buffer.vertex(pose, 0F, -1F / 256F + 2, 1F).color(colorsAdd.getRed(), colorsAdd.getGreen(), colorsAdd.getBlue(), colorsAdd.getAlpha()).uv(0F, 1F).uv2(maxLight).endVertex();


        return time > renderTime;
    }
}
