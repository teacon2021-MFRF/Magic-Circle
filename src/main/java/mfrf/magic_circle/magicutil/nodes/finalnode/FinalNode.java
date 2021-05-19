package mfrf.magic_circle.magicutil.nodes.finalnode;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

import java.util.function.Predicate;

public class FinalNode extends MagicNodeBase {
    //todo complete it

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
     */
    public FinalNode(double strengthModify, int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, MagicNodePropertyMatrix8By8 eigenMatrix, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, NodeType nodeType) {
        super(strengthModify, manaReviseAdd, manaReviseMultiply, complexityAdd, complexityMultiply, nestedLayer, eigenMatrix, leftNode, rightNode, condition, nodeType);
    }


    @Override
    public MatrixObjectComponent times(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicNodePropertyMatrix8By8 times(MagicNodePropertyMatrix8By8 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent plus(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicNodePropertyMatrix8By8 plus(MagicNodePropertyMatrix8By8 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent minus(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicNodePropertyMatrix8By8 minus(MagicNodePropertyMatrix8By8 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent div(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicNodePropertyMatrix8By8 div(MagicNodePropertyMatrix8By8 d) {
        return null;
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
