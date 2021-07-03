package mfrf.magic_circle.rendering;

import java.util.OptionalDouble;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.ResourceLocation;
import org.checkerframework.checker.units.qual.A;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class RenderTypes extends RenderType {
    private static final RenderState.AlphaState ALPHA = new RenderState.AlphaState(1F / 255F);
    private static final RenderState.LightmapState ENABLE_LIGHTMAP = new RenderState.LightmapState(true);
    private static final RenderState.TransparencyState TRANSLUCENT = new RenderState.TransparencyState("translucent", RenderTypes::enableTransparency, RenderTypes::disableTransparency);

    public RenderTypes(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }

    private static void enableTransparency() {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    private static void disableTransparency() {
        RenderSystem.disableBlend();
    }


    public static RenderType slide(ResourceLocation loc) {
        return RenderType.create("figure_world",
                DefaultVertexFormats.POSITION_COLOR_TEX_LIGHTMAP, GL11.GL_QUADS,
                256, false, true,
                RenderType.State.builder().setAlphaState(ALPHA).setLightmapState(ENABLE_LIGHTMAP).setTransparencyState(TRANSLUCENT)
                        .setTextureState(new RenderState.TextureState(loc, false, true)).createCompositeState(false));
    }

    private static final LineState LINE_1_0D = new LineState(OptionalDouble.of(3.0D));
    //todo thickness

    public static final RenderType MAGIC_CIRCLE_LINES = create("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINE_STRIP, 256,
            RenderType.State.builder().setLineState(LINE_1_0D)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(NO_TEXTURE)
                    .setDepthTestState(RenderState.LEQUAL_DEPTH_TEST)
                    .setCullState(CullState.NO_CULL)
                    .setLightmapState(RenderState.NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

    public static final RenderType TWO_POINT_LINES = create("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, 256,
            RenderType.State.builder().setLineState(LINE_1_0D)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(NO_TEXTURE)
                    .setDepthTestState(RenderState.LEQUAL_DEPTH_TEST)
                    .setCullState(CullState.NO_CULL)
                    .setLightmapState(RenderState.NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

    public static final RenderType MAGIC_CIRCLE_CLOSE_LINES = create("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINE_LOOP, 256,
            RenderType.State.builder().setLineState(LINE_1_0D)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                    .setTextureState(NO_TEXTURE)
                    .setDepthTestState(RenderState.LEQUAL_DEPTH_TEST)
                    .setCullState(CullState.NO_CULL)
                    .setLightmapState(RenderState.NO_LIGHTMAP)
                    .setWriteMaskState(COLOR_WRITE)
                    .createCompositeState(false));

}
