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

    public static final RenderType MAGIC_CIRCLE_LINES = makeType("magic_circle_lines",
            DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINE_SMOOTH, 256, true, false,
            RenderType.State.getBuilder().line(new LineState(OptionalDouble.of(256)))
                    .layer(LayerState.VIEW_OFFSET_Z_LAYERING)
                    .transparency(TransparencyState.TRANSLUCENT_TRANSPARENCY)
                    .texture(TextureState.NO_TEXTURE)
                    .depthTest(RenderState.DEPTH_ALWAYS)
                    .cull(CullState.CULL_DISABLED)
                    .lightmap(LIGHTMAP_DISABLED)
                    .writeMask(RenderState.COLOR_WRITE)
                    .build(false)
    );
}
