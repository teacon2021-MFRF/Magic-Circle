package mfrf.magic_circle.magicutil.nodes.behaviornode;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import net.minecraft.nbt.CompoundNBT;

public abstract class BehaviorNodeBase extends MagicNodeBase {

    private final BehaviorType behaviorType;

    public BehaviorNodeBase(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, BehaviorType behaviorType) {
        super(NodeType.BEHAVIOR);
        this.behaviorType = behaviorType;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt, int layer, MagicStreamMatrixNByN matrix) {
        return null;
    }

    public enum BehaviorType {
        THROW, BEAM, CIRCLE, RESTRICT, RADIUS, TRIGGER;
    }
}
