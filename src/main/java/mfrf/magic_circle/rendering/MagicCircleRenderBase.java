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
    public float progressAdditionPerTick;
    protected ArrayList<BezierCurveObject> curves;
    protected ArrayList<CircleObject> circles;

    public MagicCircleRenderBase(ArrayList<BezierCurveObject> curves, ArrayList<CircleObject> circles, float progressAdditionPerTick, float delay) {
        super(delay);
        this.curves = curves;
        this.circles = circles;
        this.progressAdditionPerTick = progressAdditionPerTick;
    }

    public MagicCircleRenderBase() {
        super();
        curves = new ArrayList<>();
        circles = new ArrayList<>();
    }

    @Override
    protected boolean renderingSelf(float time, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, float trueTime, Vector3d lookVec, Vector3f actualPosition) {
        boolean flag = true;
        for (BezierCurveObject curve : curves) {
            boolean b = curve.renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
            flag = flag && b;
        }
        for (CircleObject circle : circles) {
            boolean b = circle.renderingSelf(time, matrixStackIn, bufferIn, trueTime, lookVec, actualPosition);
            flag = flag && b;
        }
        return flag;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("pgpt", progressAdditionPerTick);
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
            return new MagicCircleRenderBase(bezierCurveObjects, circleObjects, compoundNBT.getFloat("pgpt"), compoundNBT.getFloat("delay"));
        }
        return new MagicCircleRenderBase();
    }

}
