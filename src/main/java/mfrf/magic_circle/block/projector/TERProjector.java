package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.rendering.BezierCurveObject;
import mfrf.magic_circle.rendering.CircleObject;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleObjectBase;
import mfrf.magic_circle.util.MathUtil;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.ArrayList;

public class TERProjector extends TileEntityRenderer<TileProjector> {
    public TERProjector(TileEntityRendererDispatcher p_i226006_1_) {
        super(p_i226006_1_);
    }

    @Override
    public void render(TileProjector tileProjector, float v, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int i, int i1) {
        ArrayList<BezierCurveObject> bezierCurveObjects = new ArrayList<>();
        ArrayList<CircleObject> circleObjects = new ArrayList<>();
//        bezierCurveObjects.add(new BezierCurveObject(
//                new Vector3f(1, 0, 1),
//                new Vector3f(1, 1, -1),
//                new Vector3f(-1, 2, -1),
//                new Vector3f(-1, 3, 1)
//        ));
//        bezierCurveObjects.add(new BezierCurveObject(new Vector3f(1, 0, -1), new Vector3f(-1, 1, -1), new Vector3f(-1, 2, 1), new Vector3f(1, 3, 1)));
//        bezierCurveObjects.add(new BezierCurveObject(new Vector3f(-1, 0, -1), new Vector3f(-1, 1, 1), new Vector3f(1, 2, 1), new Vector3f(1, 3, -1)));
//        bezierCurveObjects.add(new BezierCurveObject(new Vector3f(-1, 0, 1), new Vector3f(1, 1, 1), new Vector3f(1, 2, -1), new Vector3f(-1, 3, -1)));
//        circleObjects.add(new CircleObject(1.414f, 0, 0, 0, 2));
//        circleObjects.add(new CircleObject(1.732f, 0, 0, 0, 4));
//        circleObjects.add(new CircleObject(2.236f, 0, 0, 0, 6));
        circleObjects.add(new CircleObject(2.780f, 0, 0, 0, 8));
        MagicCircleObjectBase magicCircleObjectBase = new MagicCircleObjectBase(bezierCurveObjects, circleObjects, 0.5f, 0);
        BlockPos pos = tileProjector.getBlockPos();
        magicCircleObjectBase.rendering(0.9f, matrixStack, iRenderTypeBuffer, tileProjector.time, new Vector3d(0, 1, 0), new Vector3f(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
       return true;
    }

}
