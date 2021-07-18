package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

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
//        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(1, 0, 0, 0);
        BlockPos pos = tileProjector.getBlockPos();

//        ImageObject p1 = new ImageObject(0, 16, 24, -16, 30, new ResourceLocation(MagicCircle.MOD_ID, "textures/magic_circle/block.png")).setRotateWithLookVec().setTransform(Matrix3f.createScaleMatrix(16, 16, 16));
//        ImageObject p2 = new ImageObject(0, 16, 24, -16, 30, new ResourceLocation(MagicCircle.MOD_ID, "textures/magic_circle/block.png")).setRotateWithLookVec().setPositionOffset(new Vector3f(0, 4, 0)).setTransform(Matrix3f.createScaleMatrix(10, 10, 10));
//        ImageObject p3 = new ImageObject(0, 16, 24, -16, 30, new ResourceLocation(MagicCircle.MOD_ID, "textures/magic_circle/block.png")).setRotateWithLookVec().setPositionOffset(new Vector3f(-8, 0, -8)).setTransform(Matrix3f.createScaleMatrix(8, 8, 8));
//        ImageObject p4 = new ImageObject(0, 16, 24, -16, 30, new ResourceLocation(MagicCircle.MOD_ID, "textures/magic_circle/block.png")).setRotateWithLookVec().setPositionOffset(new Vector3f(8, 0, 8)).setTransform(Matrix3f.createScaleMatrix(8, 8, 8));
//
//
//        magicCircleRenderBase.appendNextParallelComponents(p1, p2, p3, p4);
//        magicCircleRenderBase.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, new Vector3d(1, 1, 1), new Vector3d(1, 0, -1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()), this.renderer);
        // =====================================================================================

        MagicCircleComponentBase<?> render = tileProjector.magicCircleComponentBase.getRender();
        render.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, new Vector3d(1, 1, 1), new Vector3d(1, 0, -1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()), this.renderer);
    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
