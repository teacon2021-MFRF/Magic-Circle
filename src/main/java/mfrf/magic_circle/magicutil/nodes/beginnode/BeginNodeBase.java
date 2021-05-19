package mfrf.magic_circle.magicutil.nodes.beginnode;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;

import java.util.function.Predicate;

public class BeginNodeBase extends MagicNodeBase {
    /**
     * @param strengthModify
     * @param manaReviseAdd      Correct of mana use, would add to base value after manaReviseMultiply calculate complete.
     * @param manaReviseMultiply Correct of mana use, would multiply to base value.
     * @param complexityAdd      Same as manaReviseAdd
     * @param complexityMultiply Same as manaReviseMultiply
     * @param nestedLayer        NestedLayer of magicModel
     * @param eigenMatrix        EigenMatrix that this node abstracted.
     * @param leftNode           Left node, could be null.
     * @param rightNode          Right node, could be null.
     * @param condition          The logical condition to next node, true to left, false to right.
     * @param nodeType
     */

    protected MagicNodePropertyMatrix8By8 eigenMatrix = new MagicNodePropertyMatrix8By8();

    public BeginNodeBase(double strengthModify, int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, MagicNodePropertyMatrix8By8 eigenMatrix, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, NodeType nodeType) {
        super(strengthModify, manaReviseAdd, manaReviseMultiply, complexityAdd, complexityMultiply, nestedLayer, eigenMatrix, leftNode, rightNode, condition, nodeType);
    }

    @Override
    public MagicNodePropertyMatrix8By8 getEigenMatrix(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return null;
    }

}
