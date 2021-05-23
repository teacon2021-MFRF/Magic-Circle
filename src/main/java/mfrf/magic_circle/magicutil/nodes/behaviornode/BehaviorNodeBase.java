package mfrf.magic_circle.magicutil.nodes.behaviornode;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import net.minecraft.util.math.vector.Vector3f;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.MagicNodeBase.NodeType;

public abstract class BehaviorNodeBase extends MagicNodeBase {

    private final BehaviorType behaviorType;

    public BehaviorNodeBase(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, BehaviorType behaviorType) {
        super(NodeType.BEHAVIOR, leftNode, rightNode, condition);
        this.behaviorType = behaviorType;
    }

    public enum BehaviorType {
        THROW, BEAM, CIRCLE, RESTRICT, RADIUS, TRIGGER;
    }
}
