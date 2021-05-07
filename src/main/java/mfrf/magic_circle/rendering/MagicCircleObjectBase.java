package mfrf.magic_circle.rendering;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

public class MagicCircleObjectBase {
    public float progressAdditionPerTick;
    protected ArrayList<BezierCurveObject> curves;
    protected ArrayList<CircleObject> circles;

    public MagicCircleObjectBase(ArrayList<BezierCurveObject> curves, ArrayList<CircleObject> circles, float progressAdditionPerTick) {
        this.curves = curves;
        this.circles = circles;
        this.progressAdditionPerTick = progressAdditionPerTick;
    }

    public MagicCircleObjectBase() {
        curves = new ArrayList<>();
        circles = new ArrayList<>();
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putFloat("pgpt", progressAdditionPerTick);
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

    public static MagicCircleObjectBase deserializeNBT(CompoundNBT compoundNBT) {
        if (compoundNBT.contains("pgpt") && compoundNBT.contains("curves") && compoundNBT.contains("circles")) {
            ListNBT curvesNBT = compoundNBT.getList("curves", Constants.NBT.TAG_COMPOUND);
            ListNBT circlesNBT = compoundNBT.getList("circles", Constants.NBT.TAG_COMPOUND);
            ArrayList<BezierCurveObject> bezierCurveObjects = new ArrayList<>();
            for (INBT inbt : curvesNBT) {
                bezierCurveObjects.add(BezierCurveObject.deserializeNBT((CompoundNBT) inbt));
            }
            ArrayList<CircleObject> circleObjects = new ArrayList<>();
            for (INBT inbt : curvesNBT) {
                circleObjects.add(CircleObject.deserializeNBT((CompoundNBT) inbt));
            }
            return new MagicCircleObjectBase(bezierCurveObjects, circleObjects, compoundNBT.getFloat("pgpt"));
        }
        return new MagicCircleObjectBase();
    }

}
