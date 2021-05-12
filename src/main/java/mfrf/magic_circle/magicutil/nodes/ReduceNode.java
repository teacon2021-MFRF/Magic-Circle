package mfrf.magic_circle.magicutil.nodes;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import mfrf.magic_circle.magicutil.datastructure.DecimalMagicMatrix6By6;
import mfrf.magic_circle.magicutil.MagicNodeBase;
import mfrf.magic_circle.magicutil.MagicStream;

import java.util.function.Predicate;

public class ReduceNode extends MagicNodeBase {
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
    public ReduceNode(double strengthModify, int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, DecimalMagicMatrix6By6 eigenMatrix, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        super(strengthModify, manaReviseAdd, manaReviseMultiply, complexityAdd, complexityMultiply, nestedLayer, eigenMatrix, null, null, null);
    }

    @Override
    public MatrixObjectComponent times(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public DecimalMagicMatrix6By6 times(DecimalMagicMatrix6By6 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent plus(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public DecimalMagicMatrix6By6 plus(DecimalMagicMatrix6By6 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent minus(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public DecimalMagicMatrix6By6 minus(DecimalMagicMatrix6By6 d) {
        return null;
    }


    @Override
    public MatrixObjectComponent div(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public DecimalMagicMatrix6By6 div(DecimalMagicMatrix6By6 d) {
        return null;
    }


    @Override
    public DecimalMagicMatrix6By6 getEigenMatrix(MatrixObjectComponent matrixObjectComponent) {
        return null;
    }

    @Override
    public MagicStream apply(MagicStream magic) {
        return null;
    }
}
