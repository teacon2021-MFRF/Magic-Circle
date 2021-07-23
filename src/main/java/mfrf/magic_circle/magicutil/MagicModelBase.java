package mfrf.magic_circle.magicutil;

import mfrf.magic_circle.gui.widgets.Argument;
import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;
import mfrf.magic_circle.rendering.MagicCircleComponentBase;
import mfrf.magic_circle.rendering.MagicCircleRenderBase;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MagicModelBase extends MagicNodeBase {
    protected MagicNodeBase begin;
    private ArrayList<MagicNodeBase> nodes = null;
    private int edgeCounts = -1;
    private MagicStreamMatrixNByN connectivityMatrix = null;

    public MagicModelBase(MagicNodeBase graph) {
        super(NodeType.MODEL);
        this.begin = graph;
        appendNodeL(graph);
    }

    public MagicModelBase(MagicStreamMatrixNByN connectivityMatrix, ArrayList<MagicNodeBase> nodes) {
        super(NodeType.MODEL);

        if (nodes.size() != 0) {
            for (int i = 0; i < connectivityMatrix.numRows; i++) {
                double[] row = connectivityMatrix.getRow(i);
                MagicNodeBase nodeBase = nodes.get(i);
                for (int j = 0; j < row.length; j++) {
                    if (row[j] == 1) {
                        nodeBase.appendNodeL(nodes.get(j));
                    }
                    if (row[j] == 2) {
                        nodeBase.appendNodeR(nodes.get(j));
                    }
                }
            }

            this.begin = nodes.get(0);
            this.connectivityMatrix = connectivityMatrix;
            this.nodes = nodes;
        }
    }

    public int getEdges() {
        if (edgeCounts == -1) {
            edgeCounts = (int) Arrays.stream(updateConnectivityMatrix().data).map(operand -> operand != 0 ? 1 : 0).sum();
        }
        return edgeCounts;
    }

    public ArrayList<MagicNodeBase> updateNodes() {
        if (begin != null) {
            nodes = begin.getNodes(new ArrayList<>());
        } else {
            nodes = new ArrayList<>();
        }
        return nodes;
    }

    public MagicStreamMatrixNByN updateConnectivityMatrix() {
        ArrayList<MagicNodeBase> nodes = updateNodes();
        int nodeCounts = nodes.size();
        connectivityMatrix = new MagicStreamMatrixNByN(nodeCounts, nodeCounts);
        begin.getConnectivityMatrix(connectivityMatrix, nodes);
        return connectivityMatrix;
    }

    @Override
    public MagicNodePropertyMatrix8By8 getEigenMatrix() {
        if (eigenMatrix == null) {
            MagicNodePropertyMatrix8By8 eigen = MagicNodePropertyMatrix8By8.IDENTITY;
            for (MagicNodeBase node : updateNodes()) {
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
        ArrayList<MagicNodeBase> nodes = updateNodes();
        for (MagicNodeBase node : nodes) {
            magicCircleRenderBase.appendNextParallelComponent(node.getRender());
        }
        return magicCircleRenderBase;
    }

    @Override
    public ArrayList<Argument<?>> getArguments() {
        return new ArrayList<>();
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        MagicStreamMatrixNByN connectivityMatrix = updateConnectivityMatrix();
        ArrayList<MagicNodeBase> nodes = updateNodes();

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
    public DataContainer apply(MagicStream magic) {
        return new DataContainer(magic, true);
    }


}
