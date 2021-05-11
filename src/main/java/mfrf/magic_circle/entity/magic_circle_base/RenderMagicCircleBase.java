package mfrf.magic_circle.entity.magic_circle_base;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.rendering.MagicCircleObjectBase;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class RenderMagicCircleBase extends EntityRenderer<EntityMagicCircleBase> {
    protected RenderMagicCircleBase(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getEntityTexture(EntityMagicCircleBase entityMagicCircleBase) {
        return null;
    }

    @Override
    public void render(EntityMagicCircleBase entityMagicCircleBase, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityMagicCircleBase, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        MagicCircleObjectBase magicCircleObjectBase = MagicCircleObjectBase.deserializeNBT(entityMagicCircleBase.getDataManager().get(EntityMagicCircleBase.MAGIC_CIRCLE_OBJECT));
        Float progress = entityMagicCircleBase.getDataManager().get(EntityMagicCircleBase.PROGRESS);
        Vector3d lookVec = entityMagicCircleBase.getLookVec();
        BlockPos positionVec = entityMagicCircleBase.getPosition();
        magicCircleObjectBase.rendering(progress + partialTicks, matrixStackIn, bufferIn, packedLightIn, lookVec, new Vector3f(positionVec.getX(), positionVec.getY(), positionVec.getZ()));
    }

}
