package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;

public class CoordinatesObject extends MagicCircleComponentBase<CoordinatesObject> {

    protected Coordinates coordinates = new Coordinates(null, null, null, null);
    protected int renderTick = 0;
    private float actualRenderTick = 0;

    public CoordinatesObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTick, @Nullable Coordinates coordinates) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius);
        this.renderTick = renderTick;
        this.actualRenderTick = renderTick + delay;
        if (coordinates != null) {
            this.coordinates = coordinates;
        }
    }

    public CoordinatesObject() {
        super();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
        return false;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {
        float actualRenderProgress = time - delay;
        float progress = actualRenderProgress / renderTick;
        if (progress >= 1) {
            progress = 1;
        }
        ArrayList<Vector3f> xAxisPoints = coordinates.getXAxisPoints(progress);
        ArrayList<Vector3f> yAxisPoints = coordinates.getYAxisPoints(progress);
        ArrayList<Vector3f> zAxisPoints = coordinates.getZAxisPoints(progress);
        ArrayList<Vector3f> labels = new ArrayList<>();
        ArrayList<Vector3f> functionPoints = coordinates.getFunctionPoints(progress);

        if (progress >= 1)
            labels = coordinates.getLabels();

        Vector3f copy = actualPosition.copy();
        copy.add(positionOffset);

        Quaternion baseRot = makeRotate(time);
        xAxisPoints.forEach(vector3f -> vector3f.transform(baseRot));
        yAxisPoints.forEach(vector3f -> vector3f.transform(baseRot));
        zAxisPoints.forEach(vector3f -> vector3f.transform(baseRot));
        if (functionPoints != null) {
            functionPoints.forEach(vector3f -> vector3f.transform(baseRot));
        }
        labels.forEach(vector3f -> vector3f.transform(baseRot));

        if (rotateWithLookVec) {
            Vector3f look = new Vector3f(lookVec);
            Vector3f verticalVecF = new Vector3f(verticalVec);
            xAxisPoints.forEach(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            yAxisPoints.forEach(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            zAxisPoints.forEach(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            if (functionPoints != null) {
                functionPoints.forEach(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            }
            labels.forEach(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
        }

        doRender(matrixStackIn, copy, Color.CYAN, false, false, xAxisPoints, RenderTypes.MAGIC_CIRCLE_LINES);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, yAxisPoints, RenderTypes.MAGIC_CIRCLE_LINES);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, zAxisPoints, RenderTypes.MAGIC_CIRCLE_LINES);

        if (functionPoints != null) {
            doRender(matrixStackIn, copy, Color.GREEN, true, false, functionPoints, RenderTypes.MAGIC_CIRCLE_LINES);
        } else {
            //todo render error
            renderText(matrixStackIn, color.toAWT(), new TranslationTextComponent(MagicCircle.MOD_ID + ".type_cast_error"), renderer.font);
        }

        renderALotAxisLabels(matrixStackIn, copy, Color.CYAN, false, false, labels);

        return time >= actualRenderTick;
    }

}
