package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import mfrf.magic_circle.MagicCircle;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CoordinatesObject extends MagicCircleComponentBase<CoordinatesObject> {

    protected Coordinates coordinates = new Coordinates(null, null, null);
    protected int renderTick = 0;

    public CoordinatesObject(float delay, float xRotateSpeedRadius, float yRotateSpeedRadius, float zRotateSpeedRadius, int renderTick, @Nullable Coordinates coordinates) {
        super(delay, xRotateSpeedRadius, yRotateSpeedRadius, zRotateSpeedRadius, renderTick);

        if (coordinates != null) {
            this.coordinates = coordinates;
        }
    }

    public CoordinatesObject() {
        super();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, TileEntityRendererDispatcher renderer) {

        float progress = time / renderTick;
        if (progress >= 1) {
            progress = 1;
        }
        Stream<Vector3f> xAxisPoints = coordinates.getXAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> yAxisPoints = coordinates.getYAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> zAxisPoints = coordinates.getZAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> labels = new ArrayList<Vector3f>().stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Stream<Vector3f>> functionPoints = coordinates.getFunctionPoints(progress).map(vector3fStream -> vector3fStream.peek(vector3f -> vector3f.add(positionOffset)));

        if (progress >= 1)
            labels = coordinates.getLabels().stream().peek(vector3f -> vector3f.add(positionOffset));

        Vector3f copy = actualPosition.copy();

        Quaternion baseRot = makeRotate(time);
        xAxisPoints = xAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        yAxisPoints = yAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        zAxisPoints = zAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        if (functionPoints != null) {
            functionPoints = functionPoints.map(
                    function -> function.peek(
                            vector3d -> vector3d.transform(baseRot)
                    )
            );
        }
        labels = labels.peek(vector3f -> vector3f.transform(baseRot));

        if (rotateWithLookVec) {
            Vector3f look = new Vector3f(lookVec);
            Vector3f verticalVecF = new Vector3f(verticalVec);
            xAxisPoints = xAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            yAxisPoints = yAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            zAxisPoints = zAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            if (functionPoints != null) {
                functionPoints = functionPoints.map(
                        function -> function.map(
                                vector3f -> getLookVecTransform(vector3f, look, verticalVecF)
                        )
                );
            }
            labels = labels.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
        }

        RenderType renderType = RenderTypes.makeCircleLine(lineWidth);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, xAxisPoints.collect(Collectors.toList()), renderType);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, yAxisPoints.collect(Collectors.toList()), renderType);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, zAxisPoints.collect(Collectors.toList()), renderType);

        if (functionPoints != null) {
            functionPoints.forEachOrdered(vector3fStream -> {
                doRender(matrixStackIn, copy, Color.GREEN, true, false, vector3fStream.collect(Collectors.toList()), renderType);
            });
        } else {
            //todo render error
            renderText(matrixStackIn, color.toAWT(), new TranslationTextComponent(MagicCircle.MOD_ID + ".type_cast_error"), renderer.getFont());
        }

        renderALotAxisLabels(matrixStackIn, copy, Color.CYAN, false, false, labels.collect(Collectors.toList()));

        return time >= renderTick;
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {

        float progress = time / renderTick;
        if (progress >= 1) {
            progress = 1;
        }
        Stream<Vector3f> xAxisPoints = coordinates.getXAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> yAxisPoints = coordinates.getYAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> zAxisPoints = coordinates.getZAxisPoints(progress).stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Vector3f> labels = new ArrayList<Vector3f>().stream().peek(vector3f -> vector3f.add(positionOffset));
        Stream<Stream<Vector3f>> functionPoints = coordinates.getFunctionPoints(progress).map(vector3fStream -> vector3fStream.peek(vector3f -> vector3f.add(positionOffset)));

        if (progress >= 1)
            labels = coordinates.getLabels().stream().peek(vector3f -> vector3f.add(positionOffset));

        Vector3f copy = actualPosition.copy();

        Quaternion baseRot = makeRotate(time);
        xAxisPoints = xAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        yAxisPoints = yAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        zAxisPoints = zAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
        if (functionPoints != null) {
            functionPoints = functionPoints.map(
                    function -> function.peek(
                            vector3d -> vector3d.transform(baseRot)
                    )
            );
        }
        labels = labels.peek(vector3f -> vector3f.transform(baseRot));

        if (rotateWithLookVec) {
            Vector3f look = new Vector3f(lookVec);
            Vector3f verticalVecF = new Vector3f(verticalVec);
            xAxisPoints = xAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            yAxisPoints = yAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            zAxisPoints = zAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
            if (functionPoints != null) {
                functionPoints = functionPoints.map(
                        function -> function.map(
                                vector3f -> getLookVecTransform(vector3f, look, verticalVecF)
                        )
                );
            }
            labels = labels.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
        }

        RenderType renderType = RenderTypes.makeCircleLine(lineWidth);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, xAxisPoints.collect(Collectors.toList()), renderType);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, yAxisPoints.collect(Collectors.toList()), renderType);

        doRender(matrixStackIn, copy, Color.CYAN, false, false, zAxisPoints.collect(Collectors.toList()), renderType);

        if (functionPoints != null) {
            functionPoints.forEachOrdered(vector3fStream -> {
                doRender(matrixStackIn, copy, Color.GREEN, true, false, vector3fStream.collect(Collectors.toList()), renderType);
            });
        } else {
            //todo render error
            renderText(matrixStackIn, color.toAWT(), new TranslationTextComponent(MagicCircle.MOD_ID + ".type_cast_error"), renderer.getFont());
        }

        renderALotAxisLabels(matrixStackIn, copy, Color.CYAN, false, false, labels.collect(Collectors.toList()));

        return time >= renderTick;
    }



