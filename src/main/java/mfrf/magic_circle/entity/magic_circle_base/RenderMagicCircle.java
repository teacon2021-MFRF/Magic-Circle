package mfrf.magic_circle.entity.magic_circle_base;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderMagicCircle extends EntityRenderer<EntityMagicCircle> {
    public RenderMagicCircle(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(EntityMagicCircle entityMagicCircle) {
        return null;
    }

    @Override
    public void render(EntityMagicCircle entityMagicCircle, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityMagicCircle, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
//        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase();
//        magicCircleRenderBase.deserializeNBT(entityMagicCircle.getEntityData().get(EntityMagicCircle.MAGIC_CIRCLE_OBJECT));
//        Float progress = entityMagicCircle.getEntityData().get(EntityMagicCircle.PROGRESS);
//        Vector3d lookVec = entityMagicCircle.getLookAngle();
//        Vector3d verticalVec = entityMagicCircle.getUpVector(1);
//        BlockPos positionVec = entityMagicCircle.blockPosition();
//        magicCircleRenderBase.rendering(progress + partialTicks, matrixStackIn, bufferIn, packedLightIn, lookVec, verticalVec, new Vector3f(positionVec.getX(), positionVec.getY(), positionVec.getZ()),this.entityRenderDispatcher);
        //todo complete it
    }

}
