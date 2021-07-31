package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.rendering.CircleObject;
import mfrf.magic_circle.rendering.CoordinatesObject;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import mfrf.magic_circle.util.Colors;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;
import java.util.function.BiFunction;

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
        Vector3f offsetBase = new Vector3f(0, 32, 0);
        long gameTime = tileProjector.getLevel().getGameTime();

        Vector3f o1 = offsetBase.copy();
        o1.add(16, 0, 0);
        Vector3f o2 = offsetBase.copy();
        o2.add(0, 0, 24);
        Vector3f o3 = offsetBase.copy();
        o3.add(-32, 0, 0);
        Vector3f o4 = offsetBase.copy();
        o4.add(0, 0, -40);
        Vector3f o5 = offsetBase.copy();
        o5.add(48, 0, 0);
        Vector3f o6 = offsetBase.copy();
        o6.add(0, 0, -56);
        Vector3f o7 = offsetBase.copy();
        o7.add(-64, 0, 0);
        Vector3f o8 = offsetBase.copy();
        o8.add(0, 0, 72);

        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase();
        magicCircleRenderBase.appendNextParallelComponents(
//                new CoordinatesObject(0, 0, 0, 0, 100, new MagicCircleComponentBase.Coordinates(
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.X, new MagicCircleComponentBase.Line(-72, 72), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Y, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                        new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Z, new MagicCircleComponentBase.Line(-72, 72), new MagicCircleComponentBase.BasicArrowHead(1), 1)
////                        new PositionExpression("t", "math.pow(t,2) + 1", null, 0.05f, 100, 0),
//                )).setPositionOffset(offsetBase),
                new CircleObject(0, 8, 0, 0, 200, 8).setPositionOffset(offsetBase),
                new CircleObject(0, 8, 0, 8, 200, 8).setPositionOffset(offsetBase),
                new CircleObject(0, 4, 0, 0, 200, 7).setPositionOffset(offsetBase),
                new CircleObject(0, 4, 0, 4, 200, 7).setPositionOffset(offsetBase),

                new CircleObject(0, 0, 0, 0, 200, 16).setPositionOffset(offsetBase).setColor(Colors.KUNDI),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o1).setRotation(t -> new Quaternion(0, gameTime % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o1).setRotation(t -> new Quaternion(0, gameTime % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 24).setPositionOffset(offsetBase).setColor(Colors.DUIZE),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o2).setRotation(t -> new Quaternion(0, gameTime / 2 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o2).setRotation(t -> new Quaternion(0, gameTime / 2 % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 32).setPositionOffset(offsetBase).setColor(Colors.LIHUO),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o3).setRotation(t -> new Quaternion(0, (gameTime / 4) % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o3).setRotation(t -> new Quaternion(0, (gameTime / 4) % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 40).setPositionOffset(offsetBase).setColor(Colors.GENSHAN),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o4).setRotation(t -> new Quaternion(0, gameTime / 6 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o4).setRotation(t -> new Quaternion(0, gameTime / 6 % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 48).setPositionOffset(offsetBase).setColor(Colors.SUNDAE),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o5).setRotation(t -> new Quaternion(0, gameTime / 8 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o5).setRotation(t -> new Quaternion(0, gameTime / 8 % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 56).setPositionOffset(offsetBase).setColor(Colors.KANSHUI),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o6).setRotation(t -> new Quaternion(0, gameTime / 10 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o6).setRotation(t -> new Quaternion(0, gameTime / 10 % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 64).setPositionOffset(offsetBase).setColor(Colors.THUNDER),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o7).setRotation(t -> new Quaternion(0, gameTime / 12 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o7).setRotation(t -> new Quaternion(0, gameTime / 12 % 360, 0, true)),

                new CircleObject(0, 0, 0, 0, 200, 72).setPositionOffset(offsetBase).setColor(Colors.DRYSKY),
                new CircleObject(0, 3, 0, 0, 200, 1).setPositionOffset(o8).setRotation(t -> new Quaternion(0, gameTime / 14 % 360, 0, true)),
                new CircleObject(0, 3, 0, 3, 200, 1).setPositionOffset(o8).setRotation(t -> new Quaternion(0, gameTime / 14 % 360, 0, true))
//                new CircleObject(0, 8, 0, 8, 200, 8).setPositionOffset(offsetBase)
        );


        // =====================================================================================

        magicCircleRenderBase.rendering(gameTime, matrixStack, iRenderTypeBuffer, new Vector3d(1, 1, 1), new Vector3d(1, 0, -1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()), this.renderer);
    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
