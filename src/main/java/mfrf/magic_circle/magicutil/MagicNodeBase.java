package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class MagicNodeBase {
    private final NodeType nodeType;

    protected MagicNodePropertyMatrix8By8 eigenMatrix = new MagicNodePropertyMatrix8By8();

    protected MagicNodeBase leftNode;
    protected MagicNodeBase rightNode;
    protected Predicate<MagicStream> condition;

    public MagicNodeBase(NodeType nodeType, float strengthModify, float rangeModify, float durationModify,
                         float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify,
                         float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify,
                         EightDiragramsPrefer prefer, BGMPreferences bgmPreferences, MagicNodeBase leftNode,
                         MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        this.nodeType = nodeType;
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.STRENGTH, strengthModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.RANGE, rangeModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.DURATION, durationModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.EXECUTE_SPEED, executeSpeedModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.COOLDOWN, coolDownModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.EFFICIENT, efficientModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.WEAKNESS, weaknessModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.SHRINK, shrinkModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.BREVITY, brevityModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.RELAY, relayModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.HEATUP, heatupModify);
        eigenMatrix.set(MagicNodePropertyMatrix8By8.INDEX.WASTE, wasteModify);
        eigenMatrix.setEightDiragramsPrefer(prefer);
        eigenMatrix.setColors(prefer.calculateColor());
        eigenMatrix.setBGMPreference(bgmPreferences);
        MagicNodePropertyMatrix8By8.initPauliElements(eigenMatrix);

        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.condition = condition;
    }

    public MagicNodeBase(NodeType nodeType, MagicNodeBase leftNode, MagicNodeBase rightNode, Predicate<MagicStream> condition) {
        this.nodeType = nodeType;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.condition = condition;
    }

    public abstract MagicStream apply(MagicStream magic);

    public MagicStream invoke(MagicStream magic) {
        MagicStream apply = apply(magic);
        apply.Matrixtimes(this.eigenMatrix);
        if (condition != null) {
            if (condition.test(apply) && leftNode != null) {
                return leftNode.invoke(apply);
            } else if (rightNode != null) {
                return rightNode.invoke(apply);
            }
        }
        return magic;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public MagicNodePropertyMatrix8By8 getEigenMatrix() {
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

    public void getConnectivityMatrix(MagicStreamMatrixNByN magicStreamMatrixNByN, final ArrayList<MagicNodeBase> nodeMaps) {
        int i = nodeMaps.indexOf(this);
        if (this.leftNode != null) {
            int j = nodeMaps.indexOf(leftNode);
            magicStreamMatrixNByN.set(i, j, 1);
            leftNode.getConnectivityMatrix(magicStreamMatrixNByN, nodeMaps);
        }
        if (this.rightNode != null) {
            int j = nodeMaps.indexOf(rightNode);
            magicStreamMatrixNByN.set(i, j, 1);
            leftNode.getConnectivityMatrix(magicStreamMatrixNByN, nodeMaps);
        }
    }

    public enum NodeType {
        BEGIN, FINAL, BEHAVIOR, ADDITION, DECORATE, RESONANCE, EFFECT, MODEL;
    }
}
