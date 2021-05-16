package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.interfaces.MatrixObjectComponent;
import mfrf.magic_circle.magicutil.datastructure.DecimalMagicMatrix6By6;
import mfrf.magic_circle.magicutil.datastructure.DecimalMagicMatrixNByN;

import java.awt.*;
import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class MagicNodeBase implements MatrixObjectComponent {
    private final NodeType nodeType;
    protected double strengthModify;
    protected int manaReviseAdd;
    protected double manaReviseMultiply;
    protected int complexityAdd;
    protected double complexityMultiply;
    protected int nestedLayer;
    protected Color colors;
    protected DecimalMagicMatrix6By6 eigenMatrix;
    protected MagicNodeBase leftNode;
    protected MagicNodeBase rightNode;
    protected Predicate<MagicStream> condition;

    public MagicNodeBase getLeftNode() {
        return leftNode;
    }

    public MagicNodeBase getRightNode() {
        return rightNode;
    }

    public Predicate<MagicStream> getCondition() {
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
    public MagicNodeBase(double strengthModify, int manaReviseAdd, double manaReviseMultiply, int complexityAdd, double complexityMultiply, int nestedLayer, DecimalMagicMatrix6By6 eigenMatrix, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition, NodeType nodeType) {
        this.strengthModify = strengthModify;
        this.manaReviseAdd = manaReviseAdd;
        this.manaReviseMultiply = manaReviseMultiply;
        this.complexityAdd = complexityAdd;
        this.complexityMultiply = complexityMultiply;
        this.nestedLayer = nestedLayer;
        this.eigenMatrix = eigenMatrix;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.condition = condition;
        this.nodeType = nodeType;
    }

    public abstract MagicStream apply(MagicStream magic);

    public MagicStream invoke(MagicStream magic) {
        MagicStream apply = apply(magic);
        if (condition != null) {
            if (condition.test(apply) && leftNode != null) {
                return leftNode.invoke(apply);
            } else if (rightNode != null) {
                return rightNode.invoke(apply);
            }
        }
        return magic;
    }

    public double getStrengthModify() {
        return strengthModify;
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

    public NodeType getNodeType() {
        return nodeType;
    }

    public Color getColors() {
        return colors;
    }

    public DecimalMagicMatrix6By6 getEigenMatrix() {
        return eigenMatrix;
    }

    public ArrayList<MagicNodeBase> getNodes(ArrayList<MagicNodeBase> nodes) {
        if (!nodes.contains(this)) {
            nodes.add(this);
        }
        if (this.leftNode != null) {
            leftNode.getNodes(nodes);
        }
        if (this.rightNode != null) {
            rightNode.getNodes(nodes);
        }
        return nodes;
    }

    public void getConnectivityMatrix(DecimalMagicMatrixNByN decimalMagicMatrixNByN, final ArrayList<MagicNodeBase> nodeMaps) {
        int i = nodeMaps.indexOf(this);
        if (this.leftNode != null) {
            int j = nodeMaps.indexOf(leftNode);
            decimalMagicMatrixNByN.set(i, j, 1);
            leftNode.getConnectivityMatrix(decimalMagicMatrixNByN, nodeMaps);
        }
        if (this.rightNode != null) {
            int j = nodeMaps.indexOf(rightNode);
            decimalMagicMatrixNByN.set(i, j, 1);
            leftNode.getConnectivityMatrix(decimalMagicMatrixNByN, nodeMaps);
        }
    }

    public enum NodeType {
        BEGIN, FINAL, BEHAVIOR, ADDITION, DECORATE, RESONANCE, EFFECT;
    }
}
