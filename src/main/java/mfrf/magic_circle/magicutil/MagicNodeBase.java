package mfrf.magic_circle.magicutil;

import java.util.ArrayList;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.FinalNode;
import mfrf.magic_circle.magicutil.nodes.behaviornode.BehaviorNodeBase;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.magicutil.nodes.effectnode.EffectNodeBase;
import mfrf.magic_circle.magicutil.nodes.resonancenode.ResonanceNodeBase;
import net.minecraft.nbt.CompoundNBT;

public abstract class MagicNodeBase {
    private final NodeType nodeType;

    protected MagicNodePropertyMatrix8By8 eigenMatrix = new MagicNodePropertyMatrix8By8();

    protected int layer = 0;
    protected MagicNodeBase leftNode = null;
    protected MagicNodeBase rightNode = null;

    public MagicNodeBase(NodeType nodeType, float strengthModify, float rangeModify, float durationModify,
                         float executeSpeedModify, float coolDownModify, float efficientModify, float weaknessModify,
                         float shrinkModify, float brevityModify, float relayModify, float heatupModify, float wasteModify,
                         BaguaPrefer prefer, BGMPreferences bgmPreferences) {
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
        eigenMatrix.setBaguaPrefer(prefer);
        eigenMatrix.setColors(prefer.calculateColor());
        eigenMatrix.setBGMPreference(bgmPreferences);
        MagicNodePropertyMatrix8By8.initPauliElements(eigenMatrix);

    }

    public void appendNodeL(MagicNodeBase magicNodeBase) {
        if (magicNodeBase.layer == -1) {
            magicNodeBase.layer = this.layer + 1;
        }
        if (magicNodeBase.layer > this.layer) {
            leftNode = magicNodeBase;
        }
    }

    public void appendNodeR(MagicNodeBase magicNodeBase) {
        if (magicNodeBase.layer == -1) {
            magicNodeBase.layer = this.layer + 1;
        }
        if (magicNodeBase.layer > this.layer) {
            rightNode = magicNodeBase;
        }
    }

    public MagicNodeBase(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public abstract returnDataContainer apply(MagicStream magic);

    public MagicStream invoke(MagicStream magic) {
        returnDataContainer apply = apply(magic);

        apply.magicStream.Matrixtimes(this.eigenMatrix);

        if (apply.aBoolean && rightNode != null) {
            return rightNode.invoke(apply.magicStream);
        } else if (leftNode != null) {
            return leftNode.invoke(apply.magicStream);
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

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("layer", layer);
        compoundNBT.putString("type", nodeType.name());
        compoundNBT.put("matrix", eigenMatrix.serializeNBT());
        return null;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt) {
        NodeType type = NodeType.valueOf(nbt.getString("type"));
        int layer = nbt.getInt("layer");
        MagicStreamMatrixNByN matrix = MagicNodePropertyMatrix8By8.deserializeNBT(nbt.getCompound("matrix"));

        MagicNodeBase nodeBase;
        switch (type) {
            case DECORATE: {
                nodeBase = DecorateNodeBase.deserializeNBT(nbt, layer, matrix);
                break;
            }
            case RESONANCE: {
                nodeBase = ResonanceNodeBase.deserializeNBT(nbt, layer, matrix);
                break;
            }
            case BEHAVIOR: {
                nodeBase = BehaviorNodeBase.deserializeNBT(nbt, layer, matrix);
                break;
            }
            case EFFECT: {
                nodeBase = EffectNodeBase.deserializeNBT(nbt, layer, matrix);
                break;
            }
            case FINAL: {
                nodeBase = FinalNode.deserializeNBT(nbt, layer, matrix);
                break;
            }
            case MODEL: {
                nodeBase = MagicModelBase.deserializeNBT(nbt);
                break;
            }
            case BEGIN: {
                nodeBase = BeginNodeBase.deserializeNBT(nbt, layer, matrix);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return nodeBase;
    }

    public enum NodeType {
        BEGIN, FINAL, BEHAVIOR, DECORATE, RESONANCE, EFFECT, MODEL;
    }

    public class returnDataContainer {
        final MagicStream magicStream;
        final Boolean aBoolean;

        public returnDataContainer(MagicStream magicStream, Boolean aBoolean) {
            this.magicStream = magicStream;
            this.aBoolean = aBoolean;
        }
    }

}
