package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import icyllis.modernui.graphics.Canvas;
import mfrf.magic_circle.rendering.*;
import mfrf.magic_circle.util.Colors;
import mfrf.magic_circle.util.PositionExpression;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
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
//        otherThings.add(new CoordinatesObject(0, 0, 0, 0, 8, new MagicCircleComponentBase.Coordinates(
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.X, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Y, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Z, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//        new PositionExpression("t", "math.pow(t,2) + 1", null, 0.05f, 100)
//                null
//        )).setRotateWithLookVec());

        //光流节点
        // ==============================================================================================================================

        CircleObject circleObject1 = new CircleObject(0, 0, 0, 0, 16, 7).setPositionOffset(new Vector3f(0, 1, 0)).setAlpha(255).setColor(Colors.DRYSKY);
        CircleObject circleObject2 = new CircleObject(0, 0, 0, 0, 16, 7 * 0.5f).setPositionOffset(new Vector3f(0, 2.5f, 0)).setAlpha(255).setColor(Colors.DRYSKY);


        LineObject l1 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 1, 0)).point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close();
        LineObject l2 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 1, 0)).point(0, 0, 7).point(-6.0523f, 0, -3.517f).point(6.0523f, 0, -3.517f).close().setRotation(new Quaternion(0, 180, 0, true));
        LineObject l3 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 2.5f, 0)).point(0, 0f, 7 * 0.5f).point(-6.0523f * 0.5f, 0f, -3.517f * 0.5f).point(6.0523f * 0.5f, 0f, -3.517f * 0.5f).close();
        LineObject l4 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 2.5f, 0)).point(0, 0f, 7 * 0.5f).point(-6.0523f * 0.5f, 0f, -3.517f * 0.5f).point(6.0523f * 0.5f, 0f, -3.517f * 0.5f).close().setRotation(new Quaternion(0, 180, 0, true));

        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(0, 0, 0, 0).appendNextParallelComponents(circleObject1).appendNextParallelComponents(circleObject1, circleObject2, l1, l2, l3, l4);
        BlockPos pos = tileProjector.getBlockPos();

        magicCircleRenderBase.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, new Vector3d(1, 1, 1), new Vector3d(0, 1, 1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()), this.renderer);
        // =====================================================================================

    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
