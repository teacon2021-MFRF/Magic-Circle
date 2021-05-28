package mfrf.magic_circle.magicutil;

import java.util.ArrayList;

import mfrf.magic_circle.magicutil.datastructure.MagicNodePropertyMatrix8By8;
import mfrf.magic_circle.magicutil.datastructure.MagicStreamMatrixNByN;

public class MagicModelBase extends MagicNodeBase {
    protected MagicNodeBase begin;
    private ArrayList<MagicNodeBase> nodes = null;
    private int edgeCounts = -1;
    private MagicStreamMatrixNByN connectivityMatrix = null;
    protected final MagicModelBase.type type;

    public MagicModelBase(MagicNodeBase graph, MagicModelBase.type type) {
        super(NodeType.MODEL, graph, null, magicStream -> true);
        this.begin = graph;
        this.type = type;
        this.eigenMatrix = null;
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
    public MagicStream apply(MagicStream magic) {
        return begin.invoke(magic);
    }

    public enum type {
        PLAYER, BLOCK, EQUIPMENT, CHAIN, DIMENSION, ENTITY, DROP_ENTITY, WEATHER, TIME, AUTO;
    }
}
