package mfrf.magic_circle.magicutil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import mfrf.magic_circle.magicutil.nodes.BeginNodeBase;
import mfrf.magic_circle.magicutil.nodes.FinalNode;
import mfrf.magic_circle.magicutil.nodes.behaviornode.BehaviorNodeBase;
import mfrf.magic_circle.magicutil.nodes.decoratenode.DecorateNodeBase;
import mfrf.magic_circle.magicutil.nodes.effectnode.EffectNodeBase;
import mfrf.magic_circle.magicutil.nodes.resonancenode.ResonanceNodeBase;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

public class MagicModelBase extends MagicNodeBase {
    protected MagicNodeBase begin;
    private ArrayList<MagicNodeBase> nodes = null;
    private int edgeCounts = -1;
    private MagicStreamMatrixNByN connectivityMatrix = null;

    public MagicModelBase(MagicNodeBase graph) {
        super(NodeType.MODEL);
        this.begin = graph;
        this.eigenMatrix = null;
        appendNodeR(graph);
    }

    public int getEdges() {
        if (edgeCounts == -1) {
            edgeCounts = (int) getConnectivityMatrix().sumAll();
        }
        return edgeCounts;
    }

    public ArrayList<MagicNodeBase> getNodes() {
        if (nodes == null)
            nodes = begin.getNodes(new ArrayList<>());
        return nodes;
    }

    public MagicStreamMatrixNByN getConnectivityMatrix() {
        if (connectivityMatrix == null) {
            ArrayList<MagicNodeBase> nodes = getNodes();
            int nodeCounts = nodes.size();
            connectivityMatrix = new MagicStreamMatrixNByN(nodeCounts, nodeCounts);
            for (int i = 0; i < nodeCounts; i++) {
                connectivityMatrix.set(i, i, 1);
            }
            begin.getConnectivityMatrix(connectivityMatrix, nodes);
        }
        return connectivityMatrix;
    }

    @Override
    public MagicNodePropertyMatrix8By8 getEigenMatrix() {
        if (eigenMatrix == null) {
            MagicNodePropertyMatrix8By8 eigen = MagicNodePropertyMatrix8By8.IDENTITY;
            for (MagicNodeBase node : getNodes()) {
                eigen = eigen.leftTimes(node.eigenMatrix);
            }
            this.eigenMatrix = eigen;
        }
        return super.getEigenMatrix();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public MagicCircleComponentBase<?> getRender() {
        MagicCircleRenderBase magicCircleRenderBase = new MagicCircleRenderBase();
        ArrayList<MagicNodeBase> nodes = getNodes();
        for (MagicNodeBase node : nodes) {
            magicCircleRenderBase.appendNextParallelComponent(node.getRender());
        }
        return magicCircleRenderBase;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        MagicStreamMatrixNByN connectivityMatrix = getConnectivityMatrix();
        ArrayList<MagicNodeBase> nodes = getNodes();

        compoundNBT.put("connectivity_matrix", connectivityMatrix.serializeNBT());

        ListNBT listNBT = new ListNBT();
        nodes.stream().map(MagicNodeBase::serializeNBT).forEachOrdered(listNBT::add);
        compoundNBT.put("nodes", listNBT);
        return compoundNBT;
    }

    public static MagicModelBase deserializeNBT(CompoundNBT nbt) {
        ArrayList<MagicNodeBase> collect = nbt.getList("nodes", Constants.NBT.TAG_COMPOUND).stream().map(inbt -> (CompoundNBT) inbt).map(MagicNodeBase::deserializeNBT).collect(Collectors.toCollection(ArrayList::new));
        if (!collect.isEmpty()) {
            MagicStreamMatrixNByN connectivity_matrix = MagicStreamMatrixNByN.deserializeNBT(nbt.getCompound("connectivity_matrix"));

            for (int i = 0; i < collect.size(); i++) {
                double[] row = connectivity_matrix.getRow(i);
                for (int j = 0; j < row.length; j++) {
                    if (j != i && row[j] != 0) {
                        MagicNodeBase node = collect.get(i);
                        if (node.leftNode == null) {
                            node.appendNodeL(collect.get(j));
                        } else if (node.rightNode == null) {
                            node.appendNodeR(collect.get(j));
                        }
                    }
                }
            }

            return new MagicModelBase(collect.get(0));
        } else {
            return new MagicModelBase(null);
        }
    }

    @Override
    public returnDataContainer apply(MagicStream magic) {
        return new returnDataContainer(magic, true);
    }


}
