package mfrf.magic_circle.block.projector;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.rendering.BezierCurveObject;
import mfrf.magic_circle.rendering.CircleObject;
import mfrf.magic_circle.rendering.LineObject;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import mfrf.magic_circle.util.Colors;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
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
        ArrayList<LineObject> lineObjects = new ArrayList<>();

        circleObjects.add(new CircleObject(2.780f, 0, 0, 0, 8).setAlpha(255).setColor(Colors.DRYSKY).setPositionOffset(new Vector3f(1, 2, 3)));

        bezierCurveObjects.add(new BezierCurveObject(0, 0, 0, 0, new Vector3f(1.1f, 1.2f, 1.3f), new Vector3f(1.3f, 2.4f, 1.5f), new Vector3f(1.6f, 3.7f, 1.8f)).setAlpha(255));

        LineObject l1 = new LineObject(2, 1, 0, 0, 5).setPositionOffset(new Vector3f(1, 0, 0));
        LineObject l2 = new LineObject(4, 1, 0, 0, 5).setPositionOffset(new Vector3f(0, 1, 0));
        LineObject l3 = new LineObject(6, 1, 0, 0, 5).setPositionOffset(new Vector3f(0, 0, 1));
        lineObjects.add(l1.point(0, 0, 0).point(1, 0, 0).point(1, 0, 1).point(0, 0, 1).close());
        lineObjects.add(l2.point(0, 0, 0).point(1, 0, 0).point(1, 0, 1).point(0, 0, 1).close());
        lineObjects.add(l3.point(0, 0, 0).point(1, 0, 0).point(1, 0, 1).point(0, 0, 1).close());

        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase(bezierCurveObjects, circleObjects, lineObjects, 100, 0, 0, 0, 0);
        BlockPos pos = tileProjector.getBlockPos();
//        magicCircleRenderBase.rendering(0.9f, matrixStack, iRenderTypeBuffer, tileProjector.time, new Vector3d(0, 1, 0), new Vector3f(pos.getX(), pos.getY(), pos.getZ()));
        magicCircleRenderBase.rendering(tileProjector.time, matrixStack, iRenderTypeBuffer, 0.5f, new Vector3d(1, 1, 1), new Vector3f(pos.getX(), pos.getY(), pos.getZ()));
    }

    @Override
    public boolean shouldRenderOffScreen(TileProjector p_188185_1_) {
        return true;
    }

}
