package mfrf.magic_circle.entity.barrage;

import mfrf.magic_circle.MagicCircle;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class RenderDanmakuEntity extends EntityRenderer<DanmakuEntity> {
    public static final ResourceLocation TEST_TEXTURE = new ResourceLocation(MagicCircle.MOD_ID, "textures/danmaku/test_danmaku.png");

    protected RenderDanmakuEntity(EntityRendererManager p_i46179_1_) {
        super(p_i46179_1_);
    }

    @Override
    public ResourceLocation getTextureLocation(DanmakuEntity danmakuEntity) {
        return TEST_TEXTURE;
    }
}
