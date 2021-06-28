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

        //光流节点
       // ==============================================================================================================================

        ArrayList<BezierCurveObject> bezierCurveObjects = new ArrayList<>();
        ArrayList<CircleObject> circleObjects = new ArrayList<>();
        ArrayList<LineObject> lineObjects = new ArrayList<>();
        ArrayList<MagicCircleComponentBase> otherThings = new ArrayList<>();
        //todo make it become functional;

        circleObjects.add(new CircleObject(7f, 0, 0, 0, 0).setPositionOffset(new Vector3f(0, 1, 0)).setAlpha(255).setColor(Colors.DRYSKY));
        circleObjects.add(new CircleObject(7*0.5f, 0, 0, 0, 0).setPositionOffset(new Vector3f(0, 2.5f, 0)).setAlpha(255).setColor(Colors.DRYSKY));


        LineObject l1 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 1, 0)).point(0,0,7).point(-6.0523f,0,-3.517f).point(6.0523f,0,-3.517f).close();
        LineObject l2 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 1, 0)).point(0,0,7).point(-6.0523f,0,-3.517f).point(6.0523f,0,-3.517f).close().setRotation(new Quaternion(0,180,0,true));
        LineObject l3 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 0, 0)).point(0,2.5f,7*0.5f).point(-6.0523f*0.5f,2.5f,-3.517f*0.5f).point(6.0523f*0.5f,2.5f,-3.517f*0.5f).close();
        LineObject l4 = new LineObject(0, 0, 0, 0, 5).setPositionOffset(new Vector3f(0, 0, 0)).point(0,2.5f,7*0.5f).point(-6.0523f*0.5f,2.5f,-3.517f*0.5f).point(6.0523f*0.5f,2.5f,-3.517f*0.5f).close().setRotation(new Quaternion(0,180,0,true));
        lineObjects.add(l1);
        lineObjects.add(l2);
        lineObjects.add(l3);
        lineObjects.add(l4);

        otherThings.add(new CoordinatesObject(0, 0, 0, 0, 8, new MagicCircleComponentBase.Coordinates(
                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.X, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
                new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Y, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
               new MagicCircleComponentBase.Axis(MagicCircleComponentBase.DIRECTION.Z, new MagicCircleComponentBase.Line(-8, 8), new MagicCircleComponentBase.BasicArrowHead(1), 1),
//                new PositionExpression("t", "math.pow(t,2) + 1", null, 0.05f, 100)
                null
        )).setRotateWithLookVec());
       // =====================================================================================
//
        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(bezierCurveObjects, circleObjects, lineObjects, otherThings, 100, 0, 0, 0, 0);
//        BlockPos pos = tileProjector.getBlockPos();
////        magicCircleRenderBase.rendering(0.9f, matrixStack, iRenderTypeBuffer, tileProjector.time, new Vector3d(0, 1, 0), new Vector3f(pos.getX(), pos.getY(), pos.getZ()));
//        magicCircleRenderBase.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, 0.5f, new Vector3d(1, 1, 1), new Vector3d(0, 1, 1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()),this.renderer);


    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
