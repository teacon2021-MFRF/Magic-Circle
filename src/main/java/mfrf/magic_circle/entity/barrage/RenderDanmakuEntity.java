package mfrf.magic_circle.entity.barrage;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.magicutil.RGBA;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class RenderDanmakuEntity extends EntityRenderer<DanmakuEntity> {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(MagicCircle.MOD_ID, "textures/danmaku/danmaku_default.png");
    private ModelDanmakuEntity danmakuEntityModel;

    public RenderDanmakuEntity(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
        this.danmakuEntityModel = new ModelDanmakuEntity();
    }


    @Override
    public boolean shouldRender(DanmakuEntity p_225626_1_, ClippingHelper p_225626_2_, double p_225626_3_, double p_225626_5_, double p_225626_7_) {
        return super.shouldRender(p_225626_1_, p_225626_2_, p_225626_3_, p_225626_5_, p_225626_7_);
    }

    @Override
    public void render(DanmakuEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.pushPose();
//        RenderSystem.enableAlphaTest();
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(this.danmakuEntityModel.renderType(this.getTextureLocation(entityIn)));
        RGBA rgba = entityIn.getRGBA();
        this.danmakuEntityModel.render(partialTicks, matrixStackIn, OverlayTexture.NO_OVERLAY, ivertexbuilder, packedLightIn, 1,1,1,0.5f);
        matrixStackIn.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(DanmakuEntity danmakuEntity) {
        return TEST_TEXTURE;
    }
}
