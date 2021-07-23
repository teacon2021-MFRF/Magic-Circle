package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.behaviornode.BehaviorNodeBase;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.magicutil.nodes.effectnode.EffectNodeBase;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

public abstract class MagicNodeBase {
    private final NodeType nodeType;

    protected MagicNodePropertyMatrix8By8 eigenMatrix = MagicNodePropertyMatrix8By8.IDENTITY.copy();

    protected int layer = -1;
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
        if (magicNodeBase != null) {
            if (magicNodeBase.layer == -1) {
                magicNodeBase.layer = this.layer + 1;
            }
            if (magicNodeBase.layer > this.layer) {
                leftNode = magicNodeBase;
            }
        }
    }

    public void appendNodeR(MagicNodeBase magicNodeBase) {
        if (magicNodeBase != null) {
            if (magicNodeBase.layer == -1) {
                magicNodeBase.layer = this.layer + 1;
            }
            if (magicNodeBase.layer > this.layer) {
                rightNode = magicNodeBase;
            }
        }
    }

    public MagicNodeBase(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public abstract DataContainer apply(MagicStream magic);

    public MagicStream invoke(MagicStream magic) {
        DataContainer apply = apply(magic);

        magic.info.lastNode = this;

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

    public MagicNodeBase getLeftNode() {
        return leftNode;
    }

    public MagicNodeBase getRightNode() {
        return rightNode;
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
        if (i != -1) {
            if (this.leftNode != null) {
                int j = nodeMaps.indexOf(leftNode);
                magicStreamMatrixNByN.set(i, j, 1);
                leftNode.getConnectivityMatrix(magicStreamMatrixNByN, nodeMaps);
            }
            if (this.rightNode != null) {
                int j = nodeMaps.indexOf(rightNode);
                magicStreamMatrixNByN.set(i, j, 2);
                leftNode.getConnectivityMatrix(magicStreamMatrixNByN, nodeMaps);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    public abstract MagicCircleComponentBase<?> getRender();

    @OnlyIn(Dist.CLIENT)
    public abstract ArrayList<Argument<?>> getArguments();

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("layer", layer);
        compoundNBT.putString("type", nodeType.name());
        compoundNBT.put("matrix", eigenMatrix.serializeNBT());
        return compoundNBT;
    }

    public static MagicNodeBase deserializeNBT(CompoundNBT nbt) {
        if (!nbt.isEmpty()) {
            NodeType type = NodeType.valueOf(nbt.getString("type"));
            int layer = nbt.getInt("layer");
            MagicNodePropertyMatrix8By8 matrix = MagicNodePropertyMatrix8By8.deserializeNBT(nbt.getCompound("matrix"));

            MagicNodeBase nodeBase;
            switch (type) {
                case DECORATE: {
                    nodeBase = DecorateNodeBase.deserializeNBT(nbt, layer, matrix);
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
        return null;
    }

    public enum NodeType {
        BEGIN, BEHAVIOR, DECORATE, EFFECT, MODEL;
    }

    public class DataContainer {
        public final MagicStream magicStream;
        public final Boolean aBoolean;

        public DataContainer(MagicStream magicStream, Boolean aBoolean) {
            this.magicStream = magicStream;
            this.aBoolean = aBoolean;
        }
    }

}