//    @Override
//    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, Vector3d lookVec, Vector3d verticalVec, Vector3f actualPosition, EntityRendererManager renderer) {
//        float progress = time / renderTick;
//        if (progress >= 1) {
//            progress = 1;
//        }
//        Stream<Vector3f> xAxisPoints = coordinates.getXAxisPoints(progress).stream();
//        Stream<Vector3f> yAxisPoints = coordinates.getYAxisPoints(progress).stream();
//        Stream<Vector3f> zAxisPoints = coordinates.getZAxisPoints(progress).stream();
//        Stream<Vector3f> labels = new ArrayList<Vector3f>().stream();
//        Stream<Stream<Vector3f>> functionPoints = coordinates.getFunctionPoints(progress);
//
//        if (progress >= 1)
//            labels = coordinates.getLabels().stream();
//
//        Vector3f copy = actualPosition.copy();
//        copy.add(positionOffset);
//
//        Quaternion baseRot = makeRotate(time);
//        xAxisPoints = xAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
//        yAxisPoints = yAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
//        zAxisPoints = zAxisPoints.peek(vector3f -> vector3f.transform(baseRot));
//        if (functionPoints != null) {
//            functionPoints = functionPoints.map(
//                    function -> function.peek(
//                            vector3d -> vector3d.transform(baseRot)
//                    )
//            );
//        }
//        labels = labels.peek(vector3f -> vector3f.transform(baseRot));
//
//        if (rotateWithLookVec) {
//            Vector3f look = new Vector3f(lookVec);
//            Vector3f verticalVecF = new Vector3f(verticalVec);
//            xAxisPoints = xAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
//            yAxisPoints = yAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
//            zAxisPoints = zAxisPoints.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
//            if (functionPoints != null) {
//                functionPoints = functionPoints.map(
//                        function -> function.map(
//                                vector3f -> getLookVecTransform(vector3f, look, verticalVecF)
//                        )
//                );
//            }
//            labels = labels.map(vector3f -> getLookVecTransform(vector3f, look, verticalVecF));
//        }
//
//        RenderType renderType = RenderTypes.makeCircleLine(lineWidth);
//
//        doRender(matrixStackIn, copy, Color.CYAN, false, false, xAxisPoints.collect(Collectors.toList()), renderType);
//
//        doRender(matrixStackIn, copy, Color.CYAN, false, false, yAxisPoints.collect(Collectors.toList()), renderType);
//
//        doRender(matrixStackIn, copy, Color.CYAN, false, false, zAxisPoints.collect(Collectors.toList()), renderType);
//
//        if (functionPoints != null) {
//            functionPoints.forEachOrdered(vector3fStream -> {
//                doRender(matrixStackIn, copy, Color.GREEN, true, false, vector3fStream.collect(Collectors.toList()), renderType);
//            });
//        } else {
//            //todo render error
//            renderText(matrixStackIn, color.toAWT(), new TranslationTextComponent(MagicCircle.MOD_ID + ".type_cast_error"), renderer.getFont());
//        }
//
//        renderALotAxisLabels(matrixStackIn, copy, Color.CYAN, false, false, labels.collect(Collectors.toList()));
//
//        return time >= renderTick;
//    }

}
