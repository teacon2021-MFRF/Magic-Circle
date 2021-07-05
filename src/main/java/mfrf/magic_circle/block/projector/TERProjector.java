package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import icyllis.modernui.graphics.Canvas;
import mfrf.magic_circle.MagicCircle;
import mfrf.magic_circle.rendering.*;
import mfrf.magic_circle.util.Colors;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;

public class TERProjector extends TileEntityRenderer<TileProjector> {
    public TERProjector(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileProjector tileProjector, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {

//示例：坐标系+函数
//        CoordinatesObject t = new CoordinatesObject(0, 0, 0, 0, 8, new MagicCircleComponentBase.Coordinates(
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.X, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Y, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Z, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new PositionExpression("t", "math.pow(t,2) + 1", null, 0.05f, 100),
//                null
//        )).setRotateWithLookVec();


        //test
        // =====================================================================================
        BlockPos pos = tileProjector.getBlockPos();
        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(1, 0, 0, 0);
//        ImageObject imageObject = new ImageObject(0, 0, 0, 0, 20, new ResourceLocation(MagicCircle.MOD_ID, "textures/danmaku/test_danmaku.png")).setRotateWithLookVec().setPositionOffset(new Vector3f(0, 3, 0));
//        magicCircleRenderBase.appendNextParallelComponents(imageObject);
//
//        LineObject lineObject = new LineObject(0, 0, 0, 0, 20).setRotateWithLookVec().point(0, 0, 0).point(0, 16, 0);

//        magicCircleRenderBase.appendNextParallelComponents(t);
//        magicCircleRenderBase.appendNextParallelComponents(lineObject);

        magicCircleRenderBase.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, new Vector3d(1, 1, 1), new Vector3d(1, 0, -1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()), this.renderer);
        // =====================================================================================
    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
