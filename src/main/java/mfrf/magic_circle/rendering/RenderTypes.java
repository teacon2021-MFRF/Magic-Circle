package mfrf.magic_circle.rendering;

import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

public class RenderTypes extends RenderType {

    public RenderTypes(String p_i225992_1_, VertexFormat p_i225992_2_, int p_i225992_3_, int p_i225992_4_, boolean p_i225992_5_, boolean p_i225992_6_, Runnable p_i225992_7_, Runnable p_i225992_8_) {
        super(p_i225992_1_, p_i225992_2_, p_i225992_3_, p_i225992_4_, p_i225992_5_, p_i225992_6_, p_i225992_7_, p_i225992_8_);
    }

    private static final LineState LINE_1_0D = new LineState(OptionalDouble.of(3.0D));

    public static final RenderType MAGIC_CIRCLE_LINES = makeType("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINE_STRIP, 256,
            RenderType.State.getBuilder().line(LINE_1_0D)
                    .layer(VIEW_OFFSET_Z_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .texture(NO_TEXTURE)
                    .depthTest(RenderState.DEPTH_LEQUAL)
                    .cull(CullState.CULL_DISABLED)
                    .lightmap(RenderState.LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(false));

    public static final RenderType MAGIC_CIRCLE_CLOSE_LINES = makeType("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINE_LOOP, 256,
            RenderType.State.getBuilder().line(LINE_1_0D)
                    .layer(VIEW_OFFSET_Z_LAYERING)
                    .transparency(TRANSLUCENT_TRANSPARENCY)
                    .texture(NO_TEXTURE)
                    .depthTest(RenderState.DEPTH_LEQUAL)
                    .cull(CullState.CULL_DISABLED)
                    .lightmap(RenderState.LIGHTMAP_DISABLED)
                    .writeMask(COLOR_WRITE)
                    .build(false));
}
