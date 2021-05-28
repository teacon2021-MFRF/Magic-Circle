package mfrf.magic_circle.magicutil.nodes.additionnode;

import java.util.function.Predicate;

import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

public abstract class AdditionNodeTypeBase extends MagicNodeBase {
    public final AdditionType additionType;

    public AdditionNodeTypeBase(MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, AdditionType additionType) {
        super(NodeType.ADDITION, leftNode, rightNode, condition);
        this.additionType = additionType;
    }

    public enum AdditionType {
        REFLEXIVE, ANTI_REFLEXIVE, SYMMETRY, ANTI_SYMMETRY, GUN, BOW;
    }
}
