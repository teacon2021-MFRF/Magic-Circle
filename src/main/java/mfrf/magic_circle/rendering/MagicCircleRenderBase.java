package mfrf.magic_circle.rendering;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class MagicCircleRenderBase extends MagicCircleComponentBase {
    public int progressAdditionPerTick;
    public int currentProgress = 0;
    public int maxProgress = 0;
    protected ArrayList<BezierCurveObject> curves;
    protected ArrayList<CircleObject> circles;

    public MagicCircleRenderBase(ArrayList<BezierCurveObject> curves, ArrayList<CircleObject> circles, int progressAdditionPerTick, int maxProgress, float delay) {
        super(delay);
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

    public static MagicCircleRenderBase deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("pgpt") && compoundNBT.contains("curves") && compoundNBT.contains("circles") && compoundNBT.contains("delay")) {
            ListNBT curvesNBT = compoundNBT.getList("curves", Constants.NBT.TAG_COMPOUND);
            ListNBT circlesNBT = compoundNBT.getList("circles", Constants.NBT.TAG_COMPOUND);
            ArrayList<BezierCurveObject> bezierCurveObjects = new ArrayList<>();
            for (INBT inbt : curvesNBT) {
                bezierCurveObjects.add(BezierCurveObject.deserializeNBT((CompoundNBT) inbt));
            }
            ArrayList<CircleObject> circleObjects = new ArrayList<>();
            for (INBT inbt : circlesNBT) {
                circleObjects.add(CircleObject.deserializeNBT((CompoundNBT) inbt));
            }
            return new MagicCircleRenderBase(bezierCurveObjects, circleObjects, compoundNBT.getInt("pgpt"), compoundNBT.getInt("max_progress"), compoundNBT.getInt("delay"));
        }
        return new MagicCircleRenderBase();
    }

}
