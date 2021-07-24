package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import net.minecraft.nbt.CompoundNBT;


public abstract class BehaviorNodeBase extends MagicNodeBase {

    private final BehaviorType behaviorType;

    public BehaviorNodeBase(BehaviorType behaviorType) {
        super(NodeType.BEHAVIOR);
        this.behaviorType = behaviorType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicNodePropertyMatrix8By8 matrix) {
        BehaviorType subtype = BehaviorType.valueOf(nbt.getString("subtype"));
        switch (subtype) {
            case THROW: {
                return ThrowBehaviorNode.deserializeNBT(nbt, layer, matrix);
            }
            default: {
                return null;
            }
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = super.serializeNBT();
        compoundNBT.putString("subtype",behaviorType.name());
        return compoundNBT;
    }

    public BehaviorType getBehaviorType() {
        return behaviorType;
    }

    public enum BehaviorType {
        THROW, BEAM, CIRCLE, RESTRICT, RADIUS, TRIGGER;
    }
}
