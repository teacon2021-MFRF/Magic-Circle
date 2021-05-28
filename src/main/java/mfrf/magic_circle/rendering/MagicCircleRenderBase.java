package mfrf.magic_circle.rendering;

import java.util.ArrayList;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

public class MagicCircleRenderBase extends MagicCircleComponentBase {
    public int progressAdditionPerTick;
    public int currentProgress = 0;
    public int maxProgress = 0;
    protected ArrayList<BezierCurveObject> curves;
    protected ArrayList<CircleObject> circles;

    public MagicCircleRenderBase(ArrayList<BezierCurveObject> curves, ArrayList<CircleObject> circles, int progressAdditionPerTick, int maxProgress, float delay, float xrot, float yrot, float zrot) {
        super(delay, xrot, yrot, zrot);
        this.curves = curves;
        this.circles = circles;
        this.progressAdditionPerTick = progressAdditionPerTick;
        this.maxProgress = maxProgress;
    }

    public MagicCircleRenderBase() {
        super();
        curves = new ArrayList<>();
        circles = new ArrayList<>();
    }

    /**
     * @return if returnValue == true, whitch means all rendering progress has complete(include progress of instance).
     */
    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        currentProgress += progressAdditionPerTick;
        boolean flag = true;
        for (BezierCurveObject curve : curves) {
            boolean b = curve.renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
            flag = flag && b;
        }
        for (CircleObject circle : circles) {
            boolean b = circle.renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
            flag = flag && b;
        }
        return flag && (maxProgress == currentProgress);
    }

    public CompoundNBT serializeNBT() {
        super.serializeNBT();
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("pgpt", progressAdditionPerTick);
        compoundNBT.putInt("max_progress", maxProgress);
        compoundNBT.putInt("current_progress", currentProgress);
        compoundNBT.putFloat("delay", delay);
        ListNBT curvesNBT = new ListNBT();
        for (BezierCurveObject curve : curves) {
            curvesNBT.add(curve.serializeNBT());
        }
        compoundNBT.put("curves", curvesNBT);
        ListNBT circlesNBT = new ListNBT();
        for (CircleObject circle : circles) {
            circlesNBT.add(circle.serializeNBT());
        }
        compoundNBT.put("circles", circlesNBT);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("pgpt") && compoundNBT.contains("curves") && compoundNBT.contains("circles") && compoundNBT.contains("delay")) {
            super.deserializeNBT(compoundNBT);
            ListNBT curvesNBT = compoundNBT.getList("curves", Constants.NBT.TAG_COMPOUND);
            ListNBT circlesNBT = compoundNBT.getList("circles", Constants.NBT.TAG_COMPOUND);
            for (INBT inbt : curvesNBT) {
                BezierCurveObject bezierCurveObject = new BezierCurveObject();
                bezierCurveObject.deserializeNBT((CompoundNBT) inbt);
                this.curves.add(bezierCurveObject);
            }
            for (INBT inbt : circlesNBT) {
                CircleObject circleObject = new CircleObject();
                circleObject.deserializeNBT((CompoundNBT) inbt);
                this.circles.add(circleObject);
            }
            progressAdditionPerTick = compoundNBT.getInt("pgpt");
            this.maxProgress = compoundNBT.getInt("max_progress");
            this.currentProgress = compoundNBT.getInt("current_progress");
            this.delay = compoundNBT.getInt("delay");
        }
    }

}
