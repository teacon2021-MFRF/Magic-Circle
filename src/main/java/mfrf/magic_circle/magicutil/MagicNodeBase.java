package mfrf.magic_circle.magicutil;

import java.util.function.Predicate;

public abstract class MagicNodeBase {
    protected int manaReviseAdd;
    protected double manaReviseMultiply;
    protected int complexityAdd;
    protected double complexityMultiply;
    protected int nestedLayer;
    protected MagicMatrix6By6 eigenMatrix;
    protected MagicNodeBase leftNode;
    protected MagicNodeBase rightCondition;
    protected Predicate<MagicModelBase> condition;

    public MagicNodeBase getLeftNode() {
        return leftNode;
    }

    public MagicNodeBase getRightCondition() {
        return rightCondition;
    }

    public Predicate<MagicModelBase> getCondition() {
        return condition;
    }

    /**
     * @param manaReviseAdd      Correct of mana use, would add to base value after manaReviseMultiply calculate complete.
     * @param manaReviseMultiply Correct of mana use, would multiply to base value.
     * @param complexityAdd      Same as manaReviseAdd
     * @param complexityMultiply Same as manaReviseMultiply
     * @param nestedLayer        NestedLayer of magicModel
     * @param eigenMatrix        EigenMatrix that this node abstracted.
     * @param condition          The logical condition to next node, true to left, false to right.
     * @param leftNode           Left node, could be null.
     * @param rightNode          Right node, could be null.
     *                           All node set to null means this node is the final node.
     */
    public MagicNodeBase(int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, MagicMatrix6By6 eigenMatrix, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicModelBase> condition) {
        this.manaReviseAdd = manaReviseAdd;
        this.manaReviseMultiply = manaReviseMultiply;
        this.complexityAdd = complexityAdd;
        this.complexityMultiply = complexityMultiply;
        this.nestedLayer = nestedLayer;
        this.eigenMatrix = eigenMatrix;
        this.leftNode = leftNode;
        this.rightCondition = rightNode;
        this.condition = condition;
    }

    public abstract MagicModelBase apply(MagicModelBase magic);

    public MagicModelBase invoke(MagicModelBase magic) {
        MagicModelBase apply = apply(magic);
        if (condition.test(apply) && leftNode != null) {
            return leftNode.invoke(apply);
        } else if (rightCondition != null) {
            return rightCondition.invoke(apply);
        } else {
            return magic;
        }
    }

    public int getManaReviseAdd() {
        return manaReviseAdd;
    }

    public double getManaReviseMultiply() {
        return manaReviseMultiply;
    }

    public int getComplexityAdd() {
        return complexityAdd;
    }

    public double getComplexityMultiply() {
        return complexityMultiply;
    }

    public int getNestedLayer() {
        return nestedLayer;
    }

    public MagicMatrix6By6 getEigenMatrix() {
        return eigenMatrix;
    }
}
